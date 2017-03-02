package zhidanhyb.huozhu.Activity.Me;

import android.os.Bundle;
import android.widget.TextView;

import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.Bean.UserAccountDataBean;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.T;

/**
 *
 */
public class MeLevelInfoActivity extends BaseActivity implements HttpController.getUserAccountDataListener {
    /**
     *
     */
    private TextView account_order_number_textview;
    private TextView account_grade_number_textview;
    private TextView now_level, next_level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_level_info);
        initView();
    }

    /**
     *
     */
    private void initView() {
        setTitleText(R.string.levelInfo);
        setLeftButton();
        setHideRightButton();
        now_level = (TextView) findViewById(R.id.now_level);
        next_level = (TextView) findViewById(R.id.next_level);
        account_order_number_textview = (TextView) findViewById(R.id.account_order_number_textview);
        account_grade_number_textview = (TextView) findViewById(R.id.account_grade_number_textview);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    /**
     *
     */
    private void getData() {
        HttpController controller = new HttpController(this);
        controller.setGetUserAccountDataListener(this);
        controller.getUserAccountData();
    }

    /**
     * @param userAccountDataBean
     */
    @Override
    public void getUserAccount(UserAccountDataBean userAccountDataBean) {
        if (userAccountDataBean == null) {
            T.showShort(this, "获取失败,请重新获取!");
            finish();
            return;
        }
        account_order_number_textview.setText(userAccountDataBean.getSuccess_num() + "单");
        account_grade_number_textview.setText(userAccountDataBean.getScore() + "分");
        now_level.setText("当前级别为 " + userAccountDataBean.getNow_level());
        next_level.setText("还差 " + userAccountDataBean.getNeed_score() + "升级为" + userAccountDataBean.getNext_level());
//        next_rank_score_textview.setText(userAccountDataBean.getNeed_score());

    }
}
