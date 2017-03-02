package zhidanhyb.huozhu.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Base.BaseDialog;
import zhidanhyb.huozhu.Utils.UserExitUtils;
/**
 * 退出dialog
 * @author lxj
 *
 */
public class Exit_Login_Dialog extends BaseDialog implements OnClickListener{

	private Context mContext;
	/**
	 *
	 */
	private Button sure_button;
	private Button back_button;
	public Exit_Login_Dialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
	}
	
	public void exitLogin(){
		View view = LayoutInflater.from(mContext).inflate(R.layout.exit_loginlayout, null);
		setContentView(view);
		setDialogTitle(R.string.exitlogin, R.drawable.yellowtips);
		sure_button = (Button)view.findViewById(R.id.dialog_sure_button);//确定
		sure_button.setOnClickListener(this);
		sure_button.setText(R.string.sendorder_goods_type_sure);
		back_button = (Button)view.findViewById(R.id.dialog_back_button);//返回
		back_button.setOnClickListener(this);
		back_button.setText(R.string.sendorder_goods_type_cancle);
		show();
	}

	/**
	 * @param v
	 */
	@SuppressWarnings("static-access")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_sure_button:
			new UserExitUtils(mContext).userExit();
			dismiss();
			break;
		case R.id.dialog_back_button:
			dismiss();
			break;
		default:
			break;
		}
	}
}
