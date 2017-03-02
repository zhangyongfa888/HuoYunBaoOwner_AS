package zhidanhyb.huozhu.Activity.Home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;

import zhidanhyb.huozhu.Activity.Order.SendOrderActivity;
import zhidanhyb.huozhu.Adapter.Home_MessageAdapter;
import zhidanhyb.huozhu.Base.BaseFragment;
import zhidanhyb.huozhu.Bean.AllDriverLocationBean;
import zhidanhyb.huozhu.Bean.HomeBean;
import zhidanhyb.huozhu.Bean.Home_AdvertiseListBean;
import zhidanhyb.huozhu.Bean.Home_NoticeListBean;
import zhidanhyb.huozhu.Bean.Home_PushMessageListBean;
import zhidanhyb.huozhu.Bean.Home_PushMessageListMoreBean;
import zhidanhyb.huozhu.Config.ZDSharedPreferences;
import zhidanhyb.huozhu.Connector.MyLocationListener;
import zhidanhyb.huozhu.Connector.MyLocationListener.OnLocationListener;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.getHomeDataListener;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.BaiduMap_Utils;
import zhidanhyb.huozhu.Utils.Home_CarouselNoticeUtils;
import zhidanhyb.huozhu.Utils.L;
import zhidanhyb.huozhu.Utils.ScreenUtils;
import zhidanhyb.huozhu.Utils.SharedPerfenceUtil;
import zhidanhyb.huozhu.Utils.StringUtil;
import zhidanhyb.huozhu.Utils.T;
import zhidanhyb.huozhu.Utils.UserVerifyUtils;
import zhidanhyb.huozhu.Utils.ViewUtils;
import zhidanhyb.huozhu.View.Main_BottomView;
import zhidanhyb.huozhu.View.XList_View.XListView;
import zhidanhyb.huozhu.View.banner.AutoScrollViewPager;
import zhidanhyb.huozhu.View.banner.CirclePageIndicator;
import zhidanhyb.huozhu.View.banner.ImagePagerAdapter;
import zhidanhyb.huozhu.View.banner.OnPagerItemClickListenner;

/**
 * 首页
 *
 * @author lxj
 */
