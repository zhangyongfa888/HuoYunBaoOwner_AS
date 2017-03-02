package zhidanhyb.huozhu.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Base.BaseDialog;

/**
 * 充值密码两次密码不一致dialog
 * @author lxj
 *
 */
public class ForgetPassword_MistakeDialog extends BaseDialog {

	private Context mContext;
	public ForgetPassword_MistakeDialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
	}

	/**
	 *
	 */
	public void showDialog(){
		View view = LayoutInflater.from(mContext).inflate(R.layout.forgetpassword_mistakelayout, null);
		setContentView(view);
		setDialogTitle(R.string.inputerror, R.drawable.graytips);
		setHintButton(R.string.back, R.drawable.gray_circular_bead_buttons);
		show();
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		if(v.getId() == R.id.dialog_back_button){
			dismiss();
		}
	}
}
