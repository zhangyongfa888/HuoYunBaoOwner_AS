package zhidanhyb.huozhu.View;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import zhidanhyb.huozhu.Base.BaseDialog;
import zhidanhyb.huozhu.Bean.RechargeAliPayBean;
import zhidanhyb.huozhu.Bean.RechargeWxBean;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.rechargeMoneyListener;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.KeyboradUtils;
import zhidanhyb.huozhu.Utils.T;
import zhidanhyb.huozhu.pay.AliPay;
import zhidanhyb.huozhu.pay.WxPay;

/**
 * 用户充值dialog
 * @author lxj
 *
 */
public class User_Recharge_Gold_Dialog extends BaseDialog implements rechargeMoneyListener{

	private Context mContext;
	private EditText recharge_edittext;
	private int paytype = 0;//充值类型 1 微信 2支付宝
	public static User_Recharge_Gold_Dialog recharge_gold;
	public User_Recharge_Gold_Dialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
	}
	
	public void showDialog(int i){
		paytype = i;
		View view = LayoutInflater.from(mContext).inflate(R.layout.user_recharge_goldlayout, null);
		setContentView(view);
		setDialogTitle(R.string.recharge,0);
		recharge_edittext = (EditText)view.findViewById(R.id.recharge_edittext);
		setLeftButton(R.string.sendorder_goods_type_cancle ,0);
		setRightButton(R.string.sure,0);
		show();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				//设置可获得焦点  
				recharge_edittext.setFocusable(true); 
				recharge_edittext.setFocusableInTouchMode(true);  
				//请求获得焦点  
				recharge_edittext.requestFocus(); 
				KeyboradUtils.ShowKeyboard(recharge_edittext);				
			}
		}, 200);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.dialog_sure_button://确认
			if(recharge_edittext.getText().toString().trim().isEmpty()){
				T.showShort(mContext, R.string.rechargemoney);
				return;
			}
			rechargeMoney(recharge_edittext.getText().toString().trim());
			break;
		case R.id.dialog_back_button://返回
			colseDialog();
			break;
		default:
			break;
		}
	}

	private void rechargeMoney(String trim) {
		HttpController controller = new HttpController(mContext);
		controller.setRechargeMoneyListener(this);
		controller.rechargeMoney(trim,paytype);
	}
	
	private void colseDialog(){
		dismiss();
		recharge_edittext = null;
		if(recharge_gold != null){
			recharge_gold = null;
		}
	}

	@Override
	public void onRechargeMoney(String Data) {
		Gson gson = new Gson();
		recharge_gold = this;
		if(paytype == 1){
			RechargeWxBean wxbean = gson.fromJson(Data, RechargeWxBean.class);
			new WxPay(mContext).pay(wxbean);;
		}else if(paytype ==2){
			RechargeAliPayBean alipaybean = gson.fromJson(Data, RechargeAliPayBean.class);
			new AliPay(mContext).pay(alipaybean,"1");
		}
	}
}
