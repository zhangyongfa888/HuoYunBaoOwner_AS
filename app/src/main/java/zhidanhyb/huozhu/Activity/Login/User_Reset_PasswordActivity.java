package zhidanhyb.huozhu.Activity.Login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.getUserResetPasswordListener;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.MD5;
import zhidanhyb.huozhu.Utils.T;
import zhidanhyb.huozhu.View.DialogUtils;

/**
 * 重置密码
 *
 * @author lxj
 */
public class User_Reset_PasswordActivity extends BaseActivity {
    private EditText passwrod_edittext;
    private EditText passwrod_again_edittext;
    private Button forget_accomplish;
    private String Phone = null;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.user_resetpasswordlayout);
        initView();
    }

    private void initView() {
        setHideRightButton();
        setLeftButton();
        setTitleText(R.string.forgetpassword);
        passwrod_edittext = (EditText) findViewById(R.id.forget_passwrod_edittext);//密码
        passwrod_again_edittext = (EditText) findViewById(R.id.forget_passwrod_again_edittext);//再次输入密码
        forget_accomplish = (Button) findViewById(R.id.forget_accomplish);//完成
        forget_accomplish.setOnClickListener(this);
        Phone = getIntent().getExtras().getString("phone");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left_rl://返回

                DialogUtils.showDialogBack(this, true, "提示", "确认放弃？", "返回", "确认", null, new DialogUtils.setOnDialogRightButtonClick() {
                    /**
                     * @param v
                     */
                    @Override
                    public void setOnClickRight(View v) {
                        finish();
                    }
                });

//			User_Cancle_RegisterDialog dialog = new User_Cancle_RegisterDialog(this, R.style.dialog);
//			dialog.setOnCancleRegisterListener(new onCancleRegisterListener() {
//				@Override
//				public void onSure() {
//					finish();
//				}
//			});
//			dialog.showDialog();
                break;
            case R.id.forget_accomplish:
                submit();
                break;
            default:
                break;
        }
    }

    /**
     *
     */
    //提交
    private void submit() {
        if (passwrod_edittext.getText().toString().trim().isEmpty()) {
            T.showShort(this, "密码不能为空!");
            return;
        }
        if (passwrod_again_edittext.getText().toString().trim().isEmpty()) {
            T.showShort(this, "密码不能为空!");
            return;
        }

        if (!passwrod_edittext.getText().toString().trim().equals(passwrod_again_edittext.getText().toString().trim())) {

            DialogUtils.showDialogBack(context, false, "输入错误", context.getResources().getString(R.string.inputhint), "返回");

//            ForgetPassword_MistakeDialog forgetdialog = new ForgetPassword_MistakeDialog(this, R.style.dialog);
//            forgetdialog.showDialog();
            return;
        }
        if (Phone == null) {
            T.showShort(this, "手机号为空");
            return;
        }
        HttpController controller = new HttpController(this);
        String phone = Phone;
        String password = MD5.GetMD5Code(passwrod_edittext.getText().toString().trim());
        String type = "2";//类型（1司机端 2货主端）
        controller.setGetUserResetPasswordListener(new getUserResetPasswordListener() {

            @Override
            public void ResetPassword() {
                User_LoginActivity.isRegister = true;
                User_LoginActivity.isRegisterPhone = Phone;
                finish();
            }
        });
        controller.User_ResetPassword(phone, password, type);

    }
}
