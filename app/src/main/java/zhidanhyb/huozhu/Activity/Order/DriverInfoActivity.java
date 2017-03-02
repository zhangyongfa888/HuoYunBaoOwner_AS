package zhidanhyb.huozhu.Activity.Order;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import zhidanhyb.huozhu.Adapter.DriverInfoCommentAdapter;
import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.Base.HYBHuoZhuApplication;
import zhidanhyb.huozhu.Bean.DriverInfoBean;
import zhidanhyb.huozhu.Config.ConstantConfig;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.CallUtils;
import zhidanhyb.huozhu.Utils.UserVerifyUtils;
import zhidanhyb.huozhu.Utils.ViewUtils;
import zhidanhyb.huozhu.View.CircleImageView;
import zhidanhyb.huozhu.View.DialogUtils;

/**
 * 货主选择司机-司机信息页面
 *
 * @author lxj
 */
public class DriverInfoActivity extends BaseActivity {

    private DriverInfoBean driverinfo;
    private ListView driverinfo_listview;
    private TextView relation_button;
    private TextView choose_button;
    /**
     *
     */
    private CircleImageView driver_head_imageview;
    private TextView driver_name_textview;
    private TextView driver_license_textview;
    private TextView driver_phone_textview;
    /**
     *
     */
    private TextView driver_size_textview;
    private TextView driver_ton_textview;
    private TextView driver_bargain_textview;
    private TextView driver_applause_rate_textview;
    private ImageLoader imageLoader;
    /**
     *
     */
//    private RatingBar driver_star_ratingbar;
    private String orderid = null;
    private ImageView driver_level_imageview;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.driverinfolayout);
        initView();
    }

    /**
     *
     */
    private void initView() {
        setLeftButton();
        setHideRightButton();
        driverinfo = (DriverInfoBean) getIntent().getExtras().getSerializable("driverinfo");
        orderid = getIntent().getExtras().getString("orderid");
        driverinfo_listview = (ListView) findViewById(R.id.driverinfo_listview);
        relation_button = (TextView) findViewById(R.id.driverinfo_relation_button);
        relation_button.setOnClickListener(this);
        choose_button = (TextView) findViewById(R.id.driverinfo_choose_button);
        choose_button.setOnClickListener(this);
        View header = LayoutInflater.from(this).inflate(R.layout.driverinfo_header_layout, null);
        driver_head_imageview = (CircleImageView) header.findViewById(R.id.driver_head_imageview);//头像
        driver_name_textview = (TextView) header.findViewById(R.id.driver_name_textview);//司机姓名
        driver_license_textview = (TextView) header.findViewById(R.id.driver_license_textview);//车牌号
        driver_phone_textview = (TextView) header.findViewById(R.id.driver_phone_textview);//司机手机号
        driver_ton_textview = (TextView) header.findViewById(R.id.driver_ton_textview);//司机车辆吨位
        driver_level_imageview = (ImageView) header.findViewById(R.id.driver_level_imageview);//等级
        driver_bargain_textview = (TextView) header.findViewById(R.id.driver_bargain_textview);//司机成交订单数
        driver_size_textview= (TextView) header.findViewById(R.id.driver_size_textview);
//        driver_star_ratingbar = (RatingBar) header.findViewById(R.id.driver_star_ratingbar);//星星
        driver_applause_rate_textview = (TextView) header.findViewById(R.id.driver_applause_rate_textview);//好评率


        ViewUtils.setViewSize(context, header.findViewById(R.id.head),280 , 220);
        ViewUtils.setViewSize(context, header.findViewById(R.id.v2_me_top), 640, 285);
        ViewUtils.setViewSize(context, header.findViewById(R.id.v2_me_top_bg), 640, 175);
        ViewUtils.setViewSize(context, driver_head_imageview, 220, 220);
        driver_head_imageview.setBorderWidth(40);

        driverinfo_listview.addHeaderView(header);
        if (driverinfo != null) {
            String driver = driverinfo.getName();
            setTitleText(driver);
            if (driverinfo.getPic().toString().equals("")) {
                driver_head_imageview.setImageDrawable(getResources().getDrawable(R.drawable.head));
            } else {
                imageLoader = ImageLoader.getInstance();
                imageLoader.displayImage(driverinfo.getPic(), driver_head_imageview, HYBHuoZhuApplication.options);
            }
            driver_name_textview.setText(driverinfo.getName());
            driver_license_textview.setText(driverinfo.getPlate_num());
            driver_size_textview.setText("车辆尺寸："+driverinfo.getVehicle_id());//// TODO: 2017/2/22
            driver_phone_textview.setText("联系电话："+driverinfo.getMobile());
            driver_ton_textview.setText("车辆载重："+driverinfo.getVehicle_id() + "吨");
            driver_bargain_textview.setText("成交订单数："+driverinfo.getSuccess_num());
//            driver_star_ratingbar.setRating(Integer.parseInt(driverinfo.getScore()));
            driver_applause_rate_textview.setText( driverinfo.getRate() + "%");
            UserVerifyUtils.setDriverLevelImage(driver_level_imageview, driverinfo.getLevel());
            if (driverinfo.getCommentlist() == null) {
                TextView textView = new TextView(this);
                textView.setText("暂无评论!");
                textView.setTextSize(17);
                textView.setTextColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);
                textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                driverinfo_listview.addFooterView(textView);
            }
            driverinfo_listview.setAdapter(new DriverInfoCommentAdapter(this, driverinfo.getCommentlist()));
        }


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.driverinfo_relation_button://联系司机
                if (driverinfo != null) {
                    CallUtils.startSystemDialingActivity(this, driverinfo.getMobile());
                }
                break;
            case R.id.driverinfo_choose_button://确认司机
                if (orderid != null) {

                    DialogUtils.showDialogBack(context, true, "确认司机", "您已为此订单选择 " + driverinfo.getName() + " " + driverinfo.getPlate_num(), "返回", "确认", new DialogUtils.setOnDialogLeftButtonClick() {
                        @Override
                        public void setOnClickLeft(View view) {

                        }
                    }, new DialogUtils.setOnDialogRightButtonClick() {
                        /**
                         * @param view
                         */
                        @Override
                        public void setOnClickRight(View view) {

                            HttpController controller = new HttpController(context);
                            String driverId = driverinfo.getDriver_id();
                            controller.setOnChooseDriver(new HttpController.onChooseDriver() {
                                /**
                                 *
                                 */
                                @Override
                                public void onChoose() {//选择成功
                                    Intent intent = new Intent(ConstantConfig.updataOrderDetails);
                                    intent.putExtra("orderid", orderid);
                                    sendBroadcast(intent);
                                }
                            });
                            controller.chooseDriver(orderid, driverId);

                            finish();
                        }
                    });

                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        driverinfo = null;
        driverinfo_listview = null;
        relation_button = null;
        choose_button = null;
        driver_head_imageview = null;
        driver_name_textview = null;
        driver_license_textview = null;
        driver_phone_textview = null;
        driver_ton_textview = null;
        driver_bargain_textview = null;
        driver_applause_rate_textview = null;
        imageLoader = null;
//        driver_star_ratingbar = null;
    }
}
