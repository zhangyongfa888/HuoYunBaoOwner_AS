package zhidanhyb.huozhu.Activity.Order;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
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

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.Bean.AllDriverLocationBean;
import zhidanhyb.huozhu.Config.ZDSharedPreferences;
import zhidanhyb.huozhu.Connector.MyLocationListener;
import zhidanhyb.huozhu.Connector.MyLocationListener.OnLocationListener;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.getUserStatusListener;
import zhidanhyb.huozhu.HttpRequest.HttpController.onGetAllDriverListener;
import zhidanhyb.huozhu.Utils.BaiduMap_Utils;
import zhidanhyb.huozhu.Utils.L;
import zhidanhyb.huozhu.Utils.StringUtil;
import zhidanhyb.huozhu.Utils.T;
import zhidanhyb.huozhu.Utils.UserVerifyUtils;

public class SendOrder_ChooseLocByMapActivity extends BaseActivity
		implements OnLocationListener, getUserStatusListener, onGetAllDriverListener, OnGetGeoCoderResultListener {
	private MapView locationmapview;
	private BaiduMap mBaiduMap;
	private boolean isFirstLoc = true;
	private Marker mMarkerLocation; // 当前位置
	private ArrayList<Marker> mMarkerSurroundCars;
	// 初始化全局 bitmap 信息，不用时及时 recycle
	// private BitmapDescriptor bdLocation = null;
	private BitmapDescriptor bdDirver = null;
	private BaiduMap_Utils baiduMap;
	private HttpController controller;
	private double meLocationLat = 0;
	private double meLocationLng = 0;
	private TextView location_add_textview;
	private GeoCoder mSearch;
	public static SendOrder_ChooseLocByMapActivity locationactivity;
	public int type = 0;// 发货位置
	public String start_lat, start_lng, start_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_order__choose_loc_by_map);
		initView();

	}

	private void initView() {

		type = getIntent().getIntExtra("type", 0);

		setLeftButton();
		if (type == 0) {
			setTitleText("选择货物起始地");
		} else {
			setTitleText("选择货物目的地");

		}
		setHideRightButton();
		setRightText("确定");

		controller = new HttpController(this);
		controller.setOnGetAllDriverListener(this);
		locationactivity = this;
		bdDirver = BitmapDescriptorFactory.fromResource(R.drawable.place_driver);
		locationmapview = (MapView) findViewById(R.id.locationmapview);// 地图
		location_add_textview = (TextView) findViewById(R.id.location_add_textview);// 挪动地图，获取当前最新位置信息
		mBaiduMap = locationmapview.getMap();

		start_lat = getIntent().getStringExtra("start_lat");
		start_lng = getIntent().getStringExtra("start_lng");
		start_name = getIntent().getStringExtra("start_name");
		if (StringUtil.isEmpty(start_lat)) {
			mBaiduMap.setMyLocationEnabled(true);
			MyLocationListener onListener = new MyLocationListener();
			onListener.setOnLocationListener(this);
			baiduMap = new BaiduMap_Utils(this, onListener);
			baiduMap.LocationStart();
		} else {
			Log.e("start_lat", start_lat + "");
			Log.e("start_lng", start_lng + "");
			// 初始化发货地址
			location_add_textview.setText(start_name);
			initOverlay(Double.valueOf(start_lat), Double.valueOf(start_lng));

		}

		// 初始化搜索模块，注册事件监听
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);

		mBaiduMap.setOnMapStatusChangeListener(new OnMapStatusChangeListener() {
			public void onMapStatusChangeStart(MapStatus status) {

				// updateMapState();
			}

			public void onMapStatusChangeFinish(MapStatus status) {
				LatLng mCenterLatLng = status.target;
				/** 获取经纬度 */
				meLocationLat = mCenterLatLng.latitude;
				meLocationLng = mCenterLatLng.longitude;
				LatLng ptCenter = new LatLng(meLocationLat, meLocationLng);
				// 反Geo搜索
				mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(ptCenter));

				if (controller != null) {
					controller.upLoadNearbyDriver(meLocationLat, meLocationLng);
				}
			}

			public void onMapStatusChange(MapStatus status) {
				// updateMapState();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (locationmapview != null)
			locationmapview.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		if (locationmapview != null)
			locationmapview.onPause();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		switch (v.getId()) {
		case R.id.title_left_rl:// 返回
			finish();
			break;
		case R.id.title_right_textview:// 选择好了
			// XiaDan();
			if (StringUtil.isEmpty(name) ) {
				T.showShort(this, "请具体到线路,方便司机联系您！");
				return;
			}
			Intent intent = new Intent(this, SendOrderActivity.class);
			intent.putExtra("name", name + "");
			intent.putExtra("city", city + "");
			intent.putExtra("lat", meLocationLat + "");
			intent.putExtra("lng", meLocationLng + "");
			if (type == 0) {// 搜索起始地
				setResult(SendOrderActivity.startLocation, intent);
			} else if (type == 1) {// 搜索目的地
				setResult(SendOrderActivity.endLocation, intent);
			}
			finish();

			break;
		default:
			break;
		}
	}

	private void XiaDan() {
		if (!ZDSharedPreferences.getInstance(this).getUserStatus().toString().equals("2")) {// 状态status
																							// !=
																							// 2
																							// 获取用户状态
			HttpController controller = new HttpController(this);
			String type = "2";
			controller.setGetUserStatusListenenr(this);
			controller.getUserStatus(type);
		} else if (ZDSharedPreferences.getInstance(this).getUserStatus().toString().equals("2")) {// 状态status
																									// =
																									// 2
																									// 直接跳转到发单页面

			Intent intent = new Intent(this, SendOrderActivity.class);
			intent.putExtra("name", name);
			intent.putExtra("city", city);
			intent.putExtra("lat", meLocationLat + "");
			intent.putExtra("lng", meLocationLng + "");

			L.e("name", "name is" + name);
			L.e("city", "city is" + city);
			L.e("lat", meLocationLat + "");
			L.e("lng", meLocationLng + "");
			startActivity(intent);
			finish();
		}
	}

	String name;
	String city;

	@Override
	protected void onDestroy() {
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

	@Override
	public void onLocation(String location, double lat, double lng) {
		baiduMap.LocationStop();
		if (controller != null) {
			controller.upLoadNearbyDriver(lat, lng);
		}
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
	public void initOverlay(double lat, double lon) {
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
	}

	// 设置所有司机的位置
	public void DirvierLocation() {

	}

	// 获取用户状态成功回调的接口
	@Override
	public void getUserStatus(String status) {
		UserVerifyUtils.VerifyImage(this, status);
	}

	@Override
	public void getAllDriver(List<AllDriverLocationBean> driverlocationlist) {
		mMarkerSurroundCars = new ArrayList<Marker>();
		if (driverlocationlist == null) {
			return;
		}
		mBaiduMap.clear();
		for (AllDriverLocationBean bean : driverlocationlist) {
			LatLng llCurrent = new LatLng(bean.getLat(), bean.getLng());
			// if (bean.getLat() == currentPlace.getLat() && bean.getLng() ==
			// bean.getLng())
			// {
			// break;
			// }
			OverlayOptions ooA = new MarkerOptions().position(llCurrent).icon(bdDirver).zIndex(9).draggable(false);
			Marker marker = (Marker) (mBaiduMap.addOverlay(ooA));
			mMarkerSurroundCars.add(marker);
		}
		// 货主当前位置再次插上图标
		// LatLng llCurrent = new LatLng(meLocationLat, meLocationLng);
		// OverlayOptions ooA = new
		// MarkerOptions().position(llCurrent).icon(bdLocation).zIndex(9).draggable(true);
		// mMarkerLocation = (Marker)(mBaiduMap.addOverlay(ooA));
		// MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(llCurrent);
		// mBaiduMap.animateMapStatus(u);
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult arg0) {

	}

	// 地理反编码后得到的具体位置
	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			T.showShort(this, "抱歉，未能找到结果");
			return;
		}
		name = result.getAddressDetail().street;
		city = result.getAddressDetail().city;
		L.e("name", "name is" + name);
		L.e("city", "city is" + city);
		location_add_textview.setBackgroundResource(R.drawable.location_text);
		location_add_textview.setText(result.getAddressDetail().province + result.getAddressDetail().city
				+ result.getAddressDetail().district + result.getAddressDetail().street);

	}

}
