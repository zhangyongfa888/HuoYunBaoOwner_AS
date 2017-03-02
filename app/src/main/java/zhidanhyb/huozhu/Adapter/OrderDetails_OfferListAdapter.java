package zhidanhyb.huozhu.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import zhidanhyb.huozhu.Activity.Order.DriverInfoActivity;
import zhidanhyb.huozhu.Bean.DriverInfoBean;
import zhidanhyb.huozhu.Bean.OrderDetials_Driverinfo_Bean;
import zhidanhyb.huozhu.Bean.OrderDetials_Orderinfo_Bean;
import zhidanhyb.huozhu.Bean.RechargeAliPayBean;
import zhidanhyb.huozhu.Bean.RechargeWxBean;
import zhidanhyb.huozhu.Config.ConstantConfig;
import zhidanhyb.huozhu.HttpRequest.HttpConfigSite;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.onGetDriverInfo;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.BaiduMap_Utils;
import zhidanhyb.huozhu.Utils.CallUtils;
import zhidanhyb.huozhu.Utils.T;
import zhidanhyb.huozhu.View.DialogUtils;
import zhidanhyb.huozhu.View.Withdraw_DepositPassPop;
import zhidanhyb.huozhu.View.Withdraw_PasswordSettingPop;
import zhidanhyb.huozhu.pay.AliPay;
import zhidanhyb.huozhu.pay.WxPay;

/**
 * 订单详情页-司机竞价列表的adapter
 *
 * @author lxj
 */
public class OrderDetails_OfferListAdapter extends BaseAdapter {

    private Context mContext;
    private ViewHolder viewHolder;
    private List<OrderDetials_Driverinfo_Bean> driverinofList;
    private OrderDetials_Orderinfo_Bean orderBean = null;
    private String Comment;//是否评价是否评价 0 双方都未评 1 货主已评 2 司机已评 3双方互评

    public OrderDetails_OfferListAdapter(Context context, List<OrderDetials_Driverinfo_Bean> list, OrderDetials_Orderinfo_Bean orderbean) {
        this.mContext = context;
        this.driverinofList = list;
        this.orderBean = orderbean;

    }

