package zhidanhyb.huozhu.Activity.Order;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ta.utdid2.android.utils.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.Bean.Order_SendSureBean;
import zhidanhyb.huozhu.Config.ConstantConfig;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.T;
import zhidanhyb.huozhu.View.SendOrder_ChooseTimePop;
import zhidanhyb.huozhu.View.SendOrder_ChooseTimePop.OnChooseTimeListener;

/**
 * 修改订单页面
 *
 * @author lxj
 */
public class ModificationOrderActivity extends BaseActivity {

    private TextView time_textview;
    private TextView start_textview;
    private TextView end_textview;
    private EditText goods_evaluate_edittext;
    private EditText goods_type_edittext;
    private EditText remark_edittext;
    //	private CheckBox home_push_checkbox;
    public static final int startLocation = 100;
    public static final int endLocation = 200;
    private Button sure_button;
    public static int poiSearch = 1;//搜索地址的时候判断是起始地点还是目的地
    public String startLat = null;//起点纬度
    public String startLng = null;//起点经度
    public String startCity = null;//起点城市
    public String endLat = null;//终点纬度
    public String endLng = null;//终点经度
    public String endCity = null;//终点城市
    //	public String isPush = "0";//时候首页推送 默认是0  1是推送 0不推送
    public String chooseDate = null;//选择的时间
    private int position;
    private Order_SendSureBean orderbean = null;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.modificationlayout);
        initView();
        addListener();
    }

    /**
     *
     */
    private void initView() {
        setTitleText(R.string.modificationordertitle);
        setHideRightButton();
        setLeftButton();
        time_textview = (TextView) findViewById(R.id.sendorder_time_textview);//什么时候出发
        time_textview.setOnClickListener(this);
        start_textview = (TextView) findViewById(R.id.sendorder_start_textview);//从哪里出发
        start_textview.setOnClickListener(this);
        end_textview = (TextView) findViewById(R.id.sendorder_end_textview);//送到哪里去
        end_textview.setOnClickListener(this);
        goods_evaluate_edittext = (EditText) findViewById(R.id.sendorder_goods_evaluate_edittext);//估价
        goods_type_edittext = (EditText) findViewById(R.id.sendorder_goods_type_edittext);//货物类型
        remark_edittext = (EditText) findViewById(R.id.sendorder_remark_edittext);//备注
//		home_push_checkbox = (CheckBox)findViewById(R.id.sendorder_home_push_checkbox);//选择首页推送
        sure_button = (Button) findViewById(R.id.modificationorder_sure_button);//确认修改
        sure_button.setOnClickListener(this);
        orderbean = (Order_SendSureBean) getIntent().getExtras().getSerializable("order");
        position = getIntent().getExtras().getInt("position");

        setView(orderbean);
    }

    /**
     * @param orderbean2
     */
    private void setView(Order_SendSureBean orderbean2) {
        if (orderbean2 == null) {
            T.showShort(this, "数据错误,请重试!");
            finish();
            return;
        }
        time_textview.setText(orderbean2.getChoosetime());
        start_textview.setText(orderbean2.getStarAdr());
        end_textview.setText(orderbean2.getEndAdr());
        goods_evaluate_edittext.setText(orderbean2.getEvaluate());
        goods_type_edittext.setText(orderbean2.getGoodsType());
        remark_edittext.setText(orderbean2.getOrderDetail());
//		if(orderbean2.getIsPush().equals("0")){//不推送
//			isPush = orderbean2.getIsPush();
//			home_push_checkbox.setChecked(false);
//		}else if(orderbean2.getIsPush().equals("1")){//推送
//			isPush = orderbean2.getIsPush();
//			home_push_checkbox.setChecked(true);
//		}
        startLat = orderbean2.getStarLat();
        startLng = orderbean2.getStarLng();
        startCity = orderbean2.getStarCity();
        endCity = orderbean2.getEndCity();
        endLat = orderbean2.getEndLat();
        endLng = orderbean2.getEndLng();
        chooseDate = orderbean2.getTime();

    }

    /**
     *
     */
    private void addListener() {
//		home_push_checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				if(isChecked){//选择首页推送
//					isPush = "1";
//				}else{//未选择首页推送
//					isPush = "0";
//				}
//			}
//		});

        remark_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 30) {
                    T.showShort(ModificationOrderActivity.this, "长度不能大于30个字符");
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
                    T.showShort(ModificationOrderActivity.this, "长度不能大于10个字符");
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
                    T.showShort(ModificationOrderActivity.this, "长度不能大于7个字符");
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


    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.title_left_rl:
                finish();
                break;
            case R.id.sendorder_time_textview://什么时候出发
                new SendOrder_ChooseTimePop(this, v).setOnChooseTimeListener(new OnChooseTimeListener() {
                    @Override
                    public void getChooseTime(String time, String date, long m) {
                        time_textview.setText(time);
                        chooseDate = date;
                    }
                });
                break;
            case R.id.sendorder_start_textview://从哪里出发
                poiSearch = 1;
                intent.setClass(this, SendOrderLocationActivity.class);
                startActivityForResult(intent, startLocation);
                break;
            case R.id.sendorder_end_textview://送到哪里去
                poiSearch = 2;
                intent.setClass(this, SendOrderLocationActivity.class);
                startActivityForResult(intent, endLocation);
                break;
            case R.id.modificationorder_sure_button://确认修改
                if (isEmpty()) {
                    updateOrderList();
                }
                break;
            default:
                break;
        }
    }

    private void updateOrderList() {
        if (ConstantConfig.sendSureList != null) {
            ConstantConfig.sendSureList.remove(position);
            ConstantConfig.sendSureList.add(position, orderbean);
            T.showShort(this, "修改订单成功!");
            finish();
        }
    }

    @Override
    public void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
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
        }
    }

    /**
     * @return
     */
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
        if (startCity == null) {
            T.showShort(this, "起点位置信息有错误,请重试!");
            is = false;
            return is;
        }
        if (endLat == null) {
            T.showShort(this, "终点位置信息有错误,请重试!");
            is = false;
            return is;
        }
        if (endLng == null) {
            T.showShort(this, "终点位置信息有错误,请重试!");
            is = false;
            return is;
        }
        if (endCity == null) {
            T.showShort(this, "终点位置信息有错误,请重试!");
            is = false;
            return is;
        }
        Log.e("time", chooseDate + "," + System.currentTimeMillis() / 1000);

        if (convert2long(chooseDate, "") < System.currentTimeMillis() / 1000 || convert2long(chooseDate, "") == System.currentTimeMillis() / 1000) {
            T.showShort(this, "发货时间已过,请重新选择发货时间!");
            is = false;
            return is;
        }
        orderbean.setEndAdr(end_textview.getText().toString().trim());
        orderbean.setEndCity(endCity);
        orderbean.setEndLat(endLat);
        orderbean.setEndLng(endLng);
        orderbean.setEvaluate(goods_evaluate_edittext.getText().toString().trim());
        orderbean.setGoodsType(goods_type_edittext.getText().toString().trim());
//		orderbean.setIsPush(isPush);
        orderbean.setStarAdr(start_textview.getText().toString().trim());
        orderbean.setStarLat(startLat);
        orderbean.setStarLng(startLng);
        orderbean.setStarCity(startCity);
        orderbean.setOrderDetail(remark_edittext.getText().toString().trim());
        orderbean.setTime(chooseDate);
        return is;
    }

    /**
     * @param date
     * @param format
     * @return
     */
    public long convert2long(String date, String format) {
        try {
            if (!StringUtils.isEmpty(date)) {
                if (StringUtils.isEmpty(format))
                    format = "yyyy-MM-dd HH:mm";
                SimpleDateFormat sf = new SimpleDateFormat(format);
                return sf.parse(date).getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                return sf.parse(date).getTime();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }


        }
        return 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        time_textview = null;
        start_textview = null;
        end_textview = null;
        goods_evaluate_edittext = null;
        goods_type_edittext = null;
        remark_edittext = null;
//		home_push_checkbox = null;
        if (orderbean != null) {
            orderbean = null;
        }
        sure_button = null;
    }
}
