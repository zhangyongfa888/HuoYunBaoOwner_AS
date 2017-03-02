package zhidanhyb.huozhu.Activity.Login;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.jpush.android.api.JPushInterface;
import zhidanhyb.huozhu.Activity.MainActivity;
import zhidanhyb.huozhu.Base.AppManager;
import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.Bean.UserLoginBean;
import zhidanhyb.huozhu.Config.ZDSharedPreferences;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.userLoginSuccessListener;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.AppUtil;
import zhidanhyb.huozhu.Utils.Email_VerifyUtils;
import zhidanhyb.huozhu.Utils.MD5;
import zhidanhyb.huozhu.Utils.T;
import zhidanhyb.huozhu.Utils.ViewUtils;

/**
 * 用户登录
 * 
 * @author lxj
 *
 */
public class User_LoginActivity extends BaseActivity implements userLoginSuccessListener {

	private EditText phone_textview;
	private EditText password_textview;
	private Button login_button;
	private TextView newuser_textview;
	private TextView forgetpw_textview;
	private String jpid;
	public static boolean isRegister = false;// 判断用户是否进行了注册
	public static String isRegisterPhone = null;// 如果 isRegister = true 赋值手机号
	/**
	 *
	 */
LinearLayout login_banner;

	/**
	 * @param arg0
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.user_loginlayout);
		login_banner= (LinearLayout) findViewById(R.id.login_banner);

		ViewUtils.setViewSize(context,login_banner,640,299);
		ViewUtils.setViewSize(context,findViewById(R.id.login_logo_im),640,99);
		ViewUtils.setViewSize(context,findViewById(R.id.v2_login_pwd),640,122);
		ViewUtils.setViewSize(context,findViewById(R.id.v2_login_account),640,99);
		ViewUtils.setViewSize(context,findViewById(R.id.login_button),640,115);

		if (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
				&& hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
				&& hasPermission(Manifest.permission.READ_PHONE_STATE)) {

		} else {
			T.showLong(this, "请允许货运宝获取权限");

			getPermissions(
					new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE,
							Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE },
					REQUEST_PERMISSION_Storage);
		}
		initView();
	}

	private void initView() {
		setTitleText(R.string.login);
		setHideLeftButton();
		setHideRightButton();

		// 08-30 10:02:01.854: E/WifiService(5744): Permission denial: Need
		// ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION permission to get scan
		// results
		// getPermissions(new
		// String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},0x112);

		phone_textview = (EditText) findViewById(R.id.login_phone_textview);// 手机号
		password_textview = (EditText) findViewById(R.id.login_password_textview);// 密码
		login_button = (Button) findViewById(R.id.login_button);// 登录
		login_button.setOnClickListener(this);
		forgetpw_textview = (TextView) findViewById(R.id.login_forgetpw_textview);// 忘记密码
		forgetpw_textview.setOnClickListener(this);
		newuser_textview = (TextView) findViewById(R.id.login_newuser_textview);// 新用户注册
		newuser_textview.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.login_button:// 登录
			if (isEmpty()) {

				userLogin();
			}
			break;
		case R.id.login_forgetpw_textview:// 忘记密码
			intent.setClass(this, User_ForgetPasswordActivity.class);
			startActivity(intent);
			break;
		case R.id.login_newuser_textview:// 新用户注册
			intent.setClass(this, User_VerificationPhone.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	private boolean isEmpty() {
		boolean is = true;
		if (phone_textview.getText().toString().isEmpty()) {
			T.showShort(this, "手机号不能为空!");
			is = false;
			return is;
		}
		if (password_textview.getText().toString().isEmpty()) {
			T.showShort(this, "密码不能为空!");
			is = false;
			return is;
		}
		return is;
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (isRegister) {
			if (isRegisterPhone != null)
				phone_textview.setText(isRegisterPhone + "");
		}
	}

	// 用户登录操作
	private void userLogin() {

		if (Email_VerifyUtils.isMobileNO(phone_textview.getText().toString().trim())) {
			String mobile = phone_textview.getText().toString().trim();
			String deviceId = AppUtil.getDeviceId(this);
			String password = MD5.GetMD5Code(password_textview.getText().toString().trim());

			if (ZDSharedPreferences.getInstance(this).getJPushRrgistrationId().equals("")) {
				jpid = JPushInterface.getRegistrationID(User_LoginActivity.this);
				if (jpid != null) {
					ZDSharedPreferences.getInstance(this).setJPushRrgistrationId(jpid);
				}
			} else {
				jpid = ZDSharedPreferences.getInstance(this).getJPushRrgistrationId();
			}
			if (jpid == null || jpid.toString().equals("")) {
				T.showShort(this, "参数错误,请重试!");
				// HYBHuoZhuApplication.application.initJpush();
				// 是版本的问题，之前用的是jpush205版本 用jpush210版本就可以了，
				// 因为205会报缺少write_setting 权限，
				// 可是我另一个项目也是用205版本的也是没有这个问题，总之问题解决了，回头再看看另一个项目为什么可以用205版本

				return;
			}
			HttpController controller = new HttpController(User_LoginActivity.this);
			controller.setUserLoginSuccessListener(this);
			controller.userLogin(mobile, deviceId, password, jpid);
		} else {
			T.showLong(this, "手机号格式不正确,请重新输入");
		}

	}

	// 登录成功的回调
	@Override
	public void userLogin(UserLoginBean userLoginBean) {
		if (userLoginBean != null) {
			ZDSharedPreferences.getInstance(this).setHttpHeadToken(userLoginBean.getToken());
			ZDSharedPreferences.getInstance(this).setUserId(userLoginBean.getId());
			ZDSharedPreferences.getInstance(this).setUserMobile(userLoginBean.getMobile());
			ZDSharedPreferences.getInstance(this).setUserName(userLoginBean.getName());
			ZDSharedPreferences.getInstance(this).setUserStatus(userLoginBean.getStatus());
			ZDSharedPreferences.getInstance(this).setUserHead(userLoginBean.getPic());
			ZDSharedPreferences.getInstance(this).setUserCompany(userLoginBean.getCompany());
			if (!ZDSharedPreferences.getInstance(this).getUserId().toString().equals("")) {
				startActivity(new Intent(this, MainActivity.class));
				finish();
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			AppManager.getAppManager().finishAllActivity();
			finish();
			return true;
		}
		return false;
	}
}
