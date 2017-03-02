package zhidanhyb.huozhu.View;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Activity.Home.SendOrder_AuthenticationActivity;
import zhidanhyb.huozhu.Base.BaseDialog;

/**
 * 用户未验证dialog
 * @author lxj
 *
 */
public class User_VerificationDialog extends BaseDialog {

	private Context mContext;
	public User_VerificationDialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
	}
	
	public void showDialog(){
		View view = LayoutInflater.from(mContext).inflate(R.layout.uesr_verificationlayout, null);
		setContentView(view);
		setDialogTitle(R.string.notverification, R.drawable.graytips);
		setLeftButton(R.string.back, R.drawable.gray_circular_bead_buttons);
		setRightButton(R.string.verification, R.drawable.darkgray_circular_bead_buttons);
		show();
	}

	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		
		switch (v.getId()) {
		case R.id.dialog_back_button://返回
			dismiss();
			break;
		case R.id.dialog_sure_button://验证
			mContext.startActivity(new Intent(mContext,SendOrder_AuthenticationActivity.class));
			dismiss();
			
			break;
		default:
			break;
		}
	}
}
