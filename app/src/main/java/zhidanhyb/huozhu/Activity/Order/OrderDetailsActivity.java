package zhidanhyb.huozhu.Activity.Order;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import zhidanhyb.huozhu.Adapter.OrderDetails_OfferListAdapter;
import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.Bean.OrderDetials_Bean;
import zhidanhyb.huozhu.Bean.OrderDetials_Orderinfo_Bean;
import zhidanhyb.huozhu.Bean.OrderDetials_Releaseinfo_Bean;
import zhidanhyb.huozhu.Bean.ShareContentBean;
import zhidanhyb.huozhu.Config.ConstantConfig;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.getOrderDetails;
import zhidanhyb.huozhu.HttpRequest.HttpController.onGetShareContentListener;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.ShareUtils;
import zhidanhyb.huozhu.Utils.StringUtil;

/**
 * 订单详情页面
 *
 * @author lxj
 */
public class OrderDetailsActivity extends BaseActivity implements getOrderDetails {
    private TextView startsite_textview;
    private TextView endtsite_textview;
    private TextView goods_textview;
    private TextView time_textview;
    private TextView remark_textview;
    private TextView orderid_textview;
    private TextView offerbargain_textview;
    private TextView monet_textview;
    private ListView offer_listview;
    private OrderDetials_Orderinfo_Bean orderinfoBean;
    private OrderDetials_Releaseinfo_Bean ReleaseinofBean;
    private OrderDetails_OfferListAdapter offerListAdapter;
    private MyBroadcastReceiver broadcastReceiver;
    public static OrderDetailsActivity detailsActivity = null;
    private String oid;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.orderdetailslayout);
        initView();
        getData();
    }

    public void getData() {
        HttpController controller = new HttpController(this);
        oid = getIntent().getExtras().getString("orderid");
        controller.setGetOrderDetails(this);
        controller.getOrderDetails(oid);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        super.onClick(v);
        switch (v.getId()) {
            case R.id.title_right_rl://分享
                getShareContent(orderinfoBean.getPrice());
                break;
            default:
                break;
        }
    }

    private void initView() {
        detailsActivity = this;
        setLeftButton();
        startsite_textview = (TextView) findViewById(R.id.orderdetails_startsite_textview);//起点
        endtsite_textview = (TextView) findViewById(R.id.orderdetails_endtsite_textview);//终点
        goods_textview = (TextView) findViewById(R.id.orderdetails_goods_textview);//货物
        time_textview = (TextView) findViewById(R.id.orderdetails_time_textview);//发车时间
        remark_textview = (TextView) findViewById(R.id.orderdetails_remark_textview);//备注
        orderid_textview = (TextView) findViewById(R.id.orderdetails_orderid_textview);//订单id
        offerbargain_textview = (TextView) findViewById(R.id.orderdetails_offerbargain_textview);//估价/成交价
        monet_textview = (TextView) findViewById(R.id.orderdetails_monet_textview);//价格
        offer_listview = (ListView) findViewById(R.id.orderdetails_offer_listview);//抢单列表
        //注册广播
        broadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConstantConfig.updataOrderDetails);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    /**
     * @param orderDetials_Bean
     */
    @Override
    public void OrderDetails(OrderDetials_Bean orderDetials_Bean) {
        orderinfoBean = orderDetials_Bean.getOrderinfolist();
        ReleaseinofBean = orderDetials_Bean.getReleaseinfo();
        startsite_textview.setText("起点：" + orderinfoBean.getOrigin_id());
        endtsite_textview.setText("终点：" + orderinfoBean.getDest_id());
        goods_textview.setText(orderinfoBean.getGoodstype());
        time_textview.setText(orderinfoBean.getDeparture_time());
        remark_textview.setText(orderinfoBean.getRemarks());
        orderid_textview.setText(orderinfoBean.getOid());
        int status = Integer.parseInt(orderinfoBean.getStatus());
        if (status == 3 || status == 5) {
            offerbargain_textview.setText("成交");
            if (StringUtil.isEmpty(orderinfoBean.getPrice())) {
                monet_textview.setText("￥0");
            } else {
                monet_textview.setText("￥" + orderinfoBean.getPrice());
            }
        } else if (status == 1 || status == 2 || status == 9) {
            offerbargain_textview.setText("估价");
            if (StringUtil.isEmpty(orderinfoBean.getEstimate_price())) {
                monet_textview.setText("￥0");
            } else {
                monet_textview.setText("￥" + orderinfoBean.getEstimate_price());
            }
        }
        switch (status) {//1待接单 2竞价中 3等待付款5 完成 9取消
            case ConstantConfig.OrderStatusOne:
                setTitleText(R.string.orderone);
                break;
            case ConstantConfig.OrderStatusTwo:
                setTitleText(R.string.ordertwo);
                break;
            case ConstantConfig.OrderStatusThree:
                setTitleText(R.string.orderthree);
                break;
            case ConstantConfig.OrderStatusFive:
//			setRightButton(R.drawable.share);去除分享按鈕
                setTitleText(R.string.orderfive);
                break;
            case ConstantConfig.OrderStatusNine:
                setTitleText(R.string.ordersix);
                break;
            default:
                break;
        }
        offerListAdapter = new OrderDetails_OfferListAdapter(this, orderDetials_Bean.getDriverinoflist(), orderinfoBean);
        offer_listview.setAdapter(offerListAdapter);
    }

    //订单已完成后获取分享内容
    private void getShareContent(String price) {
        HttpController controller = new HttpController(this);
        controller.setOnGetShareContentListener(new onGetShareContentListener() {
            @Override
            public void ShareContent(ShareContentBean shareContentBean) {
                @SuppressWarnings("static-access")
                ShareUtils shareUtils = new ShareUtils().getInstance();
                shareUtils.init(OrderDetailsActivity.this);
                shareUtils.showShare(shareContentBean.getUrl(), shareContentBean.getStr(), oid);
            }
        });
        controller.getShareContent(price, oid);
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        public static final String TAG = "MyBroadcastReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.toString().equals(ConstantConfig.updataOrderDetails)) {//订单详情页更新页面数据及订单状态
                String orderid = intent.getExtras().getString("orderid");
                if (oid != null) {
                    if (oid.toString().equals(orderid)) {
                        getData();
                    }
                }
                return;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConstantConfig.isOrderDetails = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startsite_textview = null;
        endtsite_textview = null;
        goods_textview = null;
        time_textview = null;
        remark_textview = null;
        orderid_textview = null;
        offerbargain_textview = null;
        monet_textview = null;
        offer_listview = null;
        if (orderinfoBean != null) {
            orderinfoBean = null;
        }
        if (ReleaseinofBean != null) {
            ReleaseinofBean = null;
        }
        if (offerListAdapter != null) {
            offerListAdapter = null;
        }
        ConstantConfig.isOrderDetails = false;
        if (detailsActivity != null) {
            detailsActivity = null;
        }
        unregisterReceiver(broadcastReceiver);
    }
}
