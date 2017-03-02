package zhidanhyb.huozhu.View;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.ScreenUtils;
/**
 * 首次注册应用的提示页面
 * @author lxj
 *
 */
public class Register_User_Hint_Dialog {

	private Activity mcontext;
	private String title;
	private View view;
	private Dialog myDialog;
	private int diax;
	private int diay;
	private ImageView register_getcode_bt;
	private TextView register_arrows_tv;

	public Register_User_Hint_Dialog(Activity context,String title, int x, int y) {
		this.mcontext = context;
		this.title = title;
		this.diax = x;
		this.diay = y;
	}
	
	@SuppressLint("NewApi")
	public void showDialog()
	{
		try {
			view = LayoutInflater.from(mcontext).inflate(R.layout.register_user_hint_dialog, null);
			myDialog = new Dialog(mcontext, R.style.myDialogTheme);
			register_getcode_bt = (ImageView)view.findViewById(R.id.register_getcode_bt);
			register_arrows_tv = (TextView)view.findViewById(R.id.register_arrows_tv);
			
			register_getcode_bt.postDelayed(new Runnable() {

				@Override
				public void run() {
					try {
						register_getcode_bt.setX(diax);
						register_getcode_bt.setY(diay-ScreenUtils.getStatusHeight(mcontext));
					} catch (Exception e) {
						Toast.makeText(mcontext, "您的系统版本过低,建议您使用安卓4.0以上的版本", 0).show();
						e.printStackTrace();
					}
				}
			}, 0);

			register_arrows_tv.postDelayed(new Runnable() {

				@Override
				public void run() {
					register_arrows_tv.setX(diax+register_getcode_bt.getWidth()/2);
					register_arrows_tv.setY(register_getcode_bt.getY()-register_arrows_tv.getHeight());
				}
			}, 0);
			myDialog.setContentView(view);
			myDialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			myDialog.show();
			
			register_getcode_bt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					registeruserlistener.onclick();
					myDialog.dismiss();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Registeruserlistener registeruserlistener;
	public interface Registeruserlistener{
		public void onclick();
	}
	public Registeruserlistener getRegisterListener()
	{
		return registeruserlistener;
	}
	public void setRegisterUserListener(Registeruserlistener listener){
		this.registeruserlistener = listener;
	}
}
