package zhidanhyb.huozhu.View;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zhidanhyb.huozhu.Config.ConstantConfig;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.rechargeMoneyListener;
import zhidanhyb.huozhu.HttpRequest.HttpController.submitWithdrawListener;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.MD5;
import zhidanhyb.huozhu.Utils.ScreenUtils;
import zhidanhyb.huozhu.Utils.T;

/**
 * 输入提现密码
 *
 * @author lxj
 */
public class Withdraw_DepositPassPop extends PopupWindow implements OnClickListener, submitWithdrawListener {

    private Context mContext;
    private List<String> password = new ArrayList<String>();
    private View view;
    private TextView withdraw_deposit_money_textview;
    private EditText input_password_one_edittext;
    private EditText input_password_two_edittext;
    private EditText input_password_three_edittext;
    private EditText input_password_four_edittext;
    private EditText input_password_five_edittext;
    private EditText input_password_six_edittext;
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
    private int Type = 0;//显示密码的类型  1-提现 2-解绑 3-支付运费
    private int position = 1;

    public Withdraw_DepositPassPop(Context context) {

        this.mContext = context;
    }

    /**
     * @param type
     * @param money
     * @param bankCardId
     */
    public void showPop(int type, String money, String bankCardId) {
        view = LayoutInflater.from(mContext).inflate(R.layout.withdraw_depositlayout, null);
        Type = type;
        initView(type, money, bankCardId);
        this.setContentView(view);
        this.setWidth(ScreenUtils.getScreenWidth(mContext));
        this.setHeight(ScreenUtils.getScreenHeight(mContext) - 50);
        this.setBackgroundDrawable(mContext.getResources().getDrawable(R.color.transparent_dialog_color));
        this.setOutsideTouchable(true);
        this.setFocusable(true);
        this.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    /**
     * @param type
     * @param money
     * @param bankCardId
     */
    private void initView(int type, final String money, final String bankCardId) {
        password.clear();
        withdraw_deposit_money_textview = (TextView) view.findViewById(R.id.withdraw_deposit_money_textview);//提现金额
        if (type == 1) {
            if (money.equals("")) {
                withdraw_deposit_money_textview.setText("提现金额：￥0.0");
            } else {
                withdraw_deposit_money_textview.setText("提现金额：￥" + money);

            }
        } else if (type == 2) {
            withdraw_deposit_money_textview.setText("请输入交易密码,解除银行卡绑定");
        } else if (type == 3) {
            withdraw_deposit_money_textview.setText("您正在为订单:" + bankCardId + "\n支付运费金额：￥" + money);
        }
        input_password_one_edittext = (EditText) view.findViewById(R.id.input_password_one_edittext);//密码第一位
        input_password_one_edittext.setKeyListener(null);
        input_password_two_edittext = (EditText) view.findViewById(R.id.input_password_two_edittext);//密码第二位
        input_password_two_edittext.setKeyListener(null);
        input_password_three_edittext = (EditText) view.findViewById(R.id.input_password_three_edittext);//密码第三位
        input_password_three_edittext.setKeyListener(null);
        input_password_four_edittext = (EditText) view.findViewById(R.id.input_password_four_edittext);//密码第四位
        input_password_four_edittext.setKeyListener(null);
        input_password_five_edittext = (EditText) view.findViewById(R.id.input_password_five_edittext);//密码第五位
        input_password_five_edittext.setKeyListener(null);
        input_password_six_edittext = (EditText) view.findViewById(R.id.input_password_six_edittext);//密码第六位
        input_password_six_edittext.setKeyListener(null);
        input_password_one_edittext.setFocusable(true);

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

        input_password_one_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    input_password_two_edittext.setFocusable(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        input_password_two_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    input_password_three_edittext.setFocusable(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        input_password_three_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    input_password_four_edittext.setFocusable(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        input_password_four_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    input_password_five_edittext.setFocusable(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        input_password_five_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    input_password_two_edittext.setFocusable(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        input_password_six_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    if (password.size() == 6) {
                        String pass = "";
                        for (int i = 0; i < password.size(); i++) {
                            pass += password.get(i);
                        }
                        pass = MD5.GetMD5Code(pass);
                        HttpController controller = new HttpController(mContext);
                        controller.setSubmitWithdrawListener(Withdraw_DepositPassPop.this);
                        controller.setSubmitWithdrawListener(Withdraw_DepositPassPop.this);
                        if (Type == 1) {
                            controller.submitWithdraw(bankCardId, money, pass);
                        } else if (Type == 2) {
                            controller.deleteBankCard(bankCardId, pass);
                        } else if (Type == 3) {
                            controller.setRechargeMoneyListener(new rechargeMoneyListener() {
                                @Override
                                public void onRechargeMoney(String Data) {
                                    //交易成功后关闭并发广播刷新订单详情页
                                    Intent intent = new Intent(ConstantConfig.updataOrderDetails);
                                    intent.putExtra("orderid", bankCardId);
                                    mContext.sendBroadcast(intent);
                                    T.showShort(mContext, "支付成功");
                                    dismiss();
                                    successListener.withdrawSuccess();
                                }
                            });
                            controller.OrderPayFreight(bankCardId, "1", pass);//在这里的bankCardId是订单详情页传过来的订单id
                        }
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
        if (password_delete_imageview == v) {
            if (password.size() > 0) {
                if (position > 0 || position == 0) {
                    password.remove(position);
                    deleteView();
                }
            }
            return;
        }
        if (position == 5) {

            return;
        }
        switch (v.getId()) {
            case R.id.password_one_textview:
                password.add("1");
                break;
            case R.id.password_two_textview:
                password.add("2");
                break;
            case R.id.password_three_textview:
                password.add("3");
                break;
            case R.id.password_four_textview:
                password.add("4");
                break;
            case R.id.password_five_textview:
                password.add("5");
                break;
            case R.id.password_six_textview:
                password.add("6");
                break;
            case R.id.password_seven_textview:
                password.add("7");
                break;
            case R.id.password_eight_textview:
                password.add("8");
                break;
            case R.id.password_nine_textview:
                password.add("9");
                break;
            case R.id.password_zero_textview:
                password.add("0");
                break;
            default:
                break;
        }
        setView();
    }

    private void deleteView() {
        switch (position) {
            case 0:
                input_password_one_edittext.setText("");
                break;
            case 1:
                input_password_two_edittext.setText("");
                break;
            case 2:
                input_password_three_edittext.setText("");
                break;
            case 3:
                input_password_four_edittext.setText("");
                break;
            case 4:
                input_password_five_edittext.setText("");
                break;
            case 5:
                input_password_six_edittext.setText("");
                break;
            default:
                break;
        }
        setView();
    }

    private void setView() {
        for (int i = 0; i < password.size(); i++) {
            position = i;
            switch (i) {
                case 0:
                    input_password_one_edittext.setText(password.get(i));
                    break;
                case 1:
                    input_password_two_edittext.setText(password.get(i));
                    break;
                case 2:
                    input_password_three_edittext.setText(password.get(i));
                    break;
                case 3:
                    input_password_four_edittext.setText(password.get(i));
                    break;
                case 4:
                    input_password_five_edittext.setText(password.get(i));
                    break;
                case 5:
                    input_password_six_edittext.setText(password.get(i));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void submitWithdraw() {
        dismiss();
        successListener.withdrawSuccess();
    }

    private withdrawSuccessListener successListener;

    public void setWithdrawSuccessListener(withdrawSuccessListener wListener) {
        this.successListener = wListener;
    }

    public interface withdrawSuccessListener {
        void withdrawSuccess();
    }
}



