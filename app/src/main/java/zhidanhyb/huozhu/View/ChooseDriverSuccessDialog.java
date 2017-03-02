package zhidanhyb.huozhu.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Base.BaseDialog;
import zhidanhyb.huozhu.Bean.DriverInfoBean;
/**
 * 选择司机成功后弹出此提示框
 * @author lxj
 *
 */
public class ChooseDriverSuccessDialog extends BaseDialog {

	private Context mContext;
	private TextView driver_textview;
	public ChooseDriverSuccessDialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
	}
	
	public void show(DriverInfoBean driverinfo){
		View view = LayoutInflater.from(mContext).inflate(R.layout.choosedriversuccesslayout, null);
		setContentView(view);
		driver_textview = (TextView)view.findViewById(R.id.driver_textview);//
		driver_textview.setText("您已为此订单选择 "+driverinfo.getName()+" "+driverinfo.getPlate_num());
		setDialogTitle(R.string.choosedriver, R.drawable.yellowtips);
		setRightButton(R.string.sendorder_goods_type_sure, R.drawable.orange_circular_bead_buttons);
		setLeftButton(R.string.sendorder_goods_type_cancle, R.drawable.gray_circular_bead_buttons);
		show();
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.dialog_sure_button://
			if(driverSuccessListener != null){
				driverSuccessListener.onSuccessCloseDialog();
			}
			dismiss();
			break;
		case R.id.dialog_back_button://
//			if(driverSuccessListener != null){
//				driverSuccessListener.onCloseDialog();
//			}
			dismiss();
			break;
		default:
			break;
		}
	}
	
	private onChooseDriverSuccessListener driverSuccessListener;
	public void setOnChooseDriverSuccessListener(onChooseDriverSuccessListener chooseDriverSuccessListener){
		this.driverSuccessListener = chooseDriverSuccessListener;
	}
	public interface onChooseDriverSuccessListener{
		void onCloseDialog();
		void onSuccessCloseDialog();
	}
}
