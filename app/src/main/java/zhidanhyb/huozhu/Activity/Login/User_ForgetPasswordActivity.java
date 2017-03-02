package zhidanhyb.huozhu.Activity.Login;

import android.app.Dialog;
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
 * 忘记密码
 *
 * @author lxj
 */
public class User_ForgetPasswordActivity extends BaseActivity implements getVerifyCodeListener {

    private EditText phone_edittext;
    private EditText code_edittext;
    /**
     *
     */
    private TextView code_button;
    private Button forgetpassword_button;
    int i = 59;
    private HttpController httpController;
    private int x;
    private int y;
    private Register_User_Hint_Dialog dialog;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.user_forgetpasswordlayout);
        initView();
        addListener();
    }

    /**
     *
     */
    private void initView() {
        setTitleText(R.string.getpassword);
        setLeftButton();
        setHideRightButton();
        phone_edittext = (EditText) findViewById(R.id.forgetpassword_phone_edittext);//手机号输入框
        code_edittext = (EditText) findViewById(R.id.forgetpassword_code_edittext);//验证码输入框
        code_button = (TextView) findViewById(R.id.forgetpassword_code_button);//获取验证码
        code_button.setOnClickListener(this);
        forgetpassword_button = (Button) findViewById(R.id.forgetpassword_button);//忘记密码按钮
        forgetpassword_button.setOnClickListener(this);
        httpController = new HttpController(this);
        httpController.setGetVerifyCodeListener(this);
    }

    Dialog d;

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
//        super.onClick(v);
        switch (v.getId()) {
            case R.id.title_left_rl:
                //返回
//                d = DialogUtils.showDialogBack(this, true, "提示", "确认放弃？", "返回", "确认", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        d.dismiss();
//                    }
//                }, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        d.dismiss();
//                        finish();
//                    }
//                });
                finish();
                break;
            case R.id.forgetpassword_code_button://获取验证码
                getVerfiyCode();
                break;
            case R.id.forgetpassword_button://忘记密码按钮
                submit();
                break;
            default:
                break;
        }
    }

    /**
     *
     */
    private void addListener() {
        code_button.postDelayed(new Runnable() {
            /**
             *
             */
            @Override
            public void run() {
                //首次注册登陆时提示信息
                dialog = new Register_User_Hint_Dialog(User_ForgetPasswordActivity.this, "", x, y);
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
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            int[] location = new int[2];
            code_button.getLocationOnScreen(location);
            x = location[0];
            y = location[1];
        }
    }

    private void submit() {
        if (phone_edittext.getText().toString().trim().isEmpty()) {
            T.showLong(this, "请输入手机号!");
            return;
        }
        if (code_edittext.getText().toString().trim().isEmpty()) {
            T.showLong(this, "请输入验证码!");
            return;
        }
        String mobile = phone_edittext.getText().toString().trim();
        String deviceId = AppUtil.getDeviceId(this);
        String code = code_edittext.getText().toString().trim();
        httpController.verifyCode(mobile, deviceId, code, "2");
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
            httpController.getVerifyCode(mobile, deviceId, "2", "2");
        } else {
            T.showLong(this, "手机号格式不正确,请修改");
        }
    }

    //验证码获取成功
    @Override
    public void getCode() {
        if (code_button == null) {
            return;
        }
        code_button.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (i == 1) {
                    i = 59;
                    if (code_button != null) {
                        code_button.setClickable(true);
                        code_button.setText("重新获取");
                    }
                    return;
                }
                i--;
                if (code_button != null) {
                    code_button.setText(i + "秒");
                    code_button.setClickable(false);
                    code_button.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }

    //提交成功
    @Override
    public void getRegister() {
        Intent intent = new Intent(this, User_Reset_PasswordActivity.class);
        intent.putExtra("phone", phone_edittext.getText().toString().trim());
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        phone_edittext = null;
        code_edittext = null;
        code_button = null;
        forgetpassword_button = null;
        if (httpController != null) {
            httpController = null;
        }
        if (dialog != null) {
            dialog = null;
        }
    }
}
