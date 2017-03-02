package zhidanhyb.huozhu.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import zhidanhyb.huozhu.Activity.Order.ModificationOrderActivity;
import zhidanhyb.huozhu.Bean.Order_SendSureBean;
import zhidanhyb.huozhu.R;

/**
 * 货主添加订单列表的Adapter
 *
 * @author lxj
 */
public class SendOrderListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Order_SendSureBean> orderlist;
    private ViewHolder viewholder;

    public SendOrderListAdapter(Context context, List<Order_SendSureBean> sendSureList) {
        this.mContext = context;
        this.orderlist = sendSureList;
    }

    @Override
    public int getCount() {
        return orderlist.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.sendorderlist_itemlayout, null);
            viewholder = new ViewHolder();
            viewholder.evaluate_textview = (TextView) convertView.findViewById(R.id.orderlist_evaluate_textview);//预报价
            viewholder.context_textview = (TextView) convertView.findViewById(R.id.orderlist_context_textview);//内容
//			viewholder.delete_button = (Button)convertView.findViewById(R.id.orderlist_delete_button);//删除
            viewholder.modification_button = (Button) convertView.findViewById(R.id.orderlist_modification_button);//修改
            viewholder.orderlist_remark_textview = (TextView) convertView.findViewById(R.id.orderlist_remark_textview);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        String content = "起点 : " + orderlist.get(position).getStarAdr() + "\n终点 : " + orderlist.get(position).getEndAdr() + "\n货物 : " + orderlist.get(position).getGoodsType();
        viewholder.context_textview.setText(content);
        viewholder.evaluate_textview.setText(orderlist.get(position).getEvaluate());
        viewholder.orderlist_remark_textview.setText(orderlist.get(position).getOrderDetail());
//		viewholder.delete_button.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				SendOrder_List_DeleteDialog dialog = new SendOrder_List_DeleteDialog(mContext, R.style.dialog);
//				dialog.setOnSendOrderListDeleteListener(new onSendOrderListDeleteListener() {
//					@Override
//					public List<Order_SendSureBean> getOrderList() {
//						if(orderlist == null){
//							return null;
//						}
//						return orderlist;
//					}
//					@Override
//					public void onDelete(List<Order_SendSureBean> list) {
//						orderlist = list;
//						if(list.size() == 0){
//							updataListListener.onUpdata();
//						}
////						if(SendOrderActivity.OrderActivity != null){
////							SendOrderActivity.OrderActivity.onUpdataList(position);
////						}
//						notifyDataSetChanged();
//					}
//				});
//				dialog.showDialog(position);
//			}
//		});

        viewholder.modification_button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ModificationOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("order", orderlist.get(position));
                intent.putExtra("position", position);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        //

        return convertView;
    }

    /**
     *
     */
    class ViewHolder {
        TextView evaluate_textview, context_textview, orderlist_remark_textview;
        Button modification_button;
    }

    private onUpdataListListener updataListListener;

    public void setOnUpdataListListener(onUpdataListListener onListener) {
        this.updataListListener = onListener;
    }

    public interface onUpdataListListener {
        void onUpdata();
    }

}