public class Main_HomeActivity extends BaseFragment implements getHomeDataListener, OnLocationListener, XListView.IXListViewListener,
        HttpController.getUserStatusListener, HttpController.onGetAllDriverListener,
        OnGetGeoCoderResultListener, HttpController.getHomeLoadMoreDataListener {
    View homeView;
    private AutoScrollViewPager home_banner_viewpager;
    private CirclePageIndicator home_banner_indicator;
    private ImageView home_ranking_list;
    private int pagerHeight;
    private int Screenwidth;
    private ImagePagerAdapter mViewPagerAdapter;
    private LinearLayout home_Xiadan_imageview;
    private TextView home_notice_textview;
    private XListView home_message_listveiw;
    private Home_MessageAdapter messageadapter;
    private Home_CarouselNoticeUtils carouselNoticeUtils;
    /**
     * 伸缩布局
     */
    private Main_BottomView bottomView;
    //公告栏
    private LinearLayout layoutNoticeTips;
    //页数
    private int page = 1;
    //自己位置的图标
    private ImageView locationAddImageView;
    //点击回到自己位置的图标
    private ImageView imageViewMyLocationHome;
    private ImageView imageViewShowList;

    /**
     * 蒋米兰
     */
    private MapView locationmapview;
    private BaiduMap mBaiduMap;
    private boolean isFirstLoc = true;
    private Marker mMarkerLocation; // 当前位置
    private ArrayList<Marker> mMarkerSurroundCars;
    // 初始化全局 bitmap 信息，不用时及时 recycle
    // private BitmapDescriptor bdLocation = null;
    private BitmapDescriptor bdDirver = null;
    private Button location_order;
    private BaiduMap_Utils baiduMap1;
    private double meLocationLat = 0;
    private double meLocationLng = 0;
    private TextView location_add_textview;
    private GeoCoder mSearch;
    public static Main_HomeActivity locationactivity;
    private BitmapDescriptor bitmapDescriptor;
    private int pushMessageListSize;
    private String license;
    private double lat;
    private double lng;
    private InfoWindow infoWindow;
    private int height;
    private int height2;
    private double meLat;
    private double melng;
    private PopupWindow popupWindow = null;
    private View popupView = null;
    private Timer timer = null;//定时器
    private Home_AdvertiseListBean advertise;
    private boolean isFalse;
    private List<Map<String, String>> list;
    private boolean isFirst = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (homeView == null) {
            homeView = inflater.inflate(R.layout.main_home_layout, null);
            initView();
        }

        return homeView;
    }

    /**
     *
     */
    private void initView() {
        getData();
        final MapView mapView = (MapView) homeView.findViewById(R.id.main_mapview);
        home_banner_viewpager = (AutoScrollViewPager) homeView.findViewById(R.id.home_banner_viewpager);
        home_banner_indicator = (CirclePageIndicator) homeView.findViewById(R.id.home_banner_indicator);
        home_ranking_list = (ImageView) homeView.findViewById(R.id.home_ranking_list);
        home_ranking_list.setOnClickListener(this);
        home_Xiadan_imageview = (LinearLayout) homeView.findViewById(R.id.home_Xiadan_imageview);// 下单
        home_Xiadan_imageview.setOnClickListener(this);
        ViewUtils.setViewSize(getContext(), home_Xiadan_imageview, 640, 90);
        ViewUtils.setViewSize(getContext(), homeView.findViewById(R.id.imageView_showList), 45, 51);
//        ViewUtils.setViewSize(getContext(), homeView.findViewById(R.id.imageView1_showList), 45, 51);
        ViewUtils.setViewSize(getContext(), homeView.findViewById(R.id.layout_notice_tips), 640, 51);
        home_notice_textview = (TextView) homeView.findViewById(R.id.home_notice_textview);// 公告
        home_message_listveiw = (XListView) homeView.findViewById(R.id.home_message_listveiw);// 消息
        imageViewShowList = (ImageView) homeView.findViewById(R.id.imageView_showList);
//        imageView1showList = (ImageView) homeView.findViewById(R.id.imageView1_showList);
        home_message_listveiw.setPullLoadEnable(true);
        home_message_listveiw.setPullRefreshEnable(false);
        home_message_listveiw.setXListViewListener(this);
        bottomView = (Main_BottomView) homeView.findViewById(R.id.main_bottom);
        layoutNoticeTips = (LinearLayout) homeView.findViewById(R.id.layout_notice_tips);//公告栏
        locationAddImageView = (ImageView) homeView.findViewById(R.id.location_add_imageView);//自己位置的图标
        imageViewMyLocationHome = (ImageView) homeView.findViewById(R.id.imageView_myLocation_home);
        bottomView.setTargetView(homeView);

        imageViewShowList.setVisibility(View.VISIBLE);

        imageViewShowList.setImageResource(R.drawable.arrow_up);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageViewShowList.getDrawable();
        animationDrawable.start();


//        final myAnimation myAnimation = new myAnimation(imageViewShowList, images, 37);
//        final myAnimation myAnimationDown = new myAnimation(imageView1showList, imagesDown, 37);
//        myAnimation.playConstant(1);

        //引导页布局文件
        popupView = getActivity().getLayoutInflater().inflate(R.layout.popuplayout, null);
        popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        ImageView imageView = (ImageView) popupView.findViewById(R.id.imageView_down_pop);

        //判断是否是第一次启动
        final SharedPreferences googleActivitySP = getContext().getSharedPreferences("HuoYunBaoOwner", Context.MODE_PRIVATE);
        //判断滑动到顶部显示提示点击箭头
        bottomView.setScrollTopListener(new Main_BottomView.isScrollTopListener() {

            /**
             * @param isTop
             */
            @Override
            public void scrollTop(boolean isTop) {
                isFalse = isTop;
                boolean firstStart = googleActivitySP.getBoolean("first_start", true);
//                Log.e("isTop", isTop + "");
                if (isTop && firstStart) {//
                    popupWindowshow(googleActivitySP, "first_start");
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            /**
             * @param v
             */
            @Override
            public void onClick(View v) {
                if (popupShowBig != null && popupShowBig.isShowing()) {
                    popupShowBig.dismiss();
                }

                popupWindow.dismiss();
                home_message_listveiw.setVisibility(View.GONE);
                home_message_listveiw.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bottom_end));
                //设置头部可见
                bottomView.setHeadVisable();
                //设置homeView不能滑动
                bottomView.setTargetView(homeView);
                //消失之后换为向上的箭头
                //// TODO: 2017/2/17
                imageViewShowList.setImageResource(R.drawable.arrow_down);
                AnimationDrawable animationDrawable = (AnimationDrawable) imageViewShowList.getDrawable();
                animationDrawable.start();
            }
        });


        //向上箭头的点击事件
        imageViewShowList.setOnClickListener(new View.OnClickListener() {

            /**
             * @param view
             */
            @Override
            public void onClick(View view) {

                showList();
            }
        });
        layoutNoticeTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showList();
            }
        });

        if (Screenwidth > 0 && pagerHeight > 0) {
            ViewUtils.setViewSize(getContext(), home_banner_viewpager, 640, 190);
        } else {
            ViewUtils.setViewSize(getContext(), home_banner_viewpager, 640, 190);
        }
        home_banner_viewpager.setClickable(true);
        home_banner_viewpager.setInterval(3000);
        home_banner_viewpager.startAutoScroll();
        home_banner_viewpager.setOffscreenPageLimit(4);// 设置viewpager缓存数量，实际有5个，显示1个，缓存4个


        // check by 张永发 1.自动签到
        if (!SharedPerfenceUtil.getSpParams(getContext(), SharedPerfenceUtil.signData, "date")
                .equals(ZDSharedPreferences.getInstance(getActivity()).getUserId().toString()
                        + StringUtil.refFormatNowDate())) {// 今天没有签到
            singIn();
        }
        // check by 张永发 1. android6.0以上请求定位权限
        if (!hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            getPermission(Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_PERMISSION_LOCATION);
        }

        /*
        *地图
        */

        // controller.getAllDriverLocation();
        locationactivity = this;
