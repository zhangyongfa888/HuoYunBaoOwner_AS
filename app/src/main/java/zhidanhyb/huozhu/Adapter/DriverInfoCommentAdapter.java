package zhidanhyb.huozhu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import zhidanhyb.huozhu.Base.HYBHuoZhuApplication;
import zhidanhyb.huozhu.Bean.DriverInfo_CommentBean;
import zhidanhyb.huozhu.R;
/**
 * 评价列表的adapter
 * @author lxj
 *
 */
public class DriverInfoCommentAdapter extends BaseAdapter {

	private Context mContext;
	private List<DriverInfo_CommentBean> commentList;
	private ViewHolder viewHolder;
	private ImageLoader imageLoader;
	public DriverInfoCommentAdapter(Context context, List<DriverInfo_CommentBean> list) {
		this.mContext = context;
		this.commentList = list;
	}
	
	@Override
	public int getCount() {
		if(commentList == null){
			return 0;
		}
		return commentList.size();
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
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.user_comment_itemlayout, null);
//			viewHolder.imageview = (ImageView)convertView.findViewById(R.id.comment_imageview);//横线
			viewHolder.user_head_imageview = (ImageView)convertView.findViewById(R.id.comment_user_head_imageview);//用户头像
			viewHolder.user_phone_textview = (TextView)convertView.findViewById(R.id.comment_user_phone_textview);//用户手机号
			viewHolder.user_grade_textview = (TextView)convertView.findViewById(R.id.comment_user_grade_textview);//用户评分
			viewHolder.user_content_textview = (TextView)convertView.findViewById(R.id.comment_user_content_textview);//用户评论内容
			viewHolder.user_time_textview = (TextView)convertView.findViewById(R.id.comment_user_time_textview);//用户评论时间
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		if(commentList.get(position).getPic().toString().equals("")){
			viewHolder.user_head_imageview.setImageDrawable(mContext.getResources().getDrawable(R.drawable.head));
		}else{
			imageLoader = ImageLoader.getInstance();
			imageLoader.displayImage(commentList.get(position).getPic(), viewHolder.user_head_imageview, HYBHuoZhuApplication.options);
		}
		viewHolder.user_phone_textview.setText(commentList.get(position).getMobile());
		viewHolder.user_grade_textview.setText("+"+commentList.get(position).getScore());
		viewHolder.user_content_textview.setText(commentList.get(position).getComment());
		viewHolder.user_time_textview.setText(commentList.get(position).getCreated_on());
		
		return convertView;
	}


	/**
	 *
	 */
	class ViewHolder {
		ImageView user_head_imageview;
		TextView user_phone_textview,user_grade_textview,user_content_textview,user_time_textview;
	}
}
