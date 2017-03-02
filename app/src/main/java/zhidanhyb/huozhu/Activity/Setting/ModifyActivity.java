package zhidanhyb.huozhu.Activity.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.R;

/**
 *
 */
public class ModifyActivity extends BaseActivity {

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        setHideRightButton();
        setLeftButton();
        setTitleText("修改密码");
        findViewById(R.id.setting_modify_pwd_login).setOnClickListener(this);
        findViewById(R.id.setting_modify_pwd_pay).setOnClickListener(this);
    }

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, ModifyPwdActivity.class);
        super.onClick(v);
        switch (v.getId()) {
            case R.id.setting_modify_pwd_login:
                intent.putExtra("type", "1");
                startActivity(intent);
                break;
            case R.id.setting_modify_pwd_pay:
                intent.putExtra("type", "2");
                startActivity(intent);
                break;
            default:
                break;

        }
    }
}