//        setTitleText(R.string.xiadan);
//        setLeftButton();
//        setHideRightButton();
        // bdLocation =
        // BitmapDescriptorFactory.fromResource(R.drawable.place_distination);
        bdDirver = BitmapDescriptorFactory.fromResource(R.drawable.v2_place_driver);
        locationmapview = (MapView) homeView.findViewById(R.id.main_mapview);// 地图
        location_add_textview = (TextView) homeView.findViewById(R.id.location_add_textview);// 挪动地图，获取当前最新位置信息
//        location_order = (Button) homeView.findViewById(R.id.location_order_xiadan_button);// 立即下单
//        location_order.setOnClickListener(this);
        mBaiduMap = locationmapview.getMap();

        mBaiduMap.setMyLocationEnabled(true);
        MyLocationListener onListener = new MyLocationListener();
        onListener.setOnLocationListener(this);
        baiduMap = new BaiduMap_Utils(getContext(), onListener);
        baiduMap.LocationStart();
        // 初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);


        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            //手势操作地图，设置地图状态等操作导致地图状态开始改变。
            public void onMapStatusChangeStart(MapStatus status) {

                // updateMapState();
            }

            //地图状态改变结束
            public void onMapStatusChangeFinish(MapStatus status) {
                //设置地图的高度
                setMapHeight(mapView);
                LatLng mCenterLatLng = status.target;
                /** 获取经纬度 */
                double lat = mCenterLatLng.latitude;
                double lng = mCenterLatLng.longitude;
                meLocationLat = lat;
                meLocationLng = lng;
                LatLng ptCenter = new LatLng(lat, lng);
                // 反Geo搜索
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(ptCenter));
                if (controller != null) {
                    controller.upLoadNearbyDriver(lat, lng);
                }
            }

            //地图状态变化中
            public void onMapStatusChange(MapStatus status) {
                // updateMapState();
            }
        });
    }

    private void showList() {
        if (popupShowBig != null && popupShowBig.isShowing()) {
            popupShowBig.dismiss();
        }
        home_message_listveiw.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (popupShowBig != null && popupShowBig.isShowing()) {
                    popupShowBig.dismiss();
                }
            }
        });
        if (home_message_listveiw.getVisibility() == View.GONE) {
            home_message_listveiw.setVisibility(View.VISIBLE);
//                    imageViewMyLocationHome.setVisibility(View.GONE);
            hideImageViewMyLocationHome(imageViewMyLocationHome);
            home_message_listveiw.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.activity_open));
            bottomView.setTargetView(home_message_listveiw);

            //换为向下箭头
            imageViewShowList.setImageResource(R.drawable.arrow_down);
            AnimationDrawable animationDrawable = (AnimationDrawable) imageViewShowList.getDrawable();
            animationDrawable.start();
