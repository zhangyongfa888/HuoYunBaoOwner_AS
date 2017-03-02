package zhidanhyb.huozhu.Adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Bean.BankListBean;
import zhidanhyb.huozhu.Utils.StringUtil;
/**
 * 银行卡列表
 * @author lxj
 *
 */
public class BankCardAdapter extends BaseAdapter {

	private Context mContext;
	private List<BankListBean>banklist;
	private ViewHolder viewHolder;
	
	public BankCardAdapter(Context context,List<BankListBean> banckList) {
		this.mContext = context;
		this.banklist = banckList;
	}
	@Override
	public int getCount() {
		return banklist.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.bankcarditemlayout, null);
			viewHolder = new ViewHolder();
			viewHolder.name_textview = (TextView)convertView.findViewById(R.id.bankcard_name_textview);
			viewHolder.user_textview = (TextView)convertView.findViewById(R.id.bankcard_user_textview);
			viewHolder.number_textview = (TextView)convertView.findViewById(R.id.bankcard_number_textview);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.name_textview.setText(banklist.get(position).getCard_bank());
		viewHolder.user_textview.setText("持卡人："+banklist.get(position).getCard_name());
		viewHolder.number_textview.setText(StringUtil.bankCardToAsterisk(banklist.get(position).getCard_no()));//
		
		return convertView;
	}

	class ViewHolder{
		TextView name_textview,user_textview,number_textview;
	}
	
	public void updataList(List<BankListBean> banckList) {
		this.banklist = banckList;
		notifyDataSetChanged();
	}
}
