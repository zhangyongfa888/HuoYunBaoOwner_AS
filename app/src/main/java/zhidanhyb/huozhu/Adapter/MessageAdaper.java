package zhidanhyb.huozhu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zhidanhyb.huozhu.Bean.MessageListBean;
import zhidanhyb.huozhu.R;

/**
 * 消息adapter
 *
 * @author lxj
 */
public class MessageAdaper extends BaseAdapter {
    private Context mContext;
    private List<MessageListBean> messagelist = new ArrayList<MessageListBean>();
    ;
    private ViewHolder viewHolder;

    public MessageAdaper(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return messagelist.size();
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

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_item_layout, null);
            viewHolder.delete_checkbox = (CheckBox) convertView.findViewById(R.id.message_delete_checkbox);//删除按钮
            viewHolder.stutas_imageview = (ImageView) convertView.findViewById(R.id.message_stutas_imageview);//消息状态
            viewHolder.status_name_textview = (TextView) convertView.findViewById(R.id.message_status_name_textview);//消息名称
            viewHolder.status_time_textview = (TextView) convertView.findViewById(R.id.message_status_time_textview);//消息时间
            viewHolder.status_content_textview = (TextView) convertView.findViewById(R.id.message_status_content_textview);//消息内容
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (messagelist.get(position).isDelete()) {
            viewHolder.delete_checkbox.setVisibility(View.VISIBLE);
            viewHolder.stutas_imageview.setVisibility(View.GONE);

        } else {
            viewHolder.delete_checkbox.setVisibility(View.GONE);
            viewHolder.stutas_imageview.setVisibility(View.VISIBLE);
        }
        if (messagelist.get(position).isSelector()) {
            viewHolder.delete_checkbox.setChecked(true);
        } else {
            viewHolder.delete_checkbox.setChecked(false);
        }
        viewHolder.status_name_textview.setText(messagelist.get(position).getTitle());
        viewHolder.status_time_textview.setText(messagelist.get(position).getCreated_on());
        viewHolder.status_content_textview.setText(messagelist.get(position).getContent());
        return convertView;
    }


    class ViewHolder {
        CheckBox delete_checkbox;
        /**
         *
         */
        ImageView stutas_imageview;
        TextView status_name_textview, status_time_textview, status_content_textview;

    }

    public void updataList(List<MessageListBean> list, int i) {
        if (i == 1) {
            messagelist = list;
        } else if (i == 2) {
            messagelist.addAll(list);
        }
        notifyDataSetChanged();
    }

    public List<MessageListBean> getMessageList() {
        if (messagelist != null) {
            return messagelist;
        }
        return null;
    }
}
