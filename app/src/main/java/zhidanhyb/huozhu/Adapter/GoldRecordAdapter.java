package zhidanhyb.huozhu.Adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Bean.Expense_CalendarBean;
/**
 * 2
 * @author lxj
 *
 */
public class GoldRecordAdapter extends BaseAdapter {

	private Context mContext;
	private ViewHolder viewholder;
	private List<Expense_CalendarBean> BeansList;
	public GoldRecordAdapter(Context context) {
		this.mContext = context;
	}
	
	@Override
	public int getCount() {
		if(BeansList == null){
			return 0;
		}
		return BeansList.size();
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.goldrecordlistitem, null);
			viewholder = new ViewHolder();
			viewholder.id_textview = (TextView)convertView.findViewById(R.id.goldrecord_id_textview);//
			viewholder.jiedan_textview = (TextView)convertView.findViewById(R.id.goldrecord_jiedan_textview);//
			viewholder.context_textview = (TextView)convertView.findViewById(R.id.goldrecord_context_textview);//
			convertView.setTag(viewholder);
		}else{
			viewholder = (ViewHolder) convertView.getTag();
		}
		
		if(BeansList.size() > 0){
			viewholder.id_textview.setText("交易流水号: "+BeansList.get(position).getId());
			//0审核中 1成功2失败
			String status = BeansList.get(position).getStatus();
			if(status.toString().equals("0")){
				viewholder.jiedan_textview.setText("审核中");
			}else if(status.toString().equals("1")){
				viewholder.jiedan_textview.setText("完成");
			}else if(status.toString().equals("2")){
				viewholder.jiedan_textview.setText("失败");
			}
			String content = "类型: "+BeansList.get(position).getType()+"\n数量: "+BeansList.get(position).getGolds()+"\n操作时间: "+BeansList.get(position).getCreated_on();
			viewholder.context_textview .setText(content);
		}
		
		return convertView;
	}
	
	class ViewHolder{
		TextView id_textview,jiedan_textview,context_textview;
	}
	
	public void updataList(List<Expense_CalendarBean> calendarBeansList, int i){
		if(i == 1){
			BeansList = calendarBeansList;
		}else if(i == 2){
			if(BeansList != null){
				BeansList.addAll(calendarBeansList);
			}
		}
		notifyDataSetChanged();
	}

}
