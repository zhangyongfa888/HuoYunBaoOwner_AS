package zhidanhyb.huozhu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import zhidanhyb.huozhu.Base.HYBHuoZhuApplication;
import zhidanhyb.huozhu.Bean.CommentBean;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.View.CircleImageView;

/**
 * 用户评论的Adapter
 * @author lxj
 *
 */
public class User_CommentAdapter extends BaseAdapter {

	private Context mContext;
	private List<CommentBean>commentlist;
	private ViewHolder viewHolder;
	private ImageLoader imageLoader;
	public User_CommentAdapter(Context context , List<CommentBean> list) {
		this.mContext = context;
		this.commentlist = list;
		imageLoader = ImageLoader.getInstance();
	}
	@Override
	public int getCount() {
		return commentlist.size();
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
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.user_comment_itemlayout, null);
			viewHolder.user_head_imageview = (CircleImageView)convertView.findViewById(R.id.comment_user_head_imageview);//用户头像
			viewHolder.user_head_imageview.setBorderWidth(30);
			viewHolder.user_phone_textview = (TextView)convertView.findViewById(R.id.comment_user_phone_textview);//用户手机号
			viewHolder.user_grade_textview = (TextView)convertView.findViewById(R.id.comment_user_grade_textview);//用户评分
			viewHolder.user_content_textview = (TextView)convertView.findViewById(R.id.comment_user_content_textview);//用户评论内容
			viewHolder.user_time_textview = (TextView)convertView.findViewById(R.id.comment_user_time_textview);//用户评论时间
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if(commentlist.get(position).getPic().toString().equals("") || commentlist.get(position).getPic() == null){
			viewHolder.user_head_imageview.setImageDrawable(mContext.getResources().getDrawable(R.drawable.head));
		}else{
			imageLoader.displayImage(commentlist.get(position).getPic(), viewHolder.user_head_imageview, HYBHuoZhuApplication.options);
		}
		String phone = commentlist.get(position).getCommented_mobile();
		phone = phone.substring(0, phone.length()-4)+"****";
		viewHolder.user_phone_textview.setText(phone);
		viewHolder.user_grade_textview.setText("评分 +"+commentlist.get(position).getScore());
		viewHolder.user_content_textview.setText(commentlist.get(position).getComment());
		viewHolder.user_time_textview.setText(commentlist.get(position).getCreated_on());
		
		return convertView;
	}


	/**
	 *
	 */
	private class ViewHolder{
		/**
		 *
		 */
		CircleImageView user_head_imageview;
		TextView user_phone_textview,user_grade_textview,user_content_textview,user_time_textview;
	}

}
