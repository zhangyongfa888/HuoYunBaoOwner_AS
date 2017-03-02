/**
 *
 */
package zhidanhyb.huozhu.Activity.Me;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.Config.ZDSharedPreferences;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.getUserInfo;
import zhidanhyb.huozhu.HttpRequest.HttpController.userRefreshPersonInfo;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.T;
import zhidanhyb.huozhu.View.DialogUtils;

/**
 * @author 张永发 编辑个人信息
 */
public class UserEditPersonInfoActivity extends BaseActivity implements userRefreshPersonInfo, getUserInfo {
    private EditText name_edittext;
    private Button register_button;
    private CheckBox sirradio;
    /**
     *
     */
    private CheckBox madomradio;
    private int userSex = 1;// 1男0女 默认是“先生”
    private EditText company_edittext;
    private String Company;
    /**
     *
     */
    private RadioGroup register_named_radiogroup;
    /**
     *
     */
private TextView v2_me_sex;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_edit_personinfo);
        initView();
    }

    /**
     *
     */
    private void initView() {
        setRightText("编辑");
        setLeftButton();
        setTitleText(R.string.personinfo);

        name_edittext = (EditText) findViewById(R.id.register_name_edittext);// 名字
        sirradio = (CheckBox) findViewById(R.id.register_named_sir_radiobutton);// 先生
        madomradio = (CheckBox) findViewById(R.id.register_named_madam_radiobutton);// 女士
        company_edittext = (EditText) findViewById(R.id.register_company_edittext);// 企业名称
        register_button = (Button) findViewById(R.id.register_button);// 编辑完成
        v2_me_sex= (TextView) findViewById(R.id.v2_me_sex);
        register_button.setOnClickListener(this);
        getRightText().setOnClickListener(this);
        name_edittext.setEnabled(false);
        sirradio.setEnabled(false);
        madomradio.setEnabled(false);
        company_edittext.setEnabled(false);
        register_button.setVisibility(View.GONE);
        register_named_radiogroup= (RadioGroup) findViewById(R.id.register_named_radiogroup);
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


        HttpController controller = new HttpController(this);
        controller.setUserInfo(this);
        controller.getUserInfo();


    }


    /**
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.title_left_rl://返回
                if (register_button.getVisibility() == View.VISIBLE) {
                    DialogUtils.showDialogBack(this, true, "提示", "确认放弃", "返回", "确认", null, new DialogUtils.setOnDialogRightButtonClick() {
                        /**
                         * @param v
                         */
                        @Override
                        public void setOnClickRight(View v) {
                            finish();
                        }
                    });
                } else {
                    finish();
                }


//			User_Cancle_RegisterDialog dialog = new User_Cancle_RegisterDialog(this, R.style.dialog);
//			dialog.setOnCancleRegisterListener(new onCancleRegisterListener() {
//				@Override
//				public void onSure() {
//					finish();
//				}
//			});
//			dialog.showDialog();
                break;
            case R.id.title_right_textview:
                if (getRightText().getText().toString().equals("编辑")) {
                    setRightText("");
                    setEditable();
                }

                break;
            case R.id.register_button:////提交
                if (isEmpty())
                    editAccomplish();
                break;
            default:
                break;
        }
    }

    /**
     *
     */
    private void setEditable() {
        name_edittext.setEnabled(true);
        sirradio.setEnabled(true);
        madomradio.setEnabled(true);
        company_edittext.setEnabled(true);
        register_button.setVisibility(View.VISIBLE);
        register_named_radiogroup.setVisibility(View.VISIBLE);
        v2_me_sex.setVisibility(View.GONE);
    }

    /**
     * 编辑完成操作
     */
    private void editAccomplish() {
        HttpController controller = new HttpController(this);
        controller.setUserRefreshPersonInfo(this);
        String name = name_edittext.getText().toString().trim();
        String sex = userSex + "";
        controller.UserRefreshPersonInfo(name, sex, company_edittext.getText().toString());
    }

    private boolean isEmpty() {
        boolean is = true;

        if (name_edittext.getText().toString().isEmpty()) {
            T.showShort(this, "请输入姓名");
            is = false;
        }
//		if (company_edittext.getText().toString().isEmpty()) {
//			T.showShort(this, "请输入公司名称");
//			is = false;
//		}

        return is;
    }

    @Override
    public void refreshInfo() {
        // 更新信息
        ZDSharedPreferences.getInstance(this).setUserName(name_edittext.getText().toString());
        ZDSharedPreferences.getInstance(this).setUserCompany(company_edittext.getText().toString());
        setResult(RESULT_OK);
        finish();
    }

    /**
     * @param name
     * @param sex
     * @param company
     */
    @Override
    public void getInfo(String name, String sex, String company) {
        name_edittext.setText(name);
        company_edittext.setText(company);
        company_edittext.setSelection(company.length());


        if (sex.equals("1")) {
            sirradio.setChecked(true);
            madomradio.setChecked(false);
            v2_me_sex.setText("先生");

        } else {
            madomradio.setChecked(true);
            sirradio.setChecked(false);
            v2_me_sex.setText("女士");
        }
        register_named_radiogroup.setVisibility(View.GONE);

    }

}
