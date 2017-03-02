package zhidanhyb.huozhu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import zhidanhyb.huozhu.Activity.Login.User_LoginActivity;
import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.Config.ZDSharedPreferences;
import zhidanhyb.huozhu.R;

/**
 * 应用启动页
 * @author lxj
 *
 */
public class App_StartPageActivity extends BaseActivity {

	@Override
	protected void setBeforeAddContent() {
		super.setBeforeAddContent();
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
				.LayoutParams.FLAG_FULLSCREEN);
	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		setContentView(R.layout.app_startpagelayout);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if(ZDSharedPreferences.getInstance(App_StartPageActivity.this).getUserId().equals("")){//如果是空的就登录
					startActivity(new Intent(App_StartPageActivity.this,User_LoginActivity.class));
				}else{//不是空的就直接进入首页
					startActivity(new Intent(App_StartPageActivity.this,MainActivity.class));
				}
				finish();
			}
		}, 2000);
	}
}