    @Override
    public int getCount() {
        if (driverinofList == null) {
            return 0;
        }
        return driverinofList.size() > 0 ? driverinofList.size() : 0;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.orderdetails_offerlistlayout, null);
            viewHolder = new ViewHolder();
            viewHolder.offerprice_textview = (TextView) convertView.findViewById(R.id.offerlist_offerprice_textview);//报价
            viewHolder.driver_imageview = (ImageView) convertView.findViewById(R.id.offerlist_driver_imageview);//货主选择司机后，显示这个头像，并隐藏报价
            viewHolder.line_textview = (TextView) convertView.findViewById(R.id.offerlist_line_textview);//货主选择司机后，隐藏这个线
            viewHolder.driver_textview = (TextView) convertView.findViewById(R.id.offerlist_driver_textview);//司机姓名
            viewHolder.distance_textview = (TextView) convertView.findViewById(R.id.offerlist_distance_textview);//距离
            viewHolder.relation_button = (TextView) convertView.findViewById(R.id.offerlist_relation_button);//联系
            viewHolder.choose_button = (TextView) convertView.findViewById(R.id.offerlist_choose_button);//选择
            viewHolder.star_ratingbar = (RatingBar) convertView.findViewById(R.id.offerlist_star_ratingbar);//星级
            viewHolder.offerlist_line =   convertView.findViewById(R.id.offerlist_line);//分割线
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final int status = Integer.parseInt(orderBean.getStatus());
        switch (status) {
            case ConstantConfig.OrderStatusTwo:
                viewHolder.offerprice_textview.setVisibility(View.VISIBLE);
                viewHolder.line_textview.setVisibility(View.VISIBLE);
                viewHolder.driver_imageview.setVisibility(View.GONE);
                viewHolder.offerprice_textview.setText("￥" + driverinofList.get(position).getPrice());
                viewHolder.choose_button.setText("选择");
                break;
            case ConstantConfig.OrderStatusThree:
                viewHolder.offerprice_textview.setVisibility(View.GONE);
                viewHolder.line_textview.setVisibility(View.GONE);
                viewHolder.driver_imageview.setVisibility(View.VISIBLE);
                viewHolder.choose_button.setText("支付");
                break;
            case ConstantConfig.OrderStatusFive:
                viewHolder.offerprice_textview.setVisibility(View.GONE);
                viewHolder.line_textview.setVisibility(View.GONE);
                viewHolder.driver_imageview.setVisibility(View.VISIBLE);
                Comment = orderBean.getIs_commen();
                if (Comment.toString().equals("0") || Comment.toString().equals("2")) {//货主未评价
                    viewHolder.choose_button.setText("评价");
                } else {//货主已评价
                    viewHolder.offerlist_line.setVisibility(View.GONE);
                    viewHolder.choose_button.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
        viewHolder.driver_textview.setText(driverinofList.get(position).getName());
        viewHolder.star_ratingbar.setRating(Float.parseFloat(driverinofList.get(position).getScore()));
        ConstantConfig.DriverLat = Double.parseDouble(driverinofList.get(position).getLat());
        ConstantConfig.DriverLng = Double.parseDouble(driverinofList.get(position).getLng());
        if (ConstantConfig.DriverLat != 0 && ConstantConfig.DriverLng != 0) {//通过百度规划路线获取司机到货物起点的距离
            String startLatLng[] = orderBean.getStartsite().split(",");
            double startLat = Float.parseFloat(startLatLng[1]);
            double startLng = Float.parseFloat(startLatLng[0]);
            BaiduMap_Utils baiduMap_Utils = new BaiduMap_Utils(mContext);
            baiduMap_Utils.GetDriverToGoodsStart(ConstantConfig.DriverLat, ConstantConfig.DriverLng, startLat, startLng, "1", viewHolder.distance_textview);
        } else {
            viewHolder.distance_textview.setText("附近");
        }
        viewHolder.relation_button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                CallUtils.startSystemDialingActivity(mContext, driverinofList.get(position).getMobile());
            }
        });

        viewHolder.relation_button.setText("联系");
        viewHolder.choose_button.setOnClickListener(new OnClickListener() {
            /**
             * @param v
             */
            @Override
            public void onClick(View v) {
                if (status == ConstantConfig.OrderStatusTwo) {//选择司机
                    chooseDriver(driverinofList.get(position).getId());
                } else if (status == ConstantConfig.OrderStatusThree) {//支付运费
                    showPayDialog(orderBean.getOid(), orderBean.getPrice());
                } else if (status == ConstantConfig.OrderStatusFive) {//评价司机
                   DialogUtils.showComentDialog(mContext,orderBean.getOid(), orderBean.getPrice());
                }
            }
        });
        return convertView;
    }

    /**
     *
     */
    Dialog dialog;
    /**
     *
     */

    /**
     * @param orderID
     * @param Price
     */

    String payType = "1";

    /**
     * @param id
     * @param price
     */
    //支付运费
    protected void showPayDialog(final String id, final String price) {
//        new PayMentfreightDialog(mContext, R.style.dialog).showDialog(id, price);

        View view = View.inflate(mContext, R.layout.paymentfreightlayout, null);

        TextView orderid_textview = (TextView) view.findViewById(R.id.payorder_orderid_textview);//订单号
        RadioGroup radiogroup = (RadioGroup) view.findViewById(R.id.payorder_radiogroup);//
        TextView money_textview = (TextView) view.findViewById(R.id.payorder_money_textview);//总运费
        orderid_textview.setText(id + "号订单付款");
        if (price.equals("")) {
            money_textview.setText("0.0元");
        } else {
            money_textview.setText(price + "元");
        }

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.payorder_balance_radiobutton://余额支付
                        payType = "1";

                        break;
                    case R.id.payorder_alipay_radiogroup://支付宝支付
                        payType = "2";

                        break;
                    case R.id.payorder_weixinpay_radiogroup://微信支付
                        payType = "3";

                        break;
                    default:
                        break;
                }
            }
        });
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = DialogUtils.showDialogCustomView(mContext, true, "付款详情", view, "返回", "确定", null, new DialogUtils.setOnDialogRightButtonClick() {
            /**
             * @param view
             */
            @Override
            public void setOnClickRight(View view) {
                PayFreight1(id, price);
            }
        });


    }

    /**
     * @param id
     * @param price
     */
    private void PayFreight1(final String id, final String price) {
        if (payType.toString().equals("1")) {//余额支付
            HttpController controller = new HttpController(mContext);
            controller.setOnGetOwnerPayPowListener(new HttpController.onGetOwnerPayPowListener() {
                /**
                 * @param string
                 */
                @Override
                public void onPayPow(String string) {
                    if (string.toString().equals("0")) {//未设置密码
                        T.showLong(mContext, "您尚未设置交易密码,请先设置密码");
                        Withdraw_PasswordSettingPop passwordSettingPop = new Withdraw_PasswordSettingPop(mContext);
                        passwordSettingPop.setOnSubmitPasswrodListener(new Withdraw_PasswordSettingPop.onSubmitPassowrdListener() {
                            @Override
                            public void onsuccess(String orderid, String orderprice) {//如果设置成功后在弹出支付密码页面
                                Withdraw_DepositPassPop pop = new Withdraw_DepositPassPop(mContext);
                                pop.showPop(3, orderprice, orderid);
//								ClooseDialog();
                                if (dialog != null && dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                            }
                        });
                        passwordSettingPop.setOrderInfor(price, id);
                        passwordSettingPop.showPop(2);
//                        ClooseDialog();
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    } else if (string.toString().equals("1")) {//已设置密码
                        Withdraw_DepositPassPop pop = new Withdraw_DepositPassPop(mContext);
                        pop.showPop(3, price, id);
//                        ClooseDialog();
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                }
            });
            controller.getOwnerPayPow();

        } else if (payType.toString().equals("2")) {//支付宝支付
            if (price == null || id == null) {
                T.showLong(mContext, "订单信息错误,请重新操作");
                return;
            }
            RechargeAliPayBean alipaybean = new RechargeAliPayBean();
            alipaybean.setAccount_id(id);
            alipaybean.setMoney(price);
            alipaybean.setNotify_url(HttpConfigSite.PostUrl + HttpConfigSite.Post_AliPayUpdata);
            new AliPay(mContext).pay(alipaybean, "2");
        } else if (payType.toString().equals("3")) {//微信支付
            HttpController controller = new HttpController(mContext);
            controller.setRechargeMoneyListener(new HttpController.rechargeMoneyListener() {
                @Override
                public void onRechargeMoney(String Data) {
                    if (payType.toString().equals("3")) {
                        Gson gson = new Gson();
                        RechargeWxBean wxbean = gson.fromJson(Data, RechargeWxBean.class);
                        new WxPay(mContext).pay(wxbean);
                    }
                }
            });
            controller.OrderPayFreight(id, payType, "");//这里的空“”是微信支付不需要本地账户余额的支付密码
        }
    }

    /**
     * 选择司机并跳转到司机信息详情页
     *
     * @param id - 司机id
     */
    protected void chooseDriver(String id) {
        HttpController controller = new HttpController(mContext);
        controller.setOnGetDriverInfo(new onGetDriverInfo() {
            @Override
            public void DriverInfo(DriverInfoBean driverInfoBean) {
                Intent intent = new Intent(mContext, DriverInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("driverinfo", driverInfoBean);
                intent.putExtras(bundle);
                intent.putExtra("orderid", orderBean.getOid());
                mContext.startActivity(intent);
            }
        });
        controller.getDriverInfo(id);
    }

    class ViewHolder {
        TextView offerprice_textview, driver_textview, distance_textview, line_textview;
        /**
         *
         */
        TextView relation_button, choose_button;
        RatingBar star_ratingbar;
        ImageView driver_imageview;
        /**
         *
         */
        View offerlist_line;
    }
}
