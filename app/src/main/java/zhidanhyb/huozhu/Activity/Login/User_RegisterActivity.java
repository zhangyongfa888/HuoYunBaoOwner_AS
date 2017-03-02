package zhidanhyb.huozhu.Activity.Login;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;

import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.userRegisterListener;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.AppUtil;
import zhidanhyb.huozhu.Utils.MD5;
import zhidanhyb.huozhu.Utils.T;
import zhidanhyb.huozhu.View.DialogUtils;

/**
 * 用户注册编辑信息
 *
 * @author lxj
 */
public class User_RegisterActivity extends BaseActivity implements userRegisterListener {


    private EditText passwrod_edittext;
    private EditText passwrod_again_edittext;
    private EditText name_edittext;
    private RadioGroup named_radiogroup;
    private Button register_button;
    /**
     *
     */
    private CheckBox sirradio;
    private int checked = Color.rgb(0, 0, 0);
    private int nocheck = Color.rgb(221, 221, 221);
    /**
     *
     */
    private CheckBox madomradio;
    private String Phone = null;
    private int userSex = 1;//1男0女  默认是“先生”
    private EditText company_edittext;
    private String Company;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.user_registerlayout);
        initView();
    }

    /**
     *
     */
    private void initView() {
        setHideRightButton();
        setLeftButton();
        setTitleText(R.string.register);
        passwrod_edittext = (EditText) findViewById(R.id.register_passwrod_edittext);//密码
        passwrod_again_edittext = (EditText) findViewById(R.id.register_passwrod_again_edittext);//确认密码
        name_edittext = (EditText) findViewById(R.id.register_name_edittext);//名字
        named_radiogroup = (RadioGroup) findViewById(R.id.register_named_radiogroup);//称呼
        sirradio = (CheckBox) findViewById(R.id.register_named_sir_radiobutton);//先生
        madomradio = (CheckBox) findViewById(R.id.register_named_madam_radiobutton);//女士
        company_edittext = (EditText) findViewById(R.id.register_company_edittext);//企业名称
        register_button = (Button) findViewById(R.id.register_button);//编辑完成
        register_button.setOnClickListener(this);
        sirradio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**
             * @param buttonView
             * @param isChecked
             */
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sirradio.setChecked(true);
                    madomradio.setChecked(false);
                    userSex = 1;
                }
            }
        });
        madomradio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**
             * @param buttonView
             * @param isChecked
             */
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sirradio.setChecked(false);
                    madomradio.setChecked(true);
                    userSex = 0;
                }
            }
        });
//		named_radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {//选择性别
//
//			/**
//			 * @param group
//			 * @param checkedId
//			 */
//			@Override
//			public void onCheckedChanged(RadioGroup group, int checkedId) {
//				switch (checkedId) {
//				case R.id.register_named_sir_radiobutton://先生
////					sirradio.setTextColor(checked);
//					sirradio.setChecked(true);
//					madomradio.setChecked(false);
////					madomradio.setTextColor(nocheck);
//					userSex = 1;
//
//					break;
//				case R.id.register_named_madam_radiobutton://女士
////					sirradio.setTextColor(nocheck);
////					madomradio.setTextColor(checked);
//					sirradio.setChecked(false);
//					madomradio.setChecked(true);
//					userSex = 0;
//					break;
//				default:
//					break;
//				}
//			}
//		});
        Phone = getIntent().getExtras().getString("Phone");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.title_left_rl://返回
                DialogUtils.showDialogBack(this, true, "提示", "确认放弃", "返回", "确认", null, new DialogUtils.setOnDialogRightButtonClick() {
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
            case R.id.register_button:////编辑完成
                if (isEmpty())
                    editAccomplish();
                break;
            default:
                break;
        }
    }

    private boolean isEmpty() {
        boolean is = true;
        if (passwrod_edittext.getText().toString().isEmpty()) {
            T.showShort(this, "请输入密码");
            is = false;
            return is;
        }
        if (passwrod_again_edittext.getText().toString().isEmpty()) {
            T.showShort(this, "请再次输入密码");
            is = false;
            return is;
        }
        if (!passwrod_edittext.getText().toString().trim().equals(passwrod_again_edittext.getText().toString().trim())) {
            T.showShort(this, "密码不一致,请修改");
            is = false;
            return is;
        }
        if (name_edittext.getText().toString().isEmpty()) {
            T.showShort(this, "请输入姓名");
            is = false;
            return is;
        }
        if (company_edittext.getText().toString().trim().isEmpty()) {
            Company = "";
        } else {
            Company = company_edittext.getText().toString().trim();
        }
        return is;
    }

    /**
     * 编辑完成操作
     */
    private void editAccomplish() {
        HttpController controller = new HttpController(this);
        controller.setUserRegisterListener(this);
        if (Phone == null) {
            T.showShort(this, "手机号码为空,请返回重试!");
            return;
        }
        String mobile = Phone;
        String deviceId = AppUtil.getDeviceId(this);
        String name = name_edittext.getText().toString().trim();
        String sex = userSex + "";
        String password = MD5.GetMD5Code(passwrod_edittext.getText().toString().trim());
        String company = Company;
        controller.userRegister(mobile, deviceId, name, sex, password, company);
    }

    //编辑完成注册成功的回调-并返回到登录页面
    @Override
    public void Register() {
        User_LoginActivity.isRegisterPhone = Phone;
        User_LoginActivity.isRegister = true;
        finish();
    }
}
