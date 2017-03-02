package zhidanhyb.huozhu.Activity.Order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

import java.util.ArrayList;
import java.util.List;

import zhidanhyb.huozhu.Adapter.PoiSearch_Location_Adapter;
import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.Bean.LatLng;
import zhidanhyb.huozhu.Bean.SuggestionInfoBean;
import zhidanhyb.huozhu.Config.ConstantConfig;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.T;

/**
 * 填写订单时的地址搜索页面
 * @author lxj
 *
 */
public class SendOrderLocationActivity extends BaseActivity{

	private ListView search_listview;
	private RelativeLayout search_left_rl;
	private EditText search_loaction_edittext;
	private List<PoiInfo> Poilist = null;
	private PoiSearch_Location_Adapter adapter;
	/**
	 *
	 */
	private ImageView search_loaction_icon;
	private SuggestionSearch mSuggestionSearch;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.sendorderlocationlayout);
		initView();
	}

	/**
	 *
	 */
	@SuppressLint("NewApi")
	private void initView() {
		mSuggestionSearch = SuggestionSearch.newInstance();//在线查询
		mSuggestionSearch.setOnGetSuggestionResultListener(listener);
		search_left_rl = (RelativeLayout)findViewById(R.id.search_left_rl);//返回
		search_left_rl.setOnClickListener(this);
		search_loaction_icon = (ImageView)findViewById(R.id.search_loaction_icon);//图标
		search_listview = (ListView)findViewById(R.id.search_listview);// 搜索结果后的列表
		search_loaction_edittext = (EditText)findViewById(R.id.search_loaction_edittext);//搜索地址
		adapter = new PoiSearch_Location_Adapter(SendOrderLocationActivity.this);	
		search_listview.setAdapter(adapter);

		if(SendOrderActivity.poiSearch == 1){//搜索起始地
			search_loaction_icon.setBackground(getResources().getDrawable(R.drawable.v2_img_sendorder_start));
		}else if(SendOrderActivity.poiSearch == 2){//搜索目的地
			search_loaction_icon.setBackground(getResources().getDrawable(R.drawable.v2_img_sendorder_end));
		}
		search_loaction_edittext.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(s.length() > 0){
					if(ConstantConfig.City != null){
						// 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新  
						mSuggestionSearch.requestSuggestion(new SuggestionSearchOption()
								.keyword(s.toString())
								.city(ConstantConfig.City));
						//						mPoiSearch.searchInCity((new PoiCitySearchOption()).keyword(s.toString()).city(ConstantConfig.City).pageNum(0));
					}else{
						mSuggestionSearch.requestSuggestion(new SuggestionSearchOption()
								.keyword(s.toString())
								.city("廊坊市"));
					}
				}
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void afterTextChanged(Editable s) {}
		});

		search_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(SuggestionInfo.size() > 0){
					Intent intent = new Intent(SendOrderLocationActivity.this,SendOrderActivity.class);
					intent.putExtra("name",SuggestionInfo.get(position).getKey()+"");
					intent.putExtra("city",SuggestionInfo.get(position).getCity()+"");
					intent.putExtra("lat",SuggestionInfo.get(position).getLatlng().getLatitude()+"");
					intent.putExtra("lng",SuggestionInfo.get(position).getLatlng().getLongitude()+"");
					if(SendOrderActivity.poiSearch == 1){//搜索起始地
						setResult(SendOrderActivity.startLocation, intent);
					}else if(SendOrderActivity.poiSearch == 2){//搜索目的地
						setResult(SendOrderActivity.endLocation, intent);
					}
					finish();
				}
			}
		});
	}
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.search_left_rl:
			finish();
			break;
		default:
			break;
		}
	}
	private List<SuggestionInfoBean> SuggestionInfo = new ArrayList<SuggestionInfoBean>();
	OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {  


		public void onGetSuggestionResult(SuggestionResult res) {  
			if (res == null || res.getAllSuggestions() == null) {  
				T.showLong(SendOrderLocationActivity.this, "未找到结果");
				return;
			}
			
			if(SuggestionInfo.size() > 0){
				SuggestionInfo.clear();
			}
			//获取在线建议检索结果  
			for (com.baidu.mapapi.search.sug.SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
//				if (info.city != null){
					SuggestionInfoBean bean = new SuggestionInfoBean();
					if(info.pt != null){
						LatLng latLng = new LatLng();
						latLng.setLatitude(info.pt.latitude);
						latLng.setLongitude(info.pt.longitude);
						bean.setLatlng(latLng);
						if(info.city == null){
							bean.setCity(info.key);//如果城市是null，那就去key，司机端抢单列表显示用的
						}else{
							bean.setCity(info.city);
						}
						if(info.district != null){
							bean.setDistrict(info.district);
						}else{
							bean.setDistrict("");
						}
						bean.setKey(info.key);
						SuggestionInfo.add(bean);
					}
//				}
			}
			if(adapter != null){
				adapter.updataList(SuggestionInfo);
			}
		}  
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		search_listview = null;
		search_left_rl = null;
		search_loaction_edittext = null;
		if(Poilist != null){
			Poilist.clear();
			Poilist = null;
		}
		if(adapter != null){
			adapter = null;
		}
		search_loaction_icon = null;

		mSuggestionSearch.destroy();
	}
}
