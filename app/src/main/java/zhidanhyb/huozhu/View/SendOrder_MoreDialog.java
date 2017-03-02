package zhidanhyb.huozhu.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import zhidanhyb.huozhu.Activity.Order.SendOrderActivity;
import zhidanhyb.huozhu.Activity.Order.SendOrderListActivity;
import zhidanhyb.huozhu.Base.BaseDialog;
import zhidanhyb.huozhu.Bean.OwnerGoldBean;
import zhidanhyb.huozhu.Config.ConstantConfig;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.onSendOrderJsonListener;
import zhidanhyb.huozhu.R;
/**
 * 发送多单时提示框
 * @author lxj
 *
 */
public class SendOrder_MoreDialog extends BaseDialog {

	private Context mContext;
	private TextView ordernumber_textview;
	private TextView leval_textview;
	private TextView gold_textview;
	private TextView discount_textview;
	private TextView integral_textview;
	public SendOrder_MoreDialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
	}
	
	public void showDialog(OwnerGoldBean ownerGoldBean){
		View view = LayoutInflater.from(mContext).inflate(R.layout.sendorder_morelayout, null);
		setContentView(view);
		ordernumber_textview = (TextView)view.findViewById(R.id.more_ordernumber_textview);//2
		leval_textview = (TextView)view.findViewById(R.id.more_leval_textview);// 单   您是 伯爵 将扣除 
		gold_textview = (TextView)view.findViewById(R.id.more_gold_textview);//9
		discount_textview = (TextView)view.findViewById(R.id.more_discount_textview);// 金币[7折]
		integral_textview = (TextView)view.findViewById(R.id.more_integral_textview);//离下一级别  侯爵  还差  20 积分
		setDialogTitle(R.string.sureorder_hint,0);
		setLeftButton(R.string.sureorder_change, 0);
		setRightButton(R.string.sendorder_goods_type_sure, 0);
		setView(ownerGoldBean);
		show();
	}

	private void setView(OwnerGoldBean ownerGoldBean) {
		ordernumber_textview.setText(ConstantConfig.sendSureList.size()+"");
		leval_textview.setText("您是"+ownerGoldBean.getLevel()+"将扣除 ");
		int s = (int)((ownerGoldBean.getPoint() * ownerGoldBean.getDiscount() * 0.01));
		int ispush = 0;
		for (int i = 0; i < ConstantConfig.sendSureList.size(); i++) {
			if(ConstantConfig.sendSureList.get(i).getIsPush().toString().equals("1")){
				ispush = ispush + 10;
			}
		}
		gold_textview.setText((s*ConstantConfig.sendSureList.size()+ispush)+"");
		discount_textview.setText("金币["+ownerGoldBean.getDiscount()+"折]");
		integral_textview.setText(ownerGoldBean.getStr());
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.dialog_back_button://修改
			dismiss();
			break;
		case R.id.dialog_sure_button://确定
			sendMoreOrder();
			break;
		default:
			break;
		}
	}

	private void sendMoreOrder() {
		dismiss();
		sure_button.setClickable(false);
		Gson gson = new Gson();
		HttpController controller = new HttpController(mContext);
		String jsonOrder = gson.toJson(ConstantConfig.sendSureList);

		controller.setOnSendOrderJsonListener(new onSendOrderJsonListener() {
			@Override
			public void sendOrder() {
				
				ConstantConfig.sendSureList.clear();
				ConstantConfig.sendOrderSucc = true;
				if(SendOrderActivity.OrderActivity != null){
					SendOrderActivity.OrderActivity.finish();
				}
				if(SendOrderListActivity.orderlistactivity != null){
					SendOrderListActivity.orderlistactivity.finish();
				}
				sure_button.setClickable(true);
				
			}
		});
		controller.SendOrderJson(jsonOrder);
	}
}
