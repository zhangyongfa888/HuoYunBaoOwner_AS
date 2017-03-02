package zhidanhyb.huozhu.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.Selection;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import zhidanhyb.huozhu.Activity.Me.Me_BankCardActivity;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.submitPasswordListener;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.MD5;
import zhidanhyb.huozhu.Utils.ScreenUtils;
import zhidanhyb.huozhu.Utils.T;

/**
 * 设置初始提现密码
 *
 * @author lxj
 */
public class Withdraw_PasswordSettingPop extends PopupWindow implements OnClickListener, submitPasswordListener {

    private Context mContext;
    private View view;
    private EditText password_edittext;
    private EditText passwords_edittext;
    private Button submit_button;
    private TextView title_textview;
    private TextView password_one_textview;
    private TextView password_two_textview;
    private TextView password_three_textview;
    private TextView password_four_textview;
    private TextView password_five_textview;
    private TextView password_six_textview;
    private TextView password_seven_textview;
    private TextView password_eight_textview;
    private TextView password_nine_textview;
    private TextView password_zero_textview;
    private ImageView password_delete_imageview;
    private boolean ispassword = true;
    private List<String> password = new ArrayList<String>();
    private List<String> passwords = new ArrayList<String>();
    private int position = 0;
    private int positions = 0;
    private Method method;
    private int Type = 0;//1-提现设置密码 2-支付运费设置密码

    public Withdraw_PasswordSettingPop(Context context) {
        this.mContext = context;
    }

