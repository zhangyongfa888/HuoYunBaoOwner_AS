package zhidanhyb.huozhu.Activity.Setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.MD5;
import zhidanhyb.huozhu.Utils.StringUtil;
import zhidanhyb.huozhu.Utils.T;
import zhidanhyb.huozhu.Utils.UserExitUtils;

/**
 *
 */
public class ModifyPwdActivity extends BaseActivity implements HttpController.modifyPwd {
    private String type = "";
    private EditText v2_edit_old_pwd, v2_edit_new_pwd, v2_edit_confirm_pwd;
    private Button v2_modify_cancel, v2_modify_confirm;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);
        type = getIntent().getStringExtra("type");
        setHideRightButton();
        setLeftButton();
        setTitleText(type.equals("1") ? "登录密码" : "支付密码");
        v2_edit_old_pwd = (EditText) findViewById(R.id.v2_edit_old_pwd);
        v2_edit_new_pwd = (EditText) findViewById(R.id.v2_edit_new_pwd);
        v2_edit_confirm_pwd = (EditText) findViewById(R.id.v2_edit_confirm_pwd);
        v2_modify_cancel = (Button) findViewById(R.id.v2_modify_cancel);
        v2_modify_confirm = (Button) findViewById(R.id.v2_modify_confirm);
        v2_modify_cancel.setOnClickListener(this);
        v2_modify_confirm.setOnClickListener(this);
    }

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        super.onClick(v);


        switch (v.getId()) {
            case R.id.v2_modify_cancel:
                finish();
                break;
            case R.id.v2_modify_confirm:
                if (StringUtil.isEmpty(v2_edit_old_pwd.getText().toString())) {
                    T.showShort(context, "请输入旧密码");
                    return;
                }
                if (StringUtil.isEmpty(v2_edit_new_pwd.getText().toString())) {
                    T.showShort(context, "请输入新密码");
                    return;
                }
                if (StringUtil.isEmpty(v2_edit_confirm_pwd.getText().toString())) {
                    T.showShort(context, "请确认密码");
                    return;
                }
                if (!v2_edit_confirm_pwd.getText().toString().equals(v2_edit_new_pwd.getText().toString())) {
                    T.showShort(context, "密码不一致！");
                    return;
                }
                HttpController controller = new HttpController(context);
                controller.exeModifyPwd(type, MD5.GetMD5Code(v2_edit_old_pwd.getText().toString()), MD5.GetMD5Code(v2_edit_new_pwd.getText().toString()));
                controller.setOnModifyPwdListener(this);
                break;
        }
    }

    /**
     *
     */
    @Override
    public void modifyBack() {
        if (type.equals("1")) {
            new UserExitUtils(context).forcedExit();
        } else {
            finish();
        }
    }
}
