package zhidanhyb.huozhu.Activity.Order;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import java.util.ArrayList;
import java.util.List;

import zhidanhyb.huozhu.Adapter.MyFragmentAdapter;
import zhidanhyb.huozhu.Base.BaseFragment;
import zhidanhyb.huozhu.Config.ConstantConfig;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.ScreenUtils;

/**
 * 订单
 *
 * @author lxj
 */
public class Main_OrderActivity extends BaseFragment implements OnPageChangeListener {
    private RadioGroup order_radiogroup;
    private ViewPager order_viewpager;
    private List<Fragment> list = new ArrayList<Fragment>();
    public static Main_Order_UnderwayActivity underwayActivity;
    public static Main_Order_AccomplishActivity accomplishActivity;
    private RadioButton ordering_radiobutton;
    private RadioButton ordered_radiobutton;
    private MyBroadcastReceiver broadcastReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.main_order_layout, null);
            initView();
        }
        ConstantConfig.isOrderList = true;
        //注册广播
        broadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConstantConfig.updataOrderList);
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
        return view;
    }

    int offset;

    /**
     *
     */
    private void initView() {
        setTitleText(R.string.order);
        setHideLeftButton();
        setHideRightButton();
        cursor = (ImageView) view.findViewById(R.id.cursor);
        offset = ScreenUtils.getScreenWidth(getContext()) / 2;
//        image.setMaxWidth(ScreenTools.instance(context).getScreenWidth() / 4);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) cursor.getLayoutParams();
//        new FrameLayout.LayoutParams(offset, ScreenUtils.getScreenWidth(getContext()) / 4, 30);
        params.gravity = Gravity.BOTTOM;
        params.width = ScreenUtils.getScreenWidth(getContext()) / 2;
        cursor.setLayoutParams(params);
        //imgageview设置平移，使下划线平移到初始位置（平移一个offset）
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);

        order_radiogroup = (RadioGroup) view.findViewById(R.id.order_radiogroup);
        ordering_radiobutton = (RadioButton) view.findViewById(R.id.ordering_radiobutton);//进行中
        ordered_radiobutton = (RadioButton) view.findViewById(R.id.ordered_radiobutton);//已完成
        order_viewpager = (ViewPager) view.findViewById(R.id.order_viewpager);
        order_viewpager.setOnPageChangeListener(this);
        underwayActivity = new Main_Order_UnderwayActivity();
        accomplishActivity = new Main_Order_AccomplishActivity();
        list.add(underwayActivity);
        list.add(accomplishActivity);
        order_viewpager.setAdapter(new MyFragmentAdapter(getFragmentManager(), list));

        order_radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.ordering_radiobutton:
//					ordered_radiobutton.setBackgroundResource(R.drawable.gray_circular_bead_buttons);
//					ordering_radiobutton.setBackgroundResource(R.drawable.orange_circular_bead_buttons);
                        order_viewpager.setCurrentItem(0);
                        break;
                    case R.id.ordered_radiobutton:
//					ordering_radiobutton.setBackgroundResource(R.drawable.gray_circular_bead_buttons);
//					ordered_radiobutton.setBackgroundResource(R.drawable.orange_circular_bead_buttons);
                        order_viewpager.setCurrentItem(1);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ConstantConfig.isOrderList = true;
        if (ConstantConfig.sendOrderSucc) {
            if (underwayActivity != null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        underwayActivity.onRefresh();
                    }
                }, 500);
            }
            ConstantConfig.sendOrderSucc = false;
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    private int one = offset * 2;//两个相邻页面的偏移量

    /**
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) cursor.getLayoutParams();
        params.gravity = Gravity.BOTTOM;
        params.width = (position * ScreenUtils.getScreenWidth(getContext())) + positionOffsetPixels + ScreenUtils.getScreenWidth(getContext()) / 2;
        cursor.setLayoutParams(params);
        //imgageview设置平移，使下划线平移到初始位置（平移一个offset）

    }

    int currIndex = 0;
    /**
     *
     */
    ImageView cursor;

    /**
     */
    @Override
    public void onPageSelected(int position) {
//        Animation animation = new TranslateAnimation(currIndex * one, position * one, 0, 0);//平移动画
//        currIndex = position;
//        animation.setFillAfter(true);//动画终止时停留在最后一帧，不然会回到没有执行前的状态
//        animation.setDuration(200);//动画持续时间0.2秒
//        cursor.startAnimation(animation);//是用ImageView来显示动画的

        if (position == 0) {
            ordered_radiobutton.setChecked(false);
            ordering_radiobutton.setChecked(true);
//			ordered_radiobutton.setBackgroundResource(R.drawable.gray_circular_bead_buttons);
//			ordering_radiobutton.setBackgroundResource(R.drawable.orange_circular_bead_buttons);
        } else if (position == 1) {
            ordered_radiobutton.setChecked(true);
            ordering_radiobutton.setChecked(false);
//			ordering_radiobutton.setBackgroundResource(R.drawable.gray_circular_bead_buttons);
//			ordered_radiobutton.setBackgroundResource(R.drawable.orange_circular_bead_buttons);
        }
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        public static final String TAG = "MyBroadcastReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.toString().equals(ConstantConfig.updataOrderList)) {//订单详情页更新页面数据及订单状态
                if (underwayActivity != null) {
                    underwayActivity.onRefresh();
                }
                if (accomplishActivity != null) {
                    accomplishActivity.onRefresh();
                }
                return;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ConstantConfig.isOrderList = false;
        if (broadcastReceiver != null) {
            getActivity().unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }

}
