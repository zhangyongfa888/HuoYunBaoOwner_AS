package zhidanhyb.huozhu.Activity.Me;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import zhidanhyb.huozhu.Adapter.BankCardAdapter;
import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.Bean.BankListBean;
import zhidanhyb.huozhu.Bean.UserGoldBean;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.getBankListListener;
import zhidanhyb.huozhu.HttpRequest.HttpController.getUserGoldListener;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.StringUtil;
import zhidanhyb.huozhu.Utils.T;
import zhidanhyb.huozhu.View.Swipemenulistview.SwipeMenu;
import zhidanhyb.huozhu.View.Swipemenulistview.SwipeMenuCreator;
import zhidanhyb.huozhu.View.Swipemenulistview.SwipeMenuItem;
import zhidanhyb.huozhu.View.Swipemenulistview.SwipeMenuListView;
import zhidanhyb.huozhu.View.Swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import zhidanhyb.huozhu.View.Swipemenulistview.SwipeMenuListView.SwipeMenuListViewListener;
import zhidanhyb.huozhu.View.Withdraw_DepositPassPop;
import zhidanhyb.huozhu.View.Withdraw_DepositPassPop.withdrawSuccessListener;

/**
 * 银行卡
 *
 * @author lxj
 */
public class Me_BankCardActivity extends BaseActivity implements SwipeMenuListViewListener, getBankListListener, getUserGoldListener {

