package zhidanhyb.huozhu.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import zhidanhyb.huozhu.Activity.Home.Main_HomeActivity;
import zhidanhyb.huozhu.Activity.Me.Main_V2_MeActivity;
import zhidanhyb.huozhu.Activity.Message.Main_MessageActivity;
import zhidanhyb.huozhu.Activity.Order.Main_OrderActivity;
import zhidanhyb.huozhu.Base.AppManager;
import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.Base.HYBHuoZhuApplication;
import zhidanhyb.huozhu.Config.ConstantConfig;
import zhidanhyb.huozhu.HttpRequest.HttpConfigSite;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.T;

//主页面
public class MainActivity extends BaseActivity implements OnTabChangeListener {

    /**
     *
     */
    // 定义数组来存放Fragment界面
    @SuppressWarnings("rawtypes")
    private Class fragmentArray[] = {
            Main_HomeActivity.class,
            Main_MessageActivity.class,
            Main_OrderActivity.class,
            Main_V2_MeActivity.class};
    // 定义数组来存放按钮图片
    private int mImageViewArray[] = {R.drawable.home_buttons,
            R.drawable.home_buttons, R.drawable.home_buttons,
            R.drawable.home_buttons};
    // private int mTextBaColor[]={};
    // Tab选项卡的文字
    private String mTextviewArray[] = {"下单", "消息", "订单", "我的"};
    private LayoutInflater layoutInflater;
    public static FragmentTabHost mTabHost;
    private TabSpec tabSpec;
    HYBHuoZhuApplication app;
    private Window window;
    private Handler sHandler;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setSwipeBackEnable(false);
        TextView flag = (TextView) findViewById(R.id.flag);

        app = new HYBHuoZhuApplication();

        if (HttpConfigSite.PostUrl.contains("139")) {
            flag.setText("货主端测试版本");
            app.isDebug = true;
        } else {
            flag.setVisibility(View.GONE);
            app.isDebug = false;

        }
        initView();

    }




    private void initView() {

        // 实例化布局对象
        layoutInflater = LayoutInflater.from(this);

        // 实例化TabHost对象，得到TabHost getSupportFragmentManager
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        // shopping_Cart_Statistics_PopWindow = new
        // Shopping_Cart_Statistics_PopWindow(this, mTabHost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.getTabWidget().setDividerDrawable(null);
        mTabHost.setOnTabChangedListener(this);
        // 得到fragment的个数
        int count = fragmentArray.length;

        for (int i = 0; i < count; i++) {
            // 为每一个Tab按钮设置图标、文字和内容
            tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            // 设置Tab按钮的背景
            // mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ConstantConfig.sendOrderSucc == true) {// 跳转到订单列表
            if (mTabHost != null) {
                mTabHost.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mTabHost.setCurrentTab(2);
                    }
                }, 50);
            }
        }

    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {//check by 张永发 1.首页底部按钮颜色修改
        View view = layoutInflater.inflate(R.layout.activity_mian_button_item, null);
        RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.relativelayout);
//		 rl.setBackgroundResource(mImageViewArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        Button number_button = (Button) view.findViewById(R.id.message_number_button);
        textView.setText(mTextviewArray[index]);
        if (index == 3) {
            view.findViewById(R.id.textline).setVisibility(View.GONE);
        }

        if (index == 1) {
            number_button.setVisibility(View.GONE);
            number_button.setText("99");
        } else {
            number_button.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onTabChanged(String tabId) {

    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                T.showShort(getApplicationContext(), R.string.press_again_exit);
                exitTime = System.currentTimeMillis();
            } else {
                AppManager.getAppManager().finishAllActivity();
                finish();
            }
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragmentArray = null;
        mImageViewArray = null;
        mTextviewArray = null;
        layoutInflater = null;
        mTabHost = null;
        tabSpec = null;
        if (ConstantConfig.sendSureList != null) {
            ConstantConfig.sendSureList.clear();
            ConstantConfig.sendSureList = null;
        }
    }
}
