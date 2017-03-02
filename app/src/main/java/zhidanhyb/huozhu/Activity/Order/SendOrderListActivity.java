package zhidanhyb.huozhu.Activity.Order;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import zhidanhyb.huozhu.Adapter.SendOrderListAdapter;
import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.Bean.OwnerGoldBean;
import zhidanhyb.huozhu.Config.ConstantConfig;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.onOwnerGoldListener;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.ScreenUtils;
import zhidanhyb.huozhu.Utils.ViewUtils;
import zhidanhyb.huozhu.View.SendOrder_MoreDialog;
import zhidanhyb.huozhu.View.Swipemenulistview.SwipeMenu;
import zhidanhyb.huozhu.View.Swipemenulistview.SwipeMenuCreator;
import zhidanhyb.huozhu.View.Swipemenulistview.SwipeMenuItem;
import zhidanhyb.huozhu.View.Swipemenulistview.SwipeMenuListView;

/**
 * 待发订单列表
 *
 * @author lxj
 */
public class SendOrderListActivity extends BaseActivity {

    /**
     *
     */
    private SwipeMenuListView sendorder_listview;
    /**
     *
     */
    private TextView sendorder_add_button;
    /**
     *
     */
    private TextView sendorder_fahuo_button;
    public static SendOrderListActivity orderlistactivity;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.sendorderlistlayout);
        initView();
    }

    /**
     *
     */
    private void initView() {
        orderlistactivity = this;
        setHideRightButton();
        setLeftButton();
        sendorder_listview = (SwipeMenuListView) findViewById(R.id.sendorder_listview);//订单列表
        sendorder_listview.setSetUpXListView(false);
        sendorder_add_button = (TextView) findViewById(R.id.sendorder_add_button);//继续添加
        sendorder_add_button.setOnClickListener(this);
        sendorder_fahuo_button = (TextView) findViewById(R.id.sendorder_fahuo_button);//立即发货
        sendorder_fahuo_button.setOnClickListener(this);
        ViewUtils.setViewSize(context, findViewById(R.id.v2_sendorderlist_bottom), 640, 115);
        setTitleText(R.string.orderlist);
        setLeftButton();
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            /**
             * @param menu
             */
            public void create(SwipeMenu menu) {


                SwipeMenuItem edit = new SwipeMenuItem(context);
                //				 set item width
                //				shareItem.setWidth(dp2px(90));
                //				 set a icon
                //				shareItem.setIcon(R.drawable.ic_delete);
                //				 add to menu
//                edit.setBackground(context.getResources().getDrawable(R.drawable.v2_ll_send_order_bg));
                edit.setWidth(ScreenUtils.dip2px(context, 50));
                edit.setTitle("修改");
                // set item title fontsize
                edit.setTitleSize(20);
                // set item title font color
                edit.setTitleColor(Color.parseColor("#F3A556"));
                // add to menu
                menu.addMenuItem(edit);


                SwipeMenuItem deleteItem = new SwipeMenuItem(context);
                //				 set item width
                //				shareItem.setWidth(dp2px(90));
                //				 set a icon
                //				shareItem.setIcon(R.drawable.ic_delete);
                //				 add to menu
                deleteItem.setIcon(R.drawable.v2_swipe_delete);
                deleteItem.setWidth(ScreenUtils.dip2px(context, 50));
//                deleteItem.setTitle("删除");
                // set item title fontsize
                deleteItem.setTitleSize(20);
                // set item title font color
                deleteItem.setTitleColor(R.color.orange_v2);
                // add to menu
                menu.addMenuItem(deleteItem);


            }
        };
        sendorder_listview.setMenuCreator(creator);
        sendorder_listview.setMenuBgRes(R.drawable.v2_ll_send_order_bg);
        sendorder_listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

            /**
             * @param position
             * @param menu
             * @param index
             */
            public void onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0://编辑
                        Intent intent = new Intent(context, ModificationOrderActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("order", ConstantConfig.sendSureList.get(position));
                        intent.putExtra("position", position);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                        break;
                    case 1://删除
                        ConstantConfig.sendSureList.remove(position);
                        if (ConstantConfig.sendSureList.size() == 0) {
                            finish();
                        }

                        adapter.notifyDataSetChanged();
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.sendorder_add_button://继续添加
                finish();
                break;
            case R.id.sendorder_fahuo_button://立即发货
                SendOrder();
                break;
            case R.id.title_left_rl://返回
                finish();
                break;
            default:
                break;
        }
    }

    private void SendOrder() {
        //// TODO: 2017/2/20  
        if (ConstantConfig.sendSureList.size() > 0) {
            HttpController controller = new HttpController(this);
            controller.setOnWonerGoldListener(new onOwnerGoldListener() {
                @Override
                public void onGold(OwnerGoldBean ownerGoldBean) {
                    SendOrder_MoreDialog moreDialog = new SendOrder_MoreDialog(SendOrderListActivity.this, R.style.dialog);
                    moreDialog.showDialog(ownerGoldBean);
                }
            });
            controller.getOwnerGold();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    SendOrderListAdapter adapter;

    /**
     *
     */
    private void setData() {
        adapter = new SendOrderListAdapter(this, ConstantConfig.sendSureList);
//        adapter.setOnUpdataListListener(this);
        sendorder_listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sendorder_listview = null;
        sendorder_add_button = null;
        sendorder_fahuo_button = null;
        if (orderlistactivity != null) {
            orderlistactivity = null;
        }
    }

    //如果将adapter里的集合为0 调用这个接口
}
