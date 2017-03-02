package zhidanhyb.huozhu.Activity.Order;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.Bean.OrderListBean;
import zhidanhyb.huozhu.Bean.Order_SendSureBean;
import zhidanhyb.huozhu.Config.ConstantConfig;
import zhidanhyb.huozhu.Connector.MyLocationListener;
import zhidanhyb.huozhu.Connector.MyLocationListener.OnLocationListener;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.BaiduMap_Utils;
import zhidanhyb.huozhu.Utils.KeyboradUtils;
import zhidanhyb.huozhu.Utils.L;
import zhidanhyb.huozhu.Utils.NumberFormatUtils;
import zhidanhyb.huozhu.Utils.ScreenUtils;
import zhidanhyb.huozhu.Utils.StringUtil;
import zhidanhyb.huozhu.Utils.T;
import zhidanhyb.huozhu.Utils.ViewUtils;
import zhidanhyb.huozhu.View.DialogUtils;
import zhidanhyb.huozhu.View.SendOrder_ChooseTimePop;
import zhidanhyb.huozhu.View.SendOrder_ChooseTimePop.OnChooseTimeListener;

/**
 * 编辑订单并发送页面
 *
 * @author lxj
 */
public class SendOrderActivity extends BaseActivity implements OnLocationListener {
    private TextView time_textview;
    private TextView start_textview;
    private TextView end_textview;
    /**
     *
     */
    private EditText remark_edittext;
    /**
     *
     */
    private LinearLayout add_textview;
    private CheckBox home_push_checkbox;
    /**
     *
     */
    private TextView sure_button;
    private EditText goods_type_edittext;
    private EditText goods_evaluate_edittext;
    public static final int startLocation = 100;
    public static final int endLocation = 200;
    public static int poiSearch = 1;// 搜索地址的时候判断是起始地点还是目的地
    private Order_SendSureBean surebean = null;// 发送订单的Bean
    //	static int number = 0;// sendSureList 集合的个数
    public String startLat = null;// 起点纬度
    public String startLng = null;// 起点经度
    public String startCity = null;// 起点城市
    public String endLat = null;// 终点纬度
    public String endLng = null;// 终点经度
    public String endCity = null;// 终点城市
    public String isPush = "0";// 时候首页推送 默认是0 1是推送 0不推送
    public String chooseDate = null;// 选择的时间
    private Long millisecond = 0L;// 选择时间的毫秒值
    private ScrollView scrollview;
    public static SendOrderActivity OrderActivity = null;
    private OrderListBean orderDetails;
    private boolean isAddAgain = false;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.sendorderlayout);
        initView();
        addListener();
        getUserLocation();
    }

    @SuppressWarnings("static-access")
    private void getUserLocation() {
        MyLocationListener onListener = new MyLocationListener();
        onListener.setOnLocationListener(this);
        BaiduMap_Utils baiduMap = new BaiduMap_Utils(this, onListener);
        baiduMap.LocationStart();
    }

    /**
     *
     */
    private void initView() {
        OrderActivity = this;
        setTitleText(R.string.editorder);
        setLeftButton();
        scrollview = (ScrollView) findViewById(R.id.scrollview);
        time_textview = (TextView) findViewById(R.id.sendorder_time_textview);// 什么时候出发
        time_textview.setOnClickListener(this);
        start_textview = (TextView) findViewById(R.id.sendorder_start_textview);// 从哪里出发
        start_textview.setOnClickListener(this);

        if (getIntent().hasExtra("orderDetails")) {//重发
            orderDetails = (OrderListBean) getIntent().getSerializableExtra("orderDetails");
            isAddAgain = true;
            setHideRightButton();
        } else {
            setRightButton(R.drawable.v2_orderlist_menu);

        }

        String name = getIntent().getStringExtra("name");
        start_textview.setText(name);
        startCity = getIntent().getStringExtra("city");
        startLat = getIntent().getStringExtra("lat");
        startLng = getIntent().getStringExtra("lng");
        end_textview = (TextView) findViewById(R.id.sendorder_end_textview);// 送到哪里去
        end_textview.setOnClickListener(this);
        goods_evaluate_edittext = (EditText) findViewById(R.id.sendorder_goods_evaluate_edittext);// 估价
        goods_type_edittext = (EditText) findViewById(R.id.sendorder_goods_type_edittext);// 货物类型
        remark_edittext = (EditText) findViewById(R.id.sendorder_remark_edittext);// 备注
        add_textview = (LinearLayout) findViewById(R.id.sendorder_add_textview);// 添加订单
        add_textview.setOnClickListener(this);
//		home_push_checkbox = (CheckBox) findViewById(R.id.sendorder_home_push_checkbox);// 选择首页推送
        sure_button = (TextView) findViewById(R.id.sendorder_sure_button);// 确认发送
        sure_button.setOnClickListener(this);
        scrollview.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        KeyboradUtils.HideKeyboard(v);
                        break;
                    default:
                        break;
                }

                return false;
            }
        });

        if (isAddAgain) {
            setTitleText(R.string.editorder_again);
            start_textview.setText(orderDetails.getStarCity());
            start_textview.setEnabled(false);
            end_textview.setText(orderDetails.getEndCity() + "");
            end_textview.setEnabled(false);

            goods_evaluate_edittext.setText(orderDetails.getEstimate_price() + "");
            goods_evaluate_edittext.setEnabled(false);

            goods_type_edittext.setText(orderDetails.getGoodstype() + "");
            goods_type_edittext.setEnabled(false);

            remark_edittext.setText(orderDetails.getRemarks() + "");
            remark_edittext.setEnabled(false);

            startCity = orderDetails.getStarCity();
            endCity = orderDetails.getEndCity();
            add_textview.setVisibility(View.GONE);
        }
    }

    private void addListener() {
        remark_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 30) {
                    T.showShort(SendOrderActivity.this, "长度不能大于30个字符");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        goods_type_edittext.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 10) {
                    T.showShort(SendOrderActivity.this, "长度不能大于10个字符");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        goods_evaluate_edittext.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 7) {
                    T.showShort(SendOrderActivity.this, "长度不能大于7个字符");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        super.onClick(v);
        switch (v.getId()) {
            case R.id.title_left_rl:// 返回
                finish();
                break;
            case R.id.title_right_rl:// 查看订单数量
                if (ConstantConfig.sendSureList.size() > 0) {
                    intent.setClass(this, SendOrderListActivity.class);
                    startActivity(intent);
                } else {
                    T.showShort(this, "请添加订单!");
                }
                break;
            case R.id.sendorder_time_textview:// 什么时候出发
                new SendOrder_ChooseTimePop(this, v).setOnChooseTimeListener(new OnChooseTimeListener() {
                    @Override
                    public void getChooseTime(String time, String date, long m) {
                        time_textview.setText(time);
                        chooseDate = date;
                        millisecond = m;
                    }
                });
                break;
            case R.id.sendorder_start_textview:// 从哪里出发

//			intent.setClass(this, SendOrder_ChooseLocByMapActivity.class);
//			intent.putExtra("type", 0);
//			startActivityForResult(intent, startLocation);
                poiSearch = 1;
                intent.setClass(this, SendOrderLocationActivity.class);
                startActivityForResult(intent, startLocation);

                break;
            case R.id.sendorder_end_textview:// 送到哪里去
//			intent.setClass(this, SendOrder_ChooseLocByMapActivity.class);
//			intent.putExtra("type", 1);
//			intent.putExtra("start_lat", startLat);
//			intent.putExtra("start_lng", startLng);
//			intent.putExtra("start_name", start_textview.getText().toString());
//			startActivityForResult(intent, endLocation);
                poiSearch = 2;
                intent.setClass(this, SendOrderLocationActivity.class);
                startActivityForResult(intent, endLocation);

                break;
            case R.id.sendorder_add_textview:// 本地添加订单
                if (isEmpty()) {
                    addOrder();
                }
                break;
            case R.id.sendorder_sure_button:// 确认发送订单
                if (isEmpty()) {
                    DialogUtils.showDialogCustomView(context, true, "提示", getView(surebean), "返回", "确认", null, new DialogUtils.setOnDialogRightButtonClick() {
                        /**
                         * @param view
                         */
                        @Override
                        public void setOnClickRight(View view) {
                            if (surebean != null) {
                                T.showShort(context, "正在发送订单...");

                                if (!StringUtil.isEmpty(surebean.getId())) {//再来订单
                                    SendOrderAgain(surebean.getTime(), surebean.getIsPush(),
                                            surebean.getId());
                                } else {//发送订单
                                    Gson gson = new Gson();
                                    List<Order_SendSureBean> list = new ArrayList<Order_SendSureBean>();
                                    list.add(surebean);
                                    SendOrder(surebean.getId(), gson.toJson(list));
                                }

                            }
                        }
                    });
                }

                break;
            default:
                break;
        }
    }

    /**
     * @param surebean
     * @return
     */
    private View getView(Order_SendSureBean surebean) {
        String ordertime = "发车时间 : " + surebean.getTime();
        String orderstart = "起点 : " + surebean.getStarAdr();
        String orderend = "终点 : " + surebean.getEndAdr();
        String goodstype = "货物类型:" + surebean.getGoodsType();
        String orderevaluate = "估价总值 : " + NumberFormatUtils.NumberType(surebean.getEvaluate()) + "元";
        String orderdetails = "货物详情/备注 : " + surebean.getOrderDetail();

        View view = View.inflate(context, R.layout.v2_dialog_custom, null);
        ((TextView) view.findViewById(R.id.start_time)).setText(ordertime);
        ((TextView) view.findViewById(R.id.start_address)).setText(orderstart);
        ((TextView) view.findViewById(R.id.end_address)).setText(orderend);
        ((TextView) view.findViewById(R.id.type)).setText(goodstype);
        ((TextView) view.findViewById(R.id.price)).setText(orderevaluate);
        ((TextView) view.findViewById(R.id.details)).setText(orderdetails);

        ViewUtils.setViewSize(context, view.findViewById(R.id.details), 640 - 2 * ScreenUtils.dip2px(context, 35), 0);
        return view;
    }


    /**
     * @param id
     * @param jsonOrder
     */
    private void SendOrder(String id, String jsonOrder) {
        HttpController controller = new HttpController(context);
        controller.SendOrderJson(jsonOrder);
        controller.setOnSendOrderJsonListener(new HttpController.onSendOrderJsonListener() {
            @Override
            public void sendOrder() {
                ConstantConfig.sendOrderSucc = true;
                finish();
            }
        });
    }

    /**
     * @param time
     * @param push
     * @param id
     */
    private void SendOrderAgain(String time, String push, String id) {
        HttpController controller = new HttpController(context);
        controller.SendAgainOrderJson(time, push, id);
        controller.setOnSendAgainOrderJsonListener(new HttpController.onSendAgainOrderJsonListener() {
            @Override
            public void sendAgainOrder() {
                ConstantConfig.sendOrderSucc = true;
                finish();
            }
        });
    }

    /**
     *
     */
    private void addOrder() {
        if (surebean != null) {
            if (ConstantConfig.sendSureList.size() < 5) {
                ConstantConfig.sendSureList.add(surebean);
                setRightNumberButton(ConstantConfig.sendSureList.size());
            } else {
                T.showShort(this, "最多发五单!");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ConstantConfig.sendSureList != null) {
            if (ConstantConfig.sendSureList.size() > 0) {
                setRightNumberButton(ConstantConfig.sendSureList.size());
            } else if (ConstantConfig.sendSureList.size() == 0) {
                setRightNumberButton(0);
            }
        } else {
            ConstantConfig.sendSureList = new ArrayList<>();
            setRightNumberButton(0);

        }

    }

    @Override
    public void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        if (ConstantConfig.sendSureList != null) {
            if (ConstantConfig.sendSureList.size() > 0) {
                setRightNumberButton(ConstantConfig.sendSureList.size());
            } else if (ConstantConfig.sendSureList.size() == 0) {
                setRightNumberButton(0);
            }
        } else {
            ConstantConfig.sendSureList = new ArrayList<>();
            setRightNumberButton(0);

        }


        if (arg2 == null) {
            return;
        }
        String name = arg2.getExtras().getString("name");
        if (arg0 == startLocation) {
            start_textview.setText(name);
            startCity = arg2.getExtras().getString("city");
            startLat = arg2.getExtras().getString("lat");
            startLng = arg2.getExtras().getString("lng");
        }
        if (arg0 == endLocation) {
            end_textview.setText(name);
            endCity = arg2.getExtras().getString("city");
            endLat = arg2.getExtras().getString("lat");
            endLng = arg2.getExtras().getString("lng");
            L.i("返回来的数据=name=" + name + " endCity=" + endCity + " endLat=" + endLat + " endLng=" + endLng);
        }
    }

    private boolean isEmpty() {
        boolean is = true;
        if (time_textview.getText().toString().isEmpty()) {
            T.showShort(this, "请选择发货物时间!");
            is = false;
            return is;
        }

        if (start_textview.getText().toString().isEmpty()) {
            T.showShort(this, "请选择出发地!");
            is = false;
            return is;
        }

        if (end_textview.getText().toString().isEmpty()) {
            T.showShort(this, "请选择目的地!");
            is = false;
            return is;
        }
        if (goods_type_edittext.getText().toString().isEmpty()) {
            T.showShort(this, "请编辑货物类型!");
            is = false;
            return is;
        }

        if (goods_evaluate_edittext.getText().toString().isEmpty()) {
            T.showShort(this, "请编辑估价!");
            is = false;
            return is;
        }
        if (remark_edittext.getText().toString().isEmpty()) {
            T.showShort(this, "请编辑订单详情!");
            is = false;
            return is;
        }
        // 再来一发没有此项目

        if (startCity == null) {
            T.showShort(this, "起点位置信息有错误,请重试!");
            is = false;
            return is;
        }

        if (!isAddAgain) {
            if (endLat == null) {
                T.showShort(this, "终点位置信息有错误,请重试!");
                is = false;
                return is;
            }

            if (startLat == null) {
                T.showShort(this, "起点位置信息有错误,请重试!");
                is = false;
                return is;
            }
            if (startLng == null) {
                T.showShort(this, "起点位置信息有错误,请重试!");
                is = false;
                return is;
            }
            if (endLng == null) {
                T.showShort(this, "终点位置信息有错误,请重试!");
                is = false;
                return is;
            }
        }

        if (endCity == null) {
            T.showShort(this, "终点位置信息有错误,请重试!");
            is = false;
            return is;
        }
        if (millisecond < System.currentTimeMillis() / 1000 || millisecond == System.currentTimeMillis() / 1000) {
            T.showShort(this, "发货时间已过,请重新选择发货时间!");
            is = false;
            return is;
        }
        if (surebean != null) {
            surebean = null;
        }
        surebean = new Order_SendSureBean();

        if (orderDetails != null) {
            surebean.setId(orderDetails.getId());
        }
        surebean.setChoosetime(time_textview.getText().toString().trim());
        surebean.setEndAdr(end_textview.getText().toString().trim());
        surebean.setEndCity(endCity);

        surebean.setEvaluate(goods_evaluate_edittext.getText().toString().trim());
        surebean.setGoodsType(goods_type_edittext.getText().toString().trim());
        surebean.setIsPush(isPush);
        surebean.setStarAdr(start_textview.getText().toString().trim());

        surebean.setStarLat(startLat);
        surebean.setStarLng(startLng);
        surebean.setEndLat(endLat);
        surebean.setEndLng(endLng);
        surebean.setStarCity(startCity);

        surebean.setOrderDetail(remark_edittext.getText().toString().trim());
        surebean.setTime(chooseDate);
        return is;
    }

//    /**
//     * @param position
//     */
////	// 在订单列表也删除订单刷新这个页面的集合和右上角的数字
//    public void onUpdataNum() {
//        if (ConstantConfig.sendSureList.size() > 0) {
//            int number = ConstantConfig.sendSureList.size();
//            setRightNumberButton(number);
//        } else if (ConstantConfig.sendSureList.size() == 0) {
//            int number = 0;
//            setRightNumberButton(number);
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        time_textview = null;
        start_textview = null;
        end_textview = null;
        remark_edittext = null;
        goods_evaluate_edittext = null;
        add_textview = null;
//		home_push_checkbox = null;
        sure_button = null;
        goods_type_edittext = null;
        if (surebean != null) {
            surebean = null;
        }
        chooseDate = null;
        isPush = null;
        endCity = null;
        endLng = null;
        endLat = null;
        startCity = null;
        startLng = null;
        startLat = null;
        if (OrderActivity != null) {
            OrderActivity = null;
        }
    }

    @Override
    public void onLocation(String location, double lat, double lng) {

    }
}
