package zhidanhyb.huozhu.Utils;

import android.content.Context;
import android.content.Intent;
import zhidanhyb.huozhu.Activity.Login.User_LoginActivity;
import zhidanhyb.huozhu.Base.AppManager;
import zhidanhyb.huozhu.Config.ZDSharedPreferences;
import zhidanhyb.huozhu.HttpRequest.HttpController;

/**
 * 用户退出进行的一些操作
 * @author lxj
 *
 */
public class UserExitUtils {

	private static Context mContext;
	@SuppressWarnings("static-access")
	public UserExitUtils(Context context) {
		this.mContext = context;
	}
	//用户主动退出
	public static void userExit(){
		if(mContext == null){
			return;
		}
		HttpController controller = new HttpController(mContext);
		String type = "2";
		controller.ExitLogin(type);
	}
	//在另一端登录，强迫退出
	@SuppressWarnings("static-access")
	public static void forcedExit(){
		ZDSharedPreferences.getInstance(mContext).setHttpHeadToken("");
		ZDSharedPreferences.getInstance(mContext).setUserId("");
		ZDSharedPreferences.getInstance(mContext).setUserMobile("");
		ZDSharedPreferences.getInstance(mContext).setUserName("");
		ZDSharedPreferences.getInstance(mContext).setUserStatus("");
		ZDSharedPreferences.getInstance(mContext).setUserHead("");
		ZDSharedPreferences.getInstance(mContext).setUserCompany("");
		//		AppManager.getAppManager().finishActivity(MainActivity.class);
		//		AppManager.getAppManager().finishActivity(SettingActivity.class);
		for (int i = 0; i < AppManager.getAppManager().activityStack.size(); i++) {
			AppManager.getAppManager().finishActivity(AppManager.getAppManager().activityStack.get(i));
		}
		mContext.startActivity(new Intent(mContext,User_LoginActivity.class));
	}
}
