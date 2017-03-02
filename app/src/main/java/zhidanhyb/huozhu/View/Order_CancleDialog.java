package zhidanhyb.huozhu.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import zhidanhyb.huozhu.Activity.Order.Main_OrderActivity;
import zhidanhyb.huozhu.Base.BaseDialog;
import zhidanhyb.huozhu.Config.ConstantConfig;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.onGetOwnerCancleListener;
import zhidanhyb.huozhu.R;
/**
 * 取消订单dialog
 * @author lxj
 *
 */
public class Order_CancleDialog extends BaseDialog implements onGetOwnerCancleListener{

	private Context mContext;
	private TextView cancle_textview;
	private String OrderId;
	private boolean isCancle = false;
	private HttpController controller;
	public Order_CancleDialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
		controller = new HttpController(mContext);
		controller.setOnGetOwnerCancleListener(this);
		controller.getOwnerCancle();
	}
	
	public void showDialog(String orderId){
		this.OrderId = orderId;
		View view = LayoutInflater.from(mContext).inflate(R.layout.order_canclelayout, null);
		setContentView(view);
		setDialogTitle(R.string.cancle, R.drawable.graytips);
		cancle_textview = (TextView)view.findViewById(R.id.cancle_textview);//取消提示内容
		setLeftButton(R.string.sendorder_goods_type_cancle, R.drawable.gray_circular_bead_buttons);
		setRightButton(R.string.sure, R.drawable.darkgray_circular_bead_buttons);
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.dialog_sure_button://确认
			if(isCancle && OrderId != null){
				sure_button.setClickable(false);
				cancle();
			}else{
				closeDialog();
			}
			break;
		case R.id.dialog_back_button://返回
			closeDialog();
		break;
		default:
			break;
		}
	}
	private void cancle() {
		if(controller != null)
		controller.cancleOrder(OrderId);
	}

	private void closeDialog() {
		dismiss();
		cancle_textview = null;
	}

	@Override
	public void CancleNum(String num) {
		if(num.toString().equals("0")){
			cancle_textview.setText("您今天取消订单次数已用完\n暂不能取消订单.");
			isCancle = false;
		}else{
			cancle_textview.setText("订单还在进行,您真的要确定取消吗?\n24小时内,您还有"+num+"次取消的机会.");
			isCancle = true;
		}
		show();
	}

	@Override
	public void CancleSuccess() {
		if(ConstantConfig.isOrderList){
			if(Main_OrderActivity.underwayActivity != null){
				Main_OrderActivity.underwayActivity.onRefresh();
			}
			if(Main_OrderActivity.accomplishActivity != null){
				Main_OrderActivity.accomplishActivity.onRefresh();
			}
		}
		closeDialog();
	}
}
