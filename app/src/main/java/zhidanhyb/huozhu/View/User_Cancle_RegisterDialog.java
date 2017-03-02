package zhidanhyb.huozhu.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Base.BaseDialog;

/**
 * 用户取消注册dialog
 * @author lxj
 *
 */
public class User_Cancle_RegisterDialog extends BaseDialog {

	private Context mContext;
	public User_Cancle_RegisterDialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
	}

	public void showDialog(){
		View view = LayoutInflater.from(mContext).inflate(R.layout.user_cancle_registerlayout, null);
		setContentView(view);
		setDialogTitle(R.string.hint, R.drawable.redtips);
		setLeftButton(R.string.sendorder_goods_type_cancle, R.drawable.gray_circular_bead_buttons);
		setRightButton(R.string.ok, R.drawable.red_circular_bead_buttons);
		show();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.dialog_back_button://返回
			dismiss();
			break;
		case R.id.dialog_sure_button://
			if(cancleRegisterListener == null)
				return;
			cancleRegisterListener.onSure();
			break;
		default:
			break;
		}
	}
	private onCancleRegisterListener cancleRegisterListener;
	public void setOnCancleRegisterListener(onCancleRegisterListener onCancleRegisterListener){
		this.cancleRegisterListener = onCancleRegisterListener;
	}
	public interface onCancleRegisterListener{
		void onSure();
	}
}
