package zhidanhyb.huozhu.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import zhidanhyb.huozhu.Activity.Order.Main_OrderActivity;
import zhidanhyb.huozhu.Activity.Order.OrderDetailsActivity;
import zhidanhyb.huozhu.Activity.Order.SendOrderActivity;
import zhidanhyb.huozhu.Bean.OrderListBean;
import zhidanhyb.huozhu.Config.ConstantConfig;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.View.DialogUtils;

/**
 * 订单adapter
 *
 * @author lxj
 */
public class OrderAdapter extends BaseAdapter {

    private ViewHolder viewHolder;
    private Context mContext;
    private List<OrderListBean> orderList;

    public OrderAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (orderList == null) {
            return 0;
        }
        return orderList.size();
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
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.orderlistitemlayout, null);
            viewHolder.id_textview = (TextView) convertView.findViewById(R.id.order_id_textview);//订单id
            viewHolder.jiedan_textview = (TextView) convertView.findViewById(R.id.order_jiedan_textview);//状态
            viewHolder.context_textview = (TextView) convertView.findViewById(R.id.order_context_textview);//内容
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.order_next_textview = (TextView) convertView.findViewById(R.id.order_next_textview);//状态按钮1
            viewHolder.order_sendagain_textview = (TextView) convertView.findViewById(R.id.order_sendagain_textview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.id_textview.setText("订单号：" + orderList.get(position).getId());
        viewHolder.price.setText("￥" + orderList.get(position).getEstimate_price() + "");
        int status = Integer.parseInt(orderList.get(position).getStatus());

        String context = null;
        if (status == 1 || status == 2 || status == 9) {
            String startCity = orderList.get(position).getStarCity();
            String endCity = orderList.get(position).getEndCity();
            context = (startCity.length() > 6 ? startCity.substring(0, 5)+"…" : startCity)
                    + " - " + (endCity.length() > 6 ? endCity.substring(0, 5) +"…": endCity)
                    + "\n货物：" + orderList.get(position).getGoodstype();
//                    + "\n估价：" + orderList.get(position).getEstimate_price();
        } else {
            String startCity = orderList.get(position).getStarCity();
            String endCity = orderList.get(position).getEndCity();

            context = (startCity.length() > 6 ? startCity.substring(0, 5)+"…" : startCity)
                    + " - " + (endCity.length() > 6 ? endCity.substring(0, 5)+"…" : endCity)
                    + "\n货物：" + orderList.get(position).getGoodstype();
//                    + "\n成交价：" + orderList.get(position).getEstimate_price();
        }
        viewHolder.context_textview.setText(context);
        int is_comment = Integer.parseInt(orderList.get(position).getIs_commen());
        switch (status) {
            case ConstantConfig.OrderStatusOne:
                viewHolder.jiedan_textview.setText("正在推送");
                viewHolder.order_next_textview.setText("取消");
                viewHolder.order_next_textview.setTextColor(mContext.getResources().getColor(R.color.text_v2));
                viewHolder.order_next_textview.setOnClickListener(new Button1ClickListener(orderList.get(position)));
                break;
            case ConstantConfig.OrderStatusTwo:
                viewHolder.jiedan_textview.setText("等待确认");
                viewHolder.order_next_textview.setText("确认");
                viewHolder.order_next_textview.setTextColor(mContext.getResources().getColor(R.color.orange_v2));
                viewHolder.order_next_textview.setOnClickListener(new Button1ClickListener(orderList.get(position)));
                break;
            case ConstantConfig.OrderStatusThree:
                viewHolder.jiedan_textview.setText("等待付款");
                viewHolder.order_next_textview.setText("付款");
                viewHolder.order_next_textview.setTextColor(mContext.getResources().getColor(R.color.orange_v2));
                viewHolder.order_next_textview.setOnClickListener(new Button1ClickListener(orderList.get(position)));

                break;
            case ConstantConfig.OrderStatusFive:
                viewHolder.jiedan_textview.setText("订单完成");
                if (is_comment == 0 || is_comment == 2) {//货主未评价
                    viewHolder.order_next_textview.setText("评价");
                    viewHolder.order_next_textview.setOnClickListener(new Button1ClickListener(orderList.get(position)));
                } else {//货主已评价
                    viewHolder.order_next_textview.setVisibility(View.GONE);
                }
                break;
            case ConstantConfig.OrderStatusNine:
                viewHolder.jiedan_textview.setText("订单取消");
                viewHolder.order_next_textview.setVisibility(View.GONE);
                break;
            default:
                break;
        }

        viewHolder.order_sendagain_textview.setOnClickListener(new OnClickListener() {

            /**
             * @param v
             */
            @Override
            public void onClick(View v) {
                //订单详情
                OrderListBean ordetails = orderList.get(position);

                Intent in = new Intent(mContext, SendOrderActivity.class);
                in.putExtra("orderDetails", ordetails);
                mContext.startActivity(in);

            }
        });

        return convertView;
    }

    class Button1ClickListener implements OnClickListener {
        int buttonstatus;
        private String orderId;
        private String Price;
        private OrderListBean bean;

        /**
         * @param bean
         */
        public Button1ClickListener(OrderListBean bean) {
            this.bean = bean;
            this.buttonstatus = Integer.parseInt(bean.getStatus());
            this.orderId = bean.getId();
            this.Price = bean.getPrice();
        }


        /**
         * @param v
         */
        @Override
        public void onClick(View v) {
            switch (buttonstatus) {
                case 1://取消订单
                    getOwnerCancleNum(orderId);
                    break;
//                case 2://
//                    getOwnerCancleNum(orderId);
//                    break;
                case 5:
//                    new CommentDialog(mContext, R.style.dialog).showDialog(orderId, Price);

                    DialogUtils.showComentDialog(mContext, bean.getId(), bean.getPrice());
                    break;
                default:
                    Intent intent = new Intent(mContext, OrderDetailsActivity.class);
                    intent.putExtra("orderid", bean.getId());
                    intent.putExtra("comment", bean.getIs_commen());
                    mContext.startActivity(intent);
                    break;
            }
        }
    }


    class ViewHolder {
        TextView id_textview, jiedan_textview, context_textview;
        /**
         *
         */
        TextView order_next_textview;
        TextView order_sendagain_textview;
        TextView price;
    }


    public void updataListView(List<OrderListBean> list, int type) {
        if (type == 1) {
            orderList = list;
        } else if (type == 2) {
            orderList.addAll(list);
        }
        notifyDataSetChanged();
    }

    String content = "";
    boolean isCancle = false;

    /**
     * @param orderId
     */
    //取消订单
    public void getOwnerCancleNum(final String orderId) {
        if (orderId != null) {

//            new Order_CancleDialog(mContext, R.style.dialog).showDialog(orderId);
//            DialogUtils.showDialogBack(mContext,false,"取消订单","");
            final HttpController controller = new HttpController(mContext);
            controller.setOnGetOwnerCancleListener(new HttpController.onGetOwnerCancleListener() {
                /**
                 * @param num
                 */
                @Override
                public void CancleNum(String num) {
                    if (num.toString().equals("0")) {
                        content = "您今天取消订单次数已用完\n暂不能取消订单.";
                    } else {
                        content = "订单还在进行,您真的要确定取消吗?\n24小时内,您还有" + num + "次取消的机会.";
                        isCancle = true;
                    }
                    DialogUtils.showDialogBack(mContext, false, "取消订单", content, "返回", "确认", null, new DialogUtils.setOnDialogRightButtonClick() {
                        @Override
                        public void setOnClickRight(View view) {
                            if (isCancle) {
                                controller.cancleOrder(orderId);
                            }
                        }
                    });


                }

                @Override
                public void CancleSuccess() {//取消成功
                    if (ConstantConfig.isOrderList) {
                        if (Main_OrderActivity.underwayActivity != null) {
                            Main_OrderActivity.underwayActivity.onRefresh();
                        }
                        if (Main_OrderActivity.accomplishActivity != null) {
                            Main_OrderActivity.accomplishActivity.onRefresh();
                        }
                    }
                }
            });
            controller.getOwnerCancle();


        }
    }

    public List<OrderListBean> getOrderList() {
        if (orderList == null) {
            return null;
        }
        return orderList;
    }
//	//再來一发
//	
//	public void sendAgain(final Context context,final Order_SendSureBean surebean) {
//			if(surebean != null){
//				final HttpController controller = new HttpController(context);
//				controller.setOnWonerGoldListener(new onOwnerGoldListener() {
//					@Override
//					public void onGold(OwnerGoldBean ownerGoldBean) {
//						if(ownerGoldBean != null);
//						new HttpController(context).setOnSendOrderJsonListener(new onSendOrderJsonListener() {
//							@Override
//							public void sendOrder() {
//								ConstantConfig.sendOrderSucc = true;
//								((Activity)context).finish();
//							}
//						});
//						SendOrder_SureOrder_Dialog sureDilaog = new SendOrder_SureOrder_Dialog(context,R.style.dialog);
//						sureDilaog.showDialog(surebean,ownerGoldBean);
//					}
//				});
//				controller.getOwnerGold();
//			}
//		}
//	
}
