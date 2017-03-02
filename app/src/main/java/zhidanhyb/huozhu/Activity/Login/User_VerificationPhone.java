package zhidanhyb.huozhu.Activity.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.getVerifyCodeListener;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.AppUtil;
import zhidanhyb.huozhu.Utils.Email_VerifyUtils;
import zhidanhyb.huozhu.Utils.T;
import zhidanhyb.huozhu.View.Register_User_Hint_Dialog;
import zhidanhyb.huozhu.View.Register_User_Hint_Dialog.Registeruserlistener;

/**
 * 用户验证手机号
 *
 * @author lxj
 */
public class User_VerificationPhone extends BaseActivity implements getVerifyCodeListener {

    private EditText phone_edittext;
    private EditText code_edittext;
    /**
     *
     */
    private TextView getcode_button;
    private Button verify_button;
    private HttpController httpController;
    int i = 59;
    private int x;
    private int y;
    private Register_User_Hint_Dialog dialog;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.user_verifyphonelayout);
        initView();
        addListener();
    }

    /**
     *
     */
    private void initView() {
        setTitleText(R.string.verifyphone);
        setHideRightButton();
        setLeftButton();
        phone_edittext = (EditText) findViewById(R.id.verify_phone_edittext);//手机号
        code_edittext = (EditText) findViewById(R.id.verify_code_edittext);//验证码
        getcode_button = (TextView) findViewById(R.id.verifygetcode_button);//获取验证码
        getcode_button.setOnClickListener(this);
        verify_button = (Button) findViewById(R.id.verify_button);//注册
        verify_button.setOnClickListener(this);
        httpController = new HttpController(this);
        httpController.setGetVerifyCodeListener(this);


//        ViewUtils.setViewSize(context, findViewById(R.id.v2_ll_phonenum), 640, 91);
//        ViewUtils.setViewSize(context, findViewById(R.id.v2_ll_phonecode), 640, 91);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            int[] location = new int[2];
            getcode_button.getLocationOnScreen(location);
            x = location[0];
            y = location[1];
        }
    }

    private void addListener() {
        getcode_button.postDelayed(new Runnable() {


            @Override
            public void run() {
                //首次注册登陆时提示信息
                dialog = new Register_User_Hint_Dialog(User_VerificationPhone.this, "", x, y);
                Registeruserlistener liRegisteruserlistener = new Registeruserlistener() {
                    @Override
                    public void onclick() {
                        getVerfiyCode();
                    }
                };
                dialog.setRegisterUserListener(liRegisteruserlistener);
            }
        }, 500);
        phone_edittext.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11) {
                    if (dialog != null) {
                        dialog.showDialog();
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    @Override
    public void onClick(View v) {
//		super.onClick(v);
        switch (v.getId()) {
            case R.id.verifygetcode_button://获取验证码
                getVerfiyCode();
                break;
            case R.id.verify_button://注册
                Register();
                break;
            case R.id.title_left_rl://返回
                finish();
                break;
            default:
                break;
        }
    }

    //验证验证码
    private void Register() {
        if (phone_edittext.getText().toString().trim().isEmpty()) {
            T.showLong(this, "请输入手机号!");
            return;
        }
        if (code_edittext.getText().toString().trim().isEmpty()) {
            T.showLong(this, "请输验证码!");
            return;
        }
        if (httpController == null) {
            return;
        }
        String mobile = phone_edittext.getText().toString().trim();
        String deviceId = AppUtil.getDeviceId(this);
        String code = code_edittext.getText().toString().trim();
        httpController.verifyCode(mobile, deviceId, code, "1");
    }

    //获取验证码
    private void getVerfiyCode() {
        if (phone_edittext.getText().toString().trim().isEmpty()) {
            T.showLong(this, "请输入手机号!");
            return;
        }
        if (httpController == null) {
            return;
        }
        if (Email_VerifyUtils.isMobileNO(phone_edittext.getText().toString().trim())) {
            String mobile = phone_edittext.getText().toString().trim();
            String deviceId = AppUtil.getDeviceId(this);
            httpController.getVerifyCode(mobile, deviceId, "2", "1");
        } else {
            T.showLong(this, "手机号格式不正确,请修改");


        }
    }

    //获取验证码成功的回调
    @Override
    public void getCode() {
        if (getcode_button == null) {
            return;
        }
        getcode_button.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (i == 1) {
                    i = 59;
                    if (getcode_button != null) {
                        getcode_button.setClickable(true);
                        getcode_button.setText("重新获取");
                    }
                    return;
                }
                i--;
                if (getcode_button != null) {
                    getcode_button.setText(i + "秒");
                    getcode_button.setClickable(false);
                    getcode_button.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }

    //验证验证码成功的回调-并跳转到填写用户信息页面
    @Override
    public void getRegister() {

        Intent intent = new Intent(this, User_RegisterActivity.class);
        intent.putExtra("Phone", phone_edittext.getText().toString().trim());
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        phone_edittext = null;
        code_edittext = null;
        getcode_button = null;
        verify_button = null;
        if (httpController != null) {
            httpController = null;
        }
        if (dialog != null) {
            dialog = null;
        }
    }
}
