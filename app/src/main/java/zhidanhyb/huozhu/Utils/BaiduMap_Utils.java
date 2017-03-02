package zhidanhyb.huozhu.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import zhidanhyb.huozhu.Connector.MyLocationListener;

public class BaiduMap_Utils implements OnGetRoutePlanResultListener {

    /**
     * 用作地图缩放： 缩略尺寸 3.0-19.0;
     */
    public static final float zoomLevel = 14.0f;
    private Context mContext;
    private static LocationClient mLocationClient;
    private DrivingRouteResult Cargo_Result;//货物起点到终点的规划路线的信息。
    private String GoodsWay = "0";//不同阶段显示不同的规划路线   1-显示司机和起点货物的路线   2货物起止路线
    private RoutePlanSearch mSearch;
    private TextView distanceTextView;

    public BaiduMap_Utils(Context context) {
        this.mContext = context;
    }

    public BaiduMap_Utils(Context context, MyLocationListener onListener) {
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(context.getApplicationContext());//声明LocationClient类
        }
        mLocationClient.registerLocationListener(onListener);    //注册监听函数
        initLocation();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 0;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    public static void LocationStop() {
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
    }

    public static void LocationStart() {
        if (mLocationClient != null) {
            mLocationClient.start();
        }
    }

    /**
     * 处理缩放 sdk 缩放级别范围： [3.0,19.0]
     *
     * @param mBaiduMap 百度地图 Open Declaration com.baidu.mapapi.map.BaiduMap
     * @param zoomLevel 缩放级别范围： [3.0,19.0]
     */
    public static void perfomZoom(BaiduMap mBaiduMap, float zoomLevel) {
        try {
            if (mBaiduMap == null) {
                return;
            }
            MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(zoomLevel);
            mBaiduMap.animateMapStatus(u);
        } catch (NumberFormatException e) {
            //Toast.makeText(mContext, "请输入正确的缩放级别", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取规划路线
     *
     * @param startlat          - 起点纬度
     * @param startlng          - 起点经度
     * @param endlat            - 终点纬度
     * @param endlng            - 终点经度
     * @param WayType           - 路线类型
     * @param distance_textview
     */
    public void GetDriverToGoodsStart(double startlat, double startlng, double endlat, double endlng, String WayType, TextView distance_textview) {
        Log.i("GetDriverToGoodsStart", "startlat:" + startlat + ",startlng:" + startlng + ",endlat:" + endlat + ",endlng" + endlng + ",WayType:" + WayType);
        GoodsWay = WayType;
        distanceTextView = distance_textview;
        //创建驾车线路规划检索实例；
        mSearch = RoutePlanSearch.newInstance();
        //设置驾车线路规划检索监听者
        mSearch.setOnGetRoutePlanResultListener(this);
        LatLng startlatlng = new LatLng(startlat, startlng);
        PlanNode stNode = PlanNode.withLocation(startlatlng);
        LatLng endlatlng = new LatLng(endlat, endlng);
        PlanNode enNode = PlanNode.withLocation(endlatlng);
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(stNode)
                .to(enNode));
    }

    private onGetDirectionsListener getDirectionsListener;

    public void setOnGetDirectionsListener(onGetDirectionsListener onDirectionsListener) {
        this.getDirectionsListener = onDirectionsListener;
    }

    public interface onGetDirectionsListener {
        void getDriverToGoodsStart(String distances);

        void getGoodsStartToEnd(DrivingRouteResult Cargo_Result, String distances);
    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult arg0) {

    }

    /**
     * @param result
     */
    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        //获取驾车线路规划结果

        Log.e("onGetDrivingRouteResult", result.error + "");

        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(mContext, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
            return;
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            //result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            //				route = result.getRouteLines().get(0);
            Cargo_Result = result;
            double distances = 0;
            //取最近的一条规划路线   result.getRouteLines().get(0).getDistance();
            distances = result.getRouteLines().get(0).getDistance();
            if (GoodsWay.equals("2")) {//获取货物起始点的路线和距离
                if (getDirectionsListener != null) {
                    getDirectionsListener.getGoodsStartToEnd(Cargo_Result, "起始距离约" + distances / 1000 + "公里");
                }
            } else if (GoodsWay.equals("1")) {//获取司机到获取起点的距离
                if (distances > 0) {
                    if (distanceTextView != null) {
                        distanceTextView.setText("距离" + distances / 1000 + "公里");
                    }
                    //					if(getDirectionsListener != null){
                    //						getDirectionsListener.getDriverToGoodsStart("离我约"+distances/1000+"公里");
                    //					}
                } else if (distances == 0.0) {
                    if (distanceTextView != null) {
                        distanceTextView.setText("距离" + 0.0 + "公里");
                    }
                }else {
                    distanceTextView.setText("获取位置信息失败");

                }
            } else if (GoodsWay.equals("3")) {
                getDirectionsListener.getDriverToGoodsStart(distances / 1000 + "公里");

            } else {
                distanceTextView.setText("获取位置信息失败");
            }
        }
    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult arg0) {

    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult arg0) {

    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult arg0) {
        // TODO Auto-generated method stub

    }


}