    private SwipeMenuListView bankcard_listview;
    private LinearLayout bankcard_add_linearlayout;
    private LinearLayout add_banckcard_linearlayout;
    private EditText add_bankcard_name;
    private EditText add_bankcard_user;
    private EditText add_bankcard_number;
    /**
     *
     */
    private TextView add_bankcard_button, add_bankcard_cancle_button;
    private TextView bankcard_name_textview;
    private TextView bankcard_user_textview;
    private TextView bankcard_number_textview;
    private EditText withdraw_money_edittext;
    private TextView withdraw_balance_textview;
    private TextView withdraw_all_textview;
    private Button withdraw_button;
    private LinearLayout withdraw_linearlayout;
    private HttpController controller;
    private List<BankListBean> banckList;
    private String balance = null;//用户余额
    private String bankCardId = null;//银行卡iD
    private View addview;
    private View withdrawview;
    private String type;//type = 1 不能有删除和添加银行卡操作，只有提现操作.type = 2 不能有提现操作，只有删除和添加银行卡操作
    private BankCardAdapter bankCardAdapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.me_bankcardlayout);
        initView();
        getData();
    }

    private void getData() {
        controller = new HttpController(this);
        controller.setGetBankListListener(this);
        controller.getBankList();
    }

    private void initView() {
        type = getIntent().getExtras().getString("type");
        setLeftButton();
        setHideRightButton();
        setTitleText(R.string.mybankcard);
        bankcard_listview = (SwipeMenuListView) findViewById(R.id.bankcard_listview);//银行卡列表
        bankcard_listview.setPullLoadEnable(false);
        bankcard_listview.setXListViewListener(this);
        View footview = LayoutInflater.from(this).inflate(R.layout.add_bankcardbuttonlayout, null);
        bankcard_add_linearlayout = (LinearLayout) footview.findViewById(R.id.bankcard_add_linearlayout);//添加银行卡
        bankcard_add_linearlayout.setOnClickListener(this);
        bankcard_listview.addFooterView(footview);
        if (type.toString().equals("2")) {
            bankcard_add_linearlayout.setVisibility(View.VISIBLE);
            SwipeMenuCreator creator = new SwipeMenuCreator() {
                public void create(SwipeMenu menu) {
                    SwipeMenuItem deleteItem = new SwipeMenuItem(Me_BankCardActivity.this);
                    //				 set item width
                    //				shareItem.setWidth(dp2px(90));
                    //				 set a icon
                    //				shareItem.setIcon(R.drawable.ic_delete);
                    //				 add to menu
                    deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                            0x3F, 0x25)));
                    deleteItem.setWidth(dp2px(90));
                    deleteItem.setTitle("删除");
                    // set item title fontsize
                    deleteItem.setTitleSize(20);
                    // set item title font color
                    deleteItem.setTitleColor(Color.WHITE);
                    // add to menu
                    menu.addMenuItem(deleteItem);
                }
            };
            bankcard_listview.setMenuCreator(creator);
            bankcard_listview.setOnMenuItemClickListener(new OnMenuItemClickListener() {

                public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                    switch (index) {
                        case 0://删除
                            deleteBankCard(position);
                            break;
                    }
                }
            });
        } else if (type.toString().equals("1")) {
            bankcard_add_linearlayout.setVisibility(View.GONE);
            bankcard_listview.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    bankCardWithdraw(banckList.get(position - 1));
                }
            });
        }


    }

    //解绑银行卡
    protected void deleteBankCard(int position) {
        bankCardId = banckList.get(position).getId();
        Withdraw_DepositPassPop pop = new Withdraw_DepositPassPop(this);
        pop.setWithdrawSuccessListener(new withdrawSuccessListener() {
            @Override
            public void withdrawSuccess() {
                if (controller != null) {
                    controller.setGetBankListListener(Me_BankCardActivity.this);
                    controller.getBankList();
                }
            }
        });
        pop.showPop(2, "", bankCardId);
    }

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left_rl:
                finish();
                break;
            case R.id.bankcard_add_linearlayout://添加银行卡
                addBankCard();
                break;
            case R.id.add_bankcard_button://提交银卡卡信息
                if (isEmpty()) {
                    submitData();
                }
                break;
            case R.id.withdraw_all_textview://全部提现
                withdraw_money_edittext.setText(balance);
                withdraw_money_edittext.setSelection(balance.length());
                withdraw_balance_textview.setText("账户余额￥" + "0.00");
                break;
            case R.id.withdraw_button://提现按钮
                if (isWithdrawEmpty()) {
                    withdrawSubmit(withdraw_money_edittext.getText().toString().trim());
                }
                break;
            case R.id.add_bankcard_cancle_button:
                finish();
                break;
            default:
                break;
        }
    }

    private void withdrawSubmit(String money) {
        Withdraw_DepositPassPop pop = new Withdraw_DepositPassPop(this);
        pop.setWithdrawSuccessListener(new withdrawSuccessListener() {
            @Override
            public void withdrawSuccess() {
                finish();
            }
        });
        pop.showPop(1, money, bankCardId);
    }

    private void submitData() {
        if (controller != null) {
            controller.setGetBankListListener(this);
            String card_no = add_bankcard_number.getText().toString();
            String card_bank = add_bankcard_name.getText().toString();
            String card_name = add_bankcard_user.getText().toString();
            controller.addBankCard(card_no, card_bank, card_name);
        }
    }

    /**
     *
     */
    //添加银行卡页面
    private void addBankCard() {
        setTitleText(R.string.addbankcard);
        if (addview == null) {
            addview = ((ViewStub) findViewById(R.id.addbankcard_import)).inflate();
            addview.setVisibility(View.VISIBLE);
        } else {
            addview.setVisibility(View.VISIBLE);
        }
        bankcard_listview.setVisibility(View.GONE);
        add_banckcard_linearlayout = (LinearLayout) addview.findViewById(R.id.add_banckcard_linearlayout);//添加银行卡
        add_banckcard_linearlayout.setVisibility(View.VISIBLE);
        add_bankcard_name = (EditText) addview.findViewById(R.id.add_bankcard_name);//银行名字
        add_bankcard_user = (EditText) addview.findViewById(R.id.add_bankcard_user);//持卡人名字
        add_bankcard_number = (EditText) addview.findViewById(R.id.add_bankcard_number);//卡号
        add_bankcard_button = (TextView) addview.findViewById(R.id.add_bankcard_button);//提交
        add_bankcard_cancle_button = (TextView) addview.findViewById(R.id.add_bankcard_cancle_button);//quxiao
        add_bankcard_button.setOnClickListener(this);
        add_bankcard_cancle_button.setOnClickListener(this);
    }

    private boolean isEmpty() {
        boolean is = true;
        if (add_bankcard_name.getText().toString().isEmpty()) {
            is = false;
            T.showShort(this, "银行名称不能为空!");
            return is;
        }

        if (add_bankcard_user.getText().toString().isEmpty()) {
            is = false;
            T.showShort(this, "持卡人名字不能为空!");
            return is;
        }

        if (add_bankcard_number.getText().toString().isEmpty()) {
            is = false;
            T.showShort(this, "银行卡号不能为空!");
            return is;
        }
        return is;
    }

    //提现页面
    protected void bankCardWithdraw(BankListBean bankListBean) {
        bankCardId = bankListBean.getId();
        getUserBalance();
        setTitleText(R.string.bankcard);
        if (withdraw_linearlayout == null) {
            withdrawview = ((ViewStub) findViewById(R.id.withdraw_import)).inflate();
        }
        withdraw_linearlayout = (LinearLayout) withdrawview.findViewById(R.id.withdraw_linearlayout);//提现页面
        withdraw_linearlayout.setVisibility(View.VISIBLE);
        bankcard_listview.setVisibility(View.GONE);
        if (add_banckcard_linearlayout != null) {
            add_banckcard_linearlayout.setVisibility(View.GONE);
        }
        bankcard_name_textview = (TextView) withdrawview.findViewById(R.id.bankcard_name_textview);//银行卡名称
        bankcard_user_textview = (TextView) withdrawview.findViewById(R.id.bankcard_user_textview);//持卡人
        bankcard_number_textview = (TextView) withdrawview.findViewById(R.id.bankcard_number_textview);//卡号
        withdraw_money_edittext = (EditText) withdrawview.findViewById(R.id.withdraw_money_edittext);//提现金额
        withdraw_balance_textview = (TextView) withdrawview.findViewById(R.id.withdraw_balance_textview);//余额显示
        withdraw_all_textview = (TextView) withdrawview.findViewById(R.id.withdraw_all_textview);//全部提现
        withdraw_all_textview.setOnClickListener(this);
        withdraw_button = (Button) withdrawview.findViewById(R.id.withdraw_button);//提现按钮
        withdraw_button.setOnClickListener(this);
        bankcard_name_textview.setText(bankListBean.getCard_bank());
        bankcard_user_textview.setText(bankListBean.getCard_name());
        bankcard_number_textview.setText(StringUtil.bankCardToAsterisk(bankListBean.getCard_no()));
        withdraw_money_edittext.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    float m = Float.parseFloat(s.toString());
                    float b = Float.parseFloat(balance);
                    if ((b - m) == 0 || (b - m) < 0) {
                        withdraw_balance_textview.setText("账户余额￥" + "0.00");
                    } else {
                        withdraw_balance_textview.setText("账户余额￥" + (b - m));
                    }
                } else {
                    withdraw_balance_textview.setText("账户余额￥" + balance);
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

    //获取用户实时的余额信息
    private void getUserBalance() {
        HttpController controller = new HttpController(this);
        String type = "2";//(1司机 2货主)
        controller.setGetUserGoldListener(this);
        controller.getUserGold(type);
    }

    private boolean isWithdrawEmpty() {

        boolean is = true;
        if (withdraw_money_edittext.getText().toString().isEmpty()) {
            is = false;
            T.showShort(this, "提现金额不能为空!");
            return is;
        }
        if (Double.parseDouble(withdraw_money_edittext.getText().toString()) == 0) {
            is = false;
            T.showShort(this, "提现金额不能为0!");
            return is;
        }
        if (bankCardId == null) {
            is = false;
            T.showShort(this, "银行卡信息错误");
            return is;
        }
        if (Double.parseDouble(balance) < Double.parseDouble(withdraw_money_edittext.getText().toString())) {
            is = false;
            T.showShort(this, "提现金额不能大于账户余额!");
            return is;
        }

        return is;
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public void onRefresh() {
        onLoad();
    }

    @Override
    public void onLoadMore() {
        onLoad();
    }

    public void onLoad() {
        bankcard_listview.stopRefresh();
        bankcard_listview.stopLoadMore();
        bankcard_listview.setRefreshTime(StringUtil.getTime());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bankcard_listview = null;
        bankcard_add_linearlayout = null;
        add_banckcard_linearlayout = null;
        add_bankcard_name = null;
        add_bankcard_user = null;
        add_bankcard_number = null;
        add_bankcard_button = null;
        if (bankcard_name_textview != null) {
            bankcard_name_textview = null;
        }
        if (bankcard_user_textview != null) {
            bankcard_user_textview = null;
        }
        if (bankcard_number_textview != null) {
            bankcard_number_textview = null;
        }
        if (withdraw_money_edittext != null) {
            withdraw_money_edittext = null;
        }
        if (withdraw_balance_textview != null) {
            withdraw_balance_textview = null;
        }
        if (withdraw_all_textview != null) {
            withdraw_all_textview = null;
        }
        if (withdraw_button != null) {
            withdraw_button = null;
        }
        if (withdraw_linearlayout != null) {
            withdraw_linearlayout = null;
        }
        if (banckList != null) {
            banckList.clear();
            banckList = null;
        }
        if (bankCardAdapter != null) {
            bankCardAdapter = null;
        }
    }

    @Override
    public void BankList(List<BankListBean> banckList) {
        if (banckList == null) {
            addBankCard();
            return;
        }
        if (this.banckList != null) {
            this.banckList.clear();
        }
        this.banckList = banckList;
        if (bankCardAdapter == null) {
            bankCardAdapter = new BankCardAdapter(this, this.banckList);
            bankcard_listview.setAdapter(bankCardAdapter);
        } else {
            bankCardAdapter.updataList(this.banckList);
        }
    }

    @Override
    public void AddBank() {
        setTitleText(R.string.mybankcard);
        add_banckcard_linearlayout.setVisibility(View.GONE);
        bankcard_listview.setVisibility(View.VISIBLE);
        if (controller != null) {
            controller.setGetBankListListener(this);
            controller.getBankList();
        }
    }

    @Override
    public void getUserGold(UserGoldBean userGoldBean) {
        if (userGoldBean == null) {
            return;
        }
        balance = userGoldBean.getBalance();
        withdraw_balance_textview.setText("账户余额￥" + balance);
    }
}