    public void showPop(int type) {
        Type = type;
        view = LayoutInflater.from(mContext).inflate(R.layout.bankcardpasswordlayout, null);
        initView();
        setContentView(view);
        setWidth(ScreenUtils.getScreenWidth(mContext));
        setHeight(ScreenUtils.getScreenHeight(mContext) - ScreenUtils.getStatusHeight(mContext));
        setBackgroundDrawable(mContext.getResources().getDrawable(R.color.transparent_dialog_color));
        this.setOutsideTouchable(false);
        this.setFocusable(true);
        this.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    /**
     *
     */
    private void initView() {
        final LinearLayout soft_pop = (LinearLayout) view.findViewById(R.id.soft_pop);
        view.findViewById(R.id.other_area).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                soft_pop.setVisibility(View.GONE);
            }
        });
        view.findViewById(R.id.title_left_button).setVisibility(View.GONE);
        view.findViewById(R.id.title_right_button).setVisibility(View.GONE);
        title_textview = (TextView) view.findViewById(R.id.title_textview);
        if (Type == 1) {
            title_textview.setText(R.string.bankcardpasswordsetting);
        } else if (Type == 2) {
            title_textview.setText(R.string.paypaw);
        }
        password_edittext = (EditText) view.findViewById(R.id.backcard_password_edittext);//密码
        passwords_edittext = (EditText) view.findViewById(R.id.backcard_passwords_edittext);//重复密码
        submit_button = (Button) view.findViewById(R.id.backcard_submit_button);//提交
        submit_button.setOnClickListener(this);
        password_edittext.setOnTouchListener(new OnTouchListener() {
            /**
             * @param v
             * @param event
             * @return
             */
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ispassword = true;
                soft_pop.setVisibility(View.VISIBLE);
                try {
                    method.invoke(password_edittext, false);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        passwords_edittext.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ispassword = false;
                soft_pop.setVisibility(View.VISIBLE);
                method.setAccessible(false);
                try {
                    method.invoke(passwords_edittext, false);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } // editText是声明的输入文本框。
                return false;
            }
        });
        // 点击EditText，隐藏系统输入法
        ((Activity) mContext).getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            method = cls.getMethod("setShowSoftInputOnFocus",
                    boolean.class);// 4.0的是setShowSoftInputOnFocus，4.2的是setSoftInputShownOnFocus
            method.setAccessible(false);
            method.invoke(password_edittext, false); // editText是声明的输入文本框。
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        password_one_textview = (TextView) view.findViewById(R.id.password_one_textview);//数字1
        password_one_textview.setOnClickListener(this);
        password_two_textview = (TextView) view.findViewById(R.id.password_two_textview);//数字2
        password_two_textview.setOnClickListener(this);
        password_three_textview = (TextView) view.findViewById(R.id.password_three_textview);//数字3
        password_three_textview.setOnClickListener(this);
        password_four_textview = (TextView) view.findViewById(R.id.password_four_textview);//数字4
        password_four_textview.setOnClickListener(this);
        password_five_textview = (TextView) view.findViewById(R.id.password_five_textview);//数字5
        password_five_textview.setOnClickListener(this);
        password_six_textview = (TextView) view.findViewById(R.id.password_six_textview);//数字6
        password_six_textview.setOnClickListener(this);
        password_seven_textview = (TextView) view.findViewById(R.id.password_seven_textview);//数字7
        password_seven_textview.setOnClickListener(this);
        password_eight_textview = (TextView) view.findViewById(R.id.password_eight_textview);//数字8
        password_eight_textview.setOnClickListener(this);
        password_nine_textview = (TextView) view.findViewById(R.id.password_nine_textview);//数字9
        password_nine_textview.setOnClickListener(this);
        password_zero_textview = (TextView) view.findViewById(R.id.password_zero_textview);//数字0
        password_zero_textview.setOnClickListener(this);
        password_delete_imageview = (ImageView) view.findViewById(R.id.password_delete_imageview);//删除
        password_delete_imageview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (password_delete_imageview == v) {
            if (ispassword) {
                if (password.size() == 0) {
                    return;
                }
                password.remove(position);
            } else {
                if (passwords.size() == 0) {
                    return;
                }
                passwords.remove(positions);
            }
            setView();
            return;
        }
        String number = "";
        switch (v.getId()) {
            case R.id.backcard_submit_button:
                if (isEmpty()) {
                    submitPassword();
                }
                break;
            case R.id.password_one_textview:
                number = "1";
                break;
            case R.id.password_two_textview:
                number = "2";
                break;
            case R.id.password_three_textview:
                number = "3";
                break;
            case R.id.password_four_textview:
                number = "4";
                break;
            case R.id.password_five_textview:
                number = "5";
                break;
            case R.id.password_six_textview:
                number = "6";
                break;
            case R.id.password_seven_textview:
                number = "7";
                break;
            case R.id.password_eight_textview:
                number = "8";
                break;
            case R.id.password_nine_textview:
                number = "9";
                break;
            case R.id.password_zero_textview:
                number = "0";
                break;
            default:
                break;
        }
        if (ispassword) {
            if (position <= 5) {
                password.add(number);
            }
        } else {
            if (positions <= 5) {
                passwords.add(number);
            }
        }
        setView();
    }

    private void setView() {
        String s = "";
        if (ispassword) {
            for (int i = 0; i < password.size(); i++) {
                s += password.get(i);
                position = i;
            }
            password_edittext.setText(s);
            Editable etext = password_edittext.getText();
            Selection.setSelection(etext, etext.length());
        } else {
            for (int i = 0; i < passwords.size(); i++) {
                positions = i;
                s += passwords.get(i);
            }
            passwords_edittext.setText(s);
            Editable etext = passwords_edittext.getText();
            Selection.setSelection(etext, etext.length());

        }
    }

    private boolean isEmpty() {
        boolean is = true;
        if (password_edittext.getText().toString().trim().isEmpty()) {
            is = false;
            T.showLong(mContext, "请输入密码!");

        } else if (passwords_edittext.getText().toString().trim().isEmpty()) {
            is = false;
            T.showLong(mContext, "请重复密码!");
        } else if (!password_edittext.getText().toString().trim().equals(passwords_edittext.getText().toString().trim())) {
            is = false;
            password_edittext.setText("");
            passwords_edittext.setText("");
            password.clear();
            passwords.clear();
            ispassword = false;
            passwords_edittext.setFocusable(true);
            T.showLong(mContext, "两次密码不一致,请重新输入!");
        }
        return is;
    }

    private void submitPassword() {
        HttpController controller = new HttpController(mContext);
        String dealpwd = MD5.GetMD5Code(password_edittext.getText().toString().trim());
        controller.setSubmitPasswordListener(this);
        controller.submitPassword("2", dealpwd);
    }

    @Override
    public void onSubmitPassword() {
        dismiss();
        T.showShort(mContext, "设置成功");
        if (Type == 1) {
            Intent intent = new Intent();
            intent.putExtra("type", "1");
            intent.setClass(mContext, Me_BankCardActivity.class);
            mContext.startActivity(intent);
        } else if (Type == 2) {//不做任何操作
            if (miListener != null) {
                miListener.onsuccess(OrderId, OrderPrice);
            }
        }
    }

    private onSubmitPassowrdListener miListener;

    public void setOnSubmitPasswrodListener(onSubmitPassowrdListener submitPassowrdListener) {
        this.miListener = submitPassowrdListener;
    }

    public interface onSubmitPassowrdListener {
        void onsuccess(String orderid, String orderprice);
    }

    private String OrderPrice = null;
    private String OrderId = null;

    public void setOrderInfor(String price, String orderid) {
        OrderPrice = price;
        OrderId = orderid;
    }
}
