package zhidanhyb.huozhu.Activity.Me;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.Bean.UserAccountDataBean;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.getUserAccountDataListener;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.T;
import zhidanhyb.huozhu.View.ChoosePayTypeDialog;
import zhidanhyb.huozhu.View.DialogUtils;
import zhidanhyb.huozhu.View.Withdraw_PasswordSettingPop;

/**
 * 用户账户
 *
 * @author lxj
 */
public class User_AccountActivity extends BaseActivity implements getUserAccountDataListener {


    private TextView gold_textview;
    private TextView balance_textview;
    private TextView orderpush_textview;
    /**
     *
     */
    //    private TextView order_number_textview;
//    private TextView grade_number_textview;
    //    private TextView rank_textview;
//    private TextView next_rank_textview;
    private LinearLayout withdraw_deposit_linearlayout;
    //    private TextView next_rank_score_textview;
    private LinearLayout bankcard_linearlayout;
    private int is_dealpwd;//提现密码 1:已设置 0 未设置
    private int is_bankcard;//添加银行卡1:已设置 0 未设置
    private LinearLayout gold_linearlayout;
    private LinearLayout balance_linearlayout;
    private LinearLayout v2_me_account_exchange, v2_me_account_charge;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.user_accountlayout);
        initView();
    }

    /**
     *
     */
    private void initView() {
        setTitleText(R.string.account);
        setLeftButton();
        setHideRightButton();
        gold_linearlayout = (LinearLayout) findViewById(R.id.account_gold_linearlayout);

        gold_textview = (TextView) findViewById(R.id.account_gold_textview);//金币数量
        balance_linearlayout = (LinearLayout) findViewById(R.id.account_balance_linearlayout);
        balance_textview = (TextView) findViewById(R.id.account_balance_textview);//余额
        orderpush_textview = (TextView) findViewById(R.id.account_orderpush_textview);//推送标语
//        order_number_textview = (TextView) findViewById(R.id.account_order_number_textview);//订单数量
//        grade_number_textview = (TextView) findViewById(R.id.account_grade_number_textview);//评分
//        rank_textview = (TextView) findViewById(R.id.account_rank_textview);//当前等级
//        next_rank_textview = (TextView) findViewById(R.id.account_next_rank_textview);//下一个等级
//        next_rank_score_textview = (TextView) findViewById(R.id.account_next_rank_score_textview);//下一等级的分数
        withdraw_deposit_linearlayout = (LinearLayout) findViewById(R.id.account_withdraw_deposit_linearlayout);//提现
        bankcard_linearlayout = (LinearLayout) findViewById(R.id.account_withdraw_bankcard_linearlayout);//银行卡
        v2_me_account_exchange = (LinearLayout) findViewById(R.id.v2_me_account_exchange);
        v2_me_account_charge = (LinearLayout) findViewById(R.id.v2_me_account_charge);


    }

    /**
     *
     */
    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        HttpController controller = new HttpController(this);
        controller.setGetUserAccountDataListener(this);
        controller.getUserAccountData();
    }


    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.title_left_rl:
                finish();
                break;
            case R.id.account_withdraw_deposit_linearlayout://提现
                if (is_dealpwd == 0) {//没有设置密码
                    new Withdraw_PasswordSettingPop(this).showPop(1);
                } else if (is_dealpwd == 1) {//设置了密码
                    intent.putExtra("type", "1");//从提现进入，type = 1 不能再Me_BankCardActivity这个页面有删除和添加银行卡操作，只有提现操作
                    intent.setClass(this, Me_BankCardActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.account_withdraw_bankcard_linearlayout://银行卡
                intent.putExtra("type", "2");//从银行卡进入，type = 2 不能再Me_BankCardActivity这个页面有提现操作，只有删除和添加银行卡操作
                intent.setClass(this, Me_BankCardActivity.class);
                startActivity(intent);
                break;
            case R.id.account_gold_linearlayout://金币记录
                intent.setClass(this, GoldRecordActivity.class);
                intent.putExtra("action", "1");
                startActivity(intent);
                break;
            case R.id.account_balance_linearlayout://账户记录
                intent.setClass(this, GoldRecordActivity.class);
                intent.putExtra("action", "2");
                startActivity(intent);
                break;
            case R.id.v2_me_account_exchange://交换金币
//                User_Conversion_Gold_Dialog dialog = new User_Conversion_Gold_Dialog(context, R.style.dialog);
//                dialog.setConversionSuccessListener(this);
//                dialog.showDialog(banlance);
                DialogUtils.showExchangeGoldDialog(context, banlance, new DialogUtils.ExchangeListener() {
                    @Override
                    public void exchangeSuccess() {
                        getData();
                    }
                });

                break;
            case R.id.v2_me_account_charge
                    ://充值
                ChoosePayTypeDialog paydialog = new ChoosePayTypeDialog(context, R.style.dialog);
                paydialog.showDialog();
                break;
            default:
                break;
        }
    }

    /**
     *
     */
    String banlance = "";

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gold_textview = null;
        balance_textview = null;
        orderpush_textview = null;
//        order_number_textview = null;
//        grade_number_textview = null;
        withdraw_deposit_linearlayout = null;
        gold_linearlayout = null;
        balance_linearlayout = null;
    }

    /**
     * @param userAccountDataBean
     */
    //获取用户账户信息的回调
    @SuppressLint("SetTextI18n")
    @Override
    public void getUserAccount(UserAccountDataBean userAccountDataBean) {
        Log.e("getUserAccount", "" + userAccountDataBean.toString());
        if (userAccountDataBean == null) {
            T.showShort(this, "获取失败,请重新获取!");
            finish();
            return;
        }
        gold_textview.setText(userAccountDataBean.getGold() + "金币");
        balance_textview.setText("￥" + userAccountDataBean.getBalance());
        orderpush_textview.setText(userAccountDataBean.getContent());
//        order_number_textview.setText(userAccountDataBean.getSuccess_num() + "单");
//        grade_number_textview.setText(userAccountDataBean.getScore() + "分");
//        rank_textview.setText("当前级别: " + userAccountDataBean.getNow_level());
//        next_rank_textview.setText("下一级别: " + userAccountDataBean.getNext_level() + "离升级还有");
//        next_rank_score_textview.setText(userAccountDataBean.getNeed_score());
        is_dealpwd = userAccountDataBean.getIs_dealpwd();
        is_bankcard = userAccountDataBean.getIs_bankcard();
        banlance = userAccountDataBean.getBalance();
        bankcard_linearlayout.setOnClickListener(this);
        withdraw_deposit_linearlayout.setOnClickListener(this);
        v2_me_account_charge.setOnClickListener(this);
        v2_me_account_exchange.setOnClickListener(this);
        gold_linearlayout.setOnClickListener(this);
        balance_linearlayout.setOnClickListener(this);
    }

    /**
     *
     */
}
