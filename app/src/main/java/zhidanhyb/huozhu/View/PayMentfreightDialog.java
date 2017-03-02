package zhidanhyb.huozhu.View;

import com.google.gson.Gson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Activity.Order.Main_OrderActivity;
import zhidanhyb.huozhu.Activity.Order.OrderDetailsActivity;
import zhidanhyb.huozhu.Base.BaseDialog;
import zhidanhyb.huozhu.Bean.RechargeAliPayBean;
import zhidanhyb.huozhu.Bean.RechargeWxBean;
import zhidanhyb.huozhu.Config.ConstantConfig;
import zhidanhyb.huozhu.HttpRequest.HttpConfigSite;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.onGetOwnerPayPowListener;
import zhidanhyb.huozhu.HttpRequest.HttpController.rechargeMoneyListener;
import zhidanhyb.huozhu.Utils.T;
import zhidanhyb.huozhu.View.Withdraw_DepositPassPop.withdrawSuccessListener;
import zhidanhyb.huozhu.View.Withdraw_PasswordSettingPop.onSubmitPassowrdListener;
import zhidanhyb.huozhu.pay.AliPay;
import zhidanhyb.huozhu.pay.WxPay;

/**
 * 货主支付运费的dialog
 * @author lxj
 *
 */
public class PayMentfreightDialog extends BaseDialog implements rechargeMoneyListener{

	private Context mContext;
	private TextView money_textview;
	private RadioGroup radiogroup;
	private TextView orderid_textview;
	private String payType = "1";//1-默认余额支付 2-支付宝支付  3-微信支付
	private String orderid = null;
	private String price = null;
	public static PayMentfreightDialog mentfreightDialog = null;
	public PayMentfreightDialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
	}
	/**
	 * @param id - 订单id
	 * @param price - 运费
	 */
	public void showDialog(String id, String price){
		orderid = id;
		this.price = price;
		View view = LayoutInflater.from(mContext).inflate(R.layout.paymentfreightlayout, null);
		setContentView(view);
		setDialogTitle(R.string.paydetails, R.drawable.yellowtips);
		setLeftButton(R.string.back, R.drawable.gray_circular_bead_buttons);
		setRightButton(R.string.ok, R.drawable.orange_circular_bead_buttons);
		orderid_textview = (TextView)view.findViewById(R.id.payorder_orderid_textview);//订单号
		radiogroup = (RadioGroup)view.findViewById(R.id.payorder_radiogroup);//
		money_textview = (TextView)view.findViewById(R.id.payorder_money_textview);//总运费
		orderid_textview.setText("订单信息   "+id+"号订单付款");
		money_textview.setText(price);
		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.payorder_balance_radiobutton://余额支付
					payType = "1";

					break;
				case R.id.payorder_alipay_radiogroup://支付宝支付
					payType = "2";

					break;
				case R.id.payorder_weixinpay_radiogroup://微信支付
					payType = "3";

					break;
				default:
					break;
				}
			}
		});
		show();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.dialog_back_button://返回
			dismiss();
			break;
		case R.id.dialog_sure_button://确定支付运费
			PayFreight();
			break;
		default:
			break;
		}
	}

	private void PayFreight() {
		if(payType.toString().equals("1")){//余额支付
			HttpController controller = new HttpController(mContext);
			controller.setOnGetOwnerPayPowListener(new onGetOwnerPayPowListener() {
				@Override
				public void onPayPow(String string) {
					if(string.toString().equals("0")){//未设置密码
						T.showLong(mContext, "您尚未设置交易密码,请先设置密码");
						Withdraw_PasswordSettingPop passwordSettingPop = new Withdraw_PasswordSettingPop(mContext);
						passwordSettingPop.setOnSubmitPasswrodListener(new onSubmitPassowrdListener() {
							@Override
							public void onsuccess(String orderid,String orderprice) {//如果设置成功后在弹出支付密码页面
								Withdraw_DepositPassPop pop = new Withdraw_DepositPassPop(mContext);
								pop.showPop(3,orderprice,orderid);
//								ClooseDialog();
							}
						});
						passwordSettingPop.setOrderInfor(price, orderid);
						passwordSettingPop.showPop(2);
						ClooseDialog();
						
					}else if(string.toString().equals("1")){//已设置密码
						Withdraw_DepositPassPop pop = new Withdraw_DepositPassPop(mContext);
						pop.showPop(3,price,orderid);
						ClooseDialog();
					}
				}
			});
			controller.getOwnerPayPow();
			
		}else if(payType.toString().equals("2")){//支付宝支付
			if(orderid == null || price == null){
				dismiss();
				T.showLong(mContext, "订单信息错误,请重新操作");
				return;
			}
			RechargeAliPayBean alipaybean = new RechargeAliPayBean();
			alipaybean.setAccount_id(orderid);
			alipaybean.setMoney(price);
			alipaybean.setNotify_url(HttpConfigSite.PostUrl+HttpConfigSite.Post_AliPayUpdata);
			mentfreightDialog = this;
			new AliPay(mContext).pay(alipaybean,"2");
		}else if(payType.toString().equals("3")){//微信支付
			mentfreightDialog = this;
			HttpController controller = new HttpController(mContext);
			controller.setRechargeMoneyListener(this);
			controller.OrderPayFreight(orderid,payType,"");//这里的空“”是微信支付不需要本地账户余额的支付密码
		}
	}
	//微信支付返回支付订单信息的回调
	@Override
	public void onRechargeMoney(String Data) {
		if(payType.toString().equals("3")){
			Gson gson = new Gson();
			RechargeWxBean wxbean = gson.fromJson(Data, RechargeWxBean.class);
			new WxPay(mContext).pay(wxbean);
		}
	}
	
	public void ClooseDialog(){
		if(ConstantConfig.isOrderDetails){
			if(OrderDetailsActivity.detailsActivity != null){
				OrderDetailsActivity.detailsActivity.getData();
			}
		}
		if(Main_OrderActivity.accomplishActivity != null){
			Main_OrderActivity.accomplishActivity.onRefresh();
		}
		dismiss();
		if(mentfreightDialog != null){
			mentfreightDialog = null;
		}
		orderid_textview = null;
		radiogroup = null;
		money_textview = null;
	}
}
