package zhidanhyb.huozhu.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import zhidanhyb.huozhu.Activity.Order.SendOrderActivity;
import zhidanhyb.huozhu.Bean.SuggestionInfoBean;
import zhidanhyb.huozhu.R;

/**
 * 发订单时选择地点时 的Poi搜索的Adapter
 * @author lxj
 *
 */
public class PoiSearch_Location_Adapter extends BaseAdapter {


	private Context mContext;
	private List<SuggestionInfoBean>poiList;
	private ViewHolder viewHolder;

	public PoiSearch_Location_Adapter(Context context) {
		this.mContext = context;
	}
	@Override
	public int getCount() {
		if(poiList == null){
			return 0;
		}
		return poiList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 */
	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.poisearchlayout, null);

			viewHolder.icon_imageview = (ImageView)convertView.findViewById(R.id.poisearch_icon_imageview);
			viewHolder.address_textview = (TextView)convertView.findViewById(R.id.poisearch_address_textview);
			viewHolder.particular_textview = (TextView)convertView.findViewById(R.id.poisearch_particular_textview);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if(SendOrderActivity.poiSearch == 1){//搜索起始地点
			viewHolder.icon_imageview.setBackground(mContext.getResources().getDrawable(R.drawable.v2_img_sendorder_start));
		}else if(SendOrderActivity.poiSearch == 2){//搜索目的地
			viewHolder.icon_imageview.setBackground(mContext.getResources().getDrawable(R.drawable.v2_img_sendorder_end));
		}
		viewHolder.address_textview.setText(poiList.get(position).getKey());
		viewHolder.particular_textview.setText(poiList.get(position).getCity()+poiList.get(position).getDistrict()+"");
		return convertView;
	}

	class ViewHolder{
		ImageView icon_imageview;
		TextView address_textview , particular_textview;
	}
	
	public void updataList(List<SuggestionInfoBean>poiList){
		this.poiList = poiList;
		notifyDataSetChanged();
	}
	
}