//                    imageViewShowList.setVisibility(View.INVISIBLE);
//                    imageView1showList.setVisibility(View.VISIBLE);
//                    if (isFirst) {
//                        myAnimationDown.playConstant(1);
//                        isFirst = false;
//                    }

            //判断你是否是第一次启动
            final SharedPreferences sp = getContext().getSharedPreferences("HuoYunBaoOwner1", Context.MODE_PRIVATE);
            boolean oneStart = sp.getBoolean("oneStart", true);
            if (oneStart && !isFalse && pushMessageListSize < 6) {
                popupWindowshow(sp, "oneStart");
            }

        } else {
            imageViewShowList.setImageResource(R.drawable.arrow_up);
            AnimationDrawable animationDrawable = (AnimationDrawable) imageViewShowList.getDrawable();
            animationDrawable.start();

            home_message_listveiw.setVisibility(View.GONE);
            showImageViewMyLocationHome(imageViewMyLocationHome);
            home_message_listveiw.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bottom_end));
            bottomView.setHeadVisable();
            bottomView.setTargetView(homeView);

            //消失之后换为向上的箭头
        }
    }

    //隐藏图片
    private void hideImageViewMyLocationHome(final ImageView imageView) {
        AlphaAnimation alp = new AlphaAnimation(1.0f, 0.0f);
        alp.setDuration(300);
        imageView.setAnimation(alp);
        alp.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                imageView.setVisibility(View.INVISIBLE);
            }
        });
    }

    //显示图片
    private void showImageViewMyLocationHome(final ImageView imageView) {
        AlphaAnimation alp = new AlphaAnimation(0.0f, 1.0f);
        alp.setDuration(300);
        imageView.setAnimation(alp);
        alp.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                imageView.setVisibility(View.VISIBLE);
            }
        });
    }

    //设置地图的高度     location[0]代表x坐标,location [1] 代表 坐标
    private void setMapHeight(final MapView mapView) {
        mapView.postDelayed(new Runnable() {
            /**
             *
             */
            @Override
            public void run() {
                int[] location = new int[2];
                mapView.getLocationOnScreen(location);
                //地图的坐标
                height = location[1];
                Log.e("heigiht", "height---->>" + height);
                bottomView.setTargetView(mapView);
            }
        }, 100);

        final LinearLayout layout_notice_tips = (LinearLayout) homeView.findViewById(R.id.layout_notice_tips);
        layout_notice_tips.postDelayed(new Runnable() {
            /**
             *
             */
            @Override
            public void run() {
                int[] location2 = new int[2];
                layout_notice_tips.getLocationOnScreen(location2);
                //公告栏的坐标
                height2 = location2[1];
                Log.e("heigiht2", "height2------>>" + height2);
                mapView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height2 - height + getVirtualBarHeight(locationactivity.getContext())));
            }
        }, 300);
    }

    //显示引导页
    private void popupWindowshow(SharedPreferences googleActivitySP, String putString) {
        popupWindow.showAtLocation(popupView, Gravity.TOP, 0, 0);
        popupWindow.isShowing();
        SharedPreferences.Editor edit = googleActivitySP.edit();
        edit.putBoolean(putString, false);
        edit.commit();
    }

    /**
     * 获取虚拟导航栏的高度
     *
     * @param context
     * @return
     */
    public int getVirtualBarHeight(Context context) {
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    /**
     * 加载更多
     */
    public void onLoad() {
        home_message_listveiw.stopRefresh();
        home_message_listveiw.stopLoadMore();
        home_message_listveiw.setRefreshTime(StringUtil.getTime());
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    AlphaAnimation aa = new AlphaAnimation(0.1f, 1.0f);
                    Random random = new Random();
                    int s = random.nextInt(msg.arg1);
                    // 渐变时间
                    aa.setDuration(1000);
                    home_notice_textview.startAnimation(aa);
                    List<Home_NoticeListBean> list = (List<Home_NoticeListBean>) msg.obj;
                    home_notice_textview.setText(list.get(s).getTitle());
                    break;
                default:
                    break;
            }
        }

        ;
    };
    private BaiduMap_Utils baiduMap;

    @Override
    public void onResume() {
        super.onResume();

        page = 1;
        if (carouselNoticeUtils == null) {
            return;
        } else {
            carouselNoticeUtils.start();
        }

        //地图
        if (locationmapview != null)
            locationmapview.onResume();
        if (popupShowBig != null && popupShowBig.isShowing()) {
            popupShowBig.dismiss();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (popupShowBig != null && popupShowBig.isShowing()) {
            popupShowBig.dismiss();
        }
        if (carouselNoticeUtils == null) {
            return;
        } else {
            carouselNoticeUtils.remove();
        }

        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        if (locationmapview != null)
            locationmapview.onPause();
    }

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_ranking_list:// 排行榜
                rankList();
                break;
            case R.id.home_Xiadan_imageview:// 下单
                XiaDan();
                break;
            default:
                break;
        }
    }

    /**
     *
     */
    private void XiaDan() {
        if (!ZDSharedPreferences.getInstance(getContext()).getUserStatus().toString().equals("2")) {// 状态status
            // !=
            // 2
            // 获取用户状态
            String type = "2";
            controller.setGetUserStatusListenenr(this);
            controller.getUserStatus(type);
        } else if (ZDSharedPreferences.getInstance(getContext()).getUserStatus().toString().equals("2")) {// 状态status
            // =
            // 2
            // 直接跳转到发单页面

            Intent intent = new Intent(getContext(), SendOrderActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("city", city);
            intent.putExtra("lat", meLocationLat + "");
            intent.putExtra("lng", meLocationLng + "");

            L.e("name", "name is" + name);
            L.e("city", "city is" + city);
            L.e("lat", meLocationLat + "");
            L.e("lng", meLocationLng + "");
            startActivity(intent);
//            finish();
        }
    }

    /**
     * 1.获取数据
     */
    private void getData() {
        MyLocationListener onListener = new MyLocationListener();
        onListener.setOnLocationListener(this);
        baiduMap = new BaiduMap_Utils(getActivity(), onListener);
        baiduMap.LocationStart();
    }

    /**
     *
     */
    private void getMoreData() {
        int paging = page;
        controller.setgetHomeLoadMoreDataListener(this);
        controller.getHomeLoadMoreData(paging + "");
    }

    private void rankList() {
    }

    private void singIn() {
        startActivity(new Intent(getActivity(), Signin_Activity.class));
    }

    /**
     * @param homeBean
     */
    // 2.成功获取首页数据
    @SuppressWarnings("static-access")
    @Override
    public void getHomeData(final HomeBean homeBean) {
//获取周围的司机
        controller.upLoadNearbyDriver(lat, lng);

        //获取消息列表的长度
        pushMessageListSize = homeBean.getPushmessageList().size();
        // 设置公告
        if (homeBean.getNoticeList().size() > 0) {
            carouselNoticeUtils = new Home_CarouselNoticeUtils(handler);
            carouselNoticeUtils.carouselNotice(homeBean.getNoticeList().size(), homeBean.getNoticeList(),
                    home_notice_textview);
        }
        // 设置广告图片
        if (homeBean.getAdvertiseList().size() > 0) {
            //过滤下广告图片
            advertise = new Home_AdvertiseListBean();
            final List<Home_AdvertiseListBean> aa = new ArrayList<>();//banner非广告页
            aa.addAll(homeBean.getAdvertiseList());
            for (int i = 0; i < homeBean.getAdvertiseList().size(); i++) {
                if (homeBean.getAdvertiseList().get(i).getSite().equals("2")) {
                    advertise = homeBean.getAdvertiseList().get(i);//广告页
                    aa.remove(i);
                }
            }
            mViewPagerAdapter = new ImagePagerAdapter(getActivity(), aa);
            home_banner_viewpager.setAdapter(mViewPagerAdapter);
            home_banner_indicator.setViewPager(home_banner_viewpager);

            // 自定义接口实现监听点击第几项
            home_banner_viewpager.setOnPagerItemClickListenner(new OnPagerItemClickListenner() {
                @Override
                public void onPageItemClick(int pagerItem) {
                    // TODO 广告图片点击事件
                    Intent intent = new Intent();
                    intent.setClass(getContext(), BannerDetailsActivity.class);
                    intent.putExtra("url", homeBean.getAdvertiseList().get(pagerItem).getAdLink());
                    intent.putExtra("remarks", homeBean.getAdvertiseList().get(pagerItem).getRemarks());
                    getContext().startActivity(intent);

                }

            });
        }
        // 设置消息
        if (homeBean.getPushmessageList().size() > 0) {
            /*list = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Map<String,String> map=new HashMap<>();
                map.put("1","content");
                list.add(map);
            }*/
            messageadapter = new Home_MessageAdapter(getActivity(), homeBean.getPushmessageList(), advertise);
//            messageadapter = new Home_MessageAdapter(getActivity(), list, advertise);
            home_message_listveiw.setAdapter(messageadapter);

            if (homeBean.getPushmessageList().size() >= 6) {
                home_message_listveiw.setPullLoadEnable(true);
            } else {
                home_message_listveiw.setPullLoadEnable(false);
            }
            /* home_message_listveiw.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // type1:跳URL type2 :跳订单详情
                    if (homeBean.getPushmessageList().get(position).getType().toString().equals("1")) {// load网址看详情
                        Intent intent = new Intent(getActivity(), PushDetialsActivity.class);
                        intent.putExtra("url", homeBean.getPushmessageList().get(position).getLink());
                        startActivity(intent);
                    } else if (homeBean.getPushmessageList().get(position).getType().toString().equals("2")) {// 进入订单详情
                        if (homeBean.getPushmessageList().get(position).getStatus().toString().equals("1")) {// 如果订单状态是1可以进入
                            Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                            intent.putExtra("orderid", homeBean.getPushmessageList().get(position).getLink());
                            intent.putExtra("orderstate", homeBean.getPushmessageList().get(position).getStatus());// 在进行中页面进入到订单详情的时候，订单状态为实际的订单状态
                            startActivity(intent);
                        } else {// 否则其他订单状态都不能进入
                            T.showShort(getActivity(), "该订单状态已改变");
                            homeBean.getPushmessageList().remove(position);
                            if (messageadapter != null)
                                messageadapter.notifyDataSetChanged();
                        }
                    }
                }
            });*/


            home_message_listveiw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                /**
                 * @param parent
                 * @param view
                 * @param position
                 * @param id
                 */
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Home_PushMessageListBean bean = messageadapter.getItem(position - 1);
                    int[] location2 = new int[2];
                    view.getLocationOnScreen(location2);
                    Log.e("message", position + "：" + bean.getContent() + ",location:x=" + location2[0] + ",y=" + location2[1]);
                    View pop = View.inflate(getContext(), R.layout.v2_pop_homemessage, null);
                    TextView content = (TextView) pop.findViewById(R.id.content);
                    content.setText(bean.getContent());
                    pop.setLayoutParams(new ViewGroup.LayoutParams(ScreenUtils.getScreenWidth(getContext()), 300));
                    if (popupShowBig != null && popupShowBig.isShowing()) {
                        popupShowBig.dismiss();
                    }
                    popupShowBig = new PopupWindow(getContext());

                    popupShowBig.setWidth(ScreenUtils.getScreenWidth(getContext()) + 4);
                    popupShowBig.setHeight(300);

                    popupShowBig.setContentView(pop);

                    popupShowBig.showAtLocation(view, Gravity.TOP, location2[0], location2[1] - 20);
                    pop.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupShowBig.dismiss();
                        }
                    });
                    popupShowBig.setBackgroundDrawable(getDrawable());
                    popupShowBig.setOutsideTouchable(true);
                }
            });


        }
    }

    /**
     * @return
     */
    private Drawable getDrawable() {
        ShapeDrawable bgdrawable = new ShapeDrawable(new OvalShape());
        bgdrawable.getPaint().setColor(getContext().getResources().getColor(android.R.color.transparent));
        return bgdrawable;
    }

    /**
     *
     */
    PopupWindow popupShowBig;

    /**
     * 成功获取到首页公告列表更多数据的回调
     *
     * @param homeMoreBean
     */
    @Override
    public void getHomeMoreData(Home_PushMessageListMoreBean homeMoreBean) {

        if (page == 1) {
            if (messageadapter != null) {
                messageadapter.updataList(homeMoreBean.getPushmessageList(), 1);
            }
        } else if (page > 1) {
            if (messageadapter != null) {
                messageadapter.updataList(homeMoreBean.getPushmessageList(), 2);
            }
        }
    }

    /**
     *
     */
    HttpController controller;

    /**
     * getData()1后获取的定位信息
     *
     * @param location
     * @param lat
     * @param lng
     */
    @Override
    public void onLocation(String location, double lat, double lng) {
        baiduMap.LocationStop();
        controller = new HttpController(getActivity());
        controller.setGetHomeDataListener(this);//请求首页数据监听2
        controller.setOnGetAllDriverListener(this);//
        controller.getHomeData(lat, lng);//得到定位请求数据

        if (isFirstLoc) {
            isFirstLoc = false;
            // 定义Maker坐标点
            if (lat == 0 && lng == 0) {
                return;
            }
            meLocationLat = lat;
            meLocationLng = lng;
            initOverlay(meLocationLat, meLocationLng);
            locationmapview.postDelayed(new Runnable() {
                @Override
                public void run() {
                    BaiduMap_Utils.perfomZoom(mBaiduMap, BaiduMap_Utils.zoomLevel);
                }
            }, 1000);
        }
    }

    // 初始化自己的位置
    public void initOverlay(final double lat, final double lon) {
        if (mMarkerLocation != null) {
            return;
        }
        if (mBaiduMap == null) {
            return;
        }
        // if(bdLocation == null){
        // bdLocation =
        // BitmapDescriptorFactory.fromResource(R.drawable.place_distination);
        // }
        // add marker overlay
        LatLng llCurrent = new LatLng(lat, lon);
        // OverlayOptions ooA = new
        // MarkerOptions().position(llCurrent).icon(bdLocation).zIndex(9).draggable(true);
        // mMarkerLocation = (Marker)(mBaiduMap.addOverlay(ooA));
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(llCurrent);
        mBaiduMap.animateMapStatus(u);

        //点击自己定位的图标返回自己的定位
        imageViewMyLocationHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng llCurrent = new LatLng(lat, lon);
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(llCurrent);
                mBaiduMap.animateMapStatus(u);
            }
        });


    }


    @Override
    public void onRefresh() {
//        page = 1;
//        getData();
        onLoad();
    }

    @Override
    public void onLoadMore() {
        page += 1;
        if (pushMessageListSize >= 6) {
            getMoreData();
        }
        onLoad();
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    String name;
    String city;

    // 地理反编码后得到的具体位置
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            T.showShort(getContext(), "抱歉，未能找到结果");
            return;
        }
        name = result.getAddressDetail().street;
        city = result.getAddressDetail().city;
        L.e("name", "name is" + name);
        L.e("city", "city is" + city);
        location_add_textview.setBackgroundResource(R.drawable.location_text);
        location_add_textview.setText(result.getAddressDetail().province + result.getAddressDetail().city
                + result.getAddressDetail().district + result.getAddressDetail().street);

        if (mTasks != null && timeTask != null) {
            timeTask.removeCallbacks(mTasks);
        }
        location_add_textview.setVisibility(View.VISIBLE);
        locationAddImageView.setVisibility(View.VISIBLE);

        int time = 5000;
        timeTask.postDelayed(mTasks, time);

    }

    /**
     *
     */
    private Handler timeTask = new Handler();
    /**
     *
     */
    private Runnable mTasks = new Runnable() {
        @Override
        public void run() {

            location_add_textview.setVisibility(View.GONE);
            locationAddImageView.setVisibility(View.GONE);
        }
    };

    //点击获取用户状态 身份是否验证
    @Override
    public void getUserStatus(String status) {
        UserVerifyUtils.VerifyImage(getContext(), status);
    }

    //获取所有司机的位子
    @Override
    public void getAllDriver(final List<AllDriverLocationBean> driverlocationlist) {
        //3获取周围的司机
        mMarkerSurroundCars = new ArrayList<Marker>();
        if (driverlocationlist == null) {
            return;
        }
        mBaiduMap.clear();
        for (AllDriverLocationBean bean : driverlocationlist) {
            meLat = bean.getLat();
            melng = bean.getLng();
            LatLng llCurrent = new LatLng(meLat, melng);
            // if (bean.getLat() == currentPlace.getLat() && bean.getLng() ==
            // bean.getLng())
            // {
            // break;
            // }
            OverlayOptions ooA = new MarkerOptions().position(llCurrent).icon(bdDirver).zIndex(9).draggable(false);
            Marker marker = (Marker) (mBaiduMap.addOverlay(ooA));
            mMarkerSurroundCars.add(marker);
        }

        //蒋米兰
        //添加marker点击事件的监听
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for (int i = 0; i < driverlocationlist.size(); i++) {
                    lat = driverlocationlist.get(i).getLat();
                    lng = driverlocationlist.get(i).getLng();
                    license = driverlocationlist.get(i).getName();

                    if (marker.getPosition().latitude == lat && marker.getPosition().longitude == lng) {
                        //infowindow中的布局
                        TextView tv = new TextView(getContext());
                        tv.setBackgroundResource(R.drawable.infowindowbackgroud);
                        tv.setTextColor(Color.WHITE);
                        tv.setTextSize(13);
                        tv.setText(license + "\n" + "400-796-9898");
                        tv.setGravity(Gravity.CENTER);
                        bitmapDescriptor = BitmapDescriptorFactory.fromView(tv);
                    }
                }

                //infowindow位置
                LatLng ll = marker.getPosition();
                LatLng latLng = new LatLng(ll.latitude, ll.longitude);
                //infowindow点击事件
                InfoWindow.OnInfoWindowClickListener listener = new InfoWindow.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick() {
                        //拨打客服电话
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_DIAL);
                        Uri uri = Uri.parse("tel://" + "400-796-9898");
                        intent.setData(uri);
                        startActivity(intent);
                    }
                };
                //显示infowindow
                infoWindow = new InfoWindow(bitmapDescriptor, latLng, -90, listener);
                mBaiduMap.showInfoWindow(infoWindow);
                return true;
            }
        });

        //地图点击事件
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                return false;
            }

            @Override
            public void onMapClick(LatLng arg0) {
                //点击地图，infowindow消失
                mBaiduMap.hideInfoWindow();
            }
        });

        // 货主当前位置再次插上图标
        // LatLng llCurrent = new LatLng(meLocationLat, meLocationLng);
        // OverlayOptions ooA = new
        // MarkerOptions().position(llCurrent).icon(bdLocation).zIndex(9).draggable(true);
        // mMarkerLocation = (Marker)(mBaiduMap.addOverlay(ooA));
        // MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(llCurrent);
        // mBaiduMap.animateMapStatus(u);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationmapview.onDestroy();
        if (mBaiduMap != null) {
            mBaiduMap.clear();
            mBaiduMap = null;
        }
        // if(bdLocation != null){
        // bdLocation.recycle();
        // }
        if (bdDirver != null) {
            bdDirver.recycle();
        }
        if (locationactivity != null) {
            locationactivity = null;
        }
    }
}
