package zhidanhyb.huozhu.Activity.Order;

import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Adapter.OrderAdapter;
import zhidanhyb.huozhu.Base.BaseFragment;
import zhidanhyb.huozhu.Bean.OrderListBean;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.onGetOrderListListener;
import zhidanhyb.huozhu.Utils.StringUtil;
import zhidanhyb.huozhu.Utils.T;
import zhidanhyb.huozhu.View.XList_View.XListView;
import zhidanhyb.huozhu.View.XList_View.XListView.IXListViewListener;
/**
 * 已完成
 * @author lxj
 *
 */
public class Main_Order_AccomplishActivity extends BaseFragment implements IXListViewListener , onGetOrderListListener{

	private XListView accomplish_listview;
	private HttpController controller;
	private int page = 1;//默认的页数
	private OrderAdapter orderadapter;
	private String pagesize = "10";//每页显示的条数
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if(view == null){
			view = inflater.inflate(R.layout.main_order_accomplishlayout, null);
			initView();
		}

		return view;
	}
	private void initView() {
		accomplish_listview = (XListView)view.findViewById(R.id.accomplish_listview);
		accomplish_listview.setPullLoadEnable(true);
		accomplish_listview.setXListViewListener(this);
		accomplish_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(),OrderDetailsActivity.class);
				intent.putExtra("orderid", orderadapter.getOrderList().get(position-1).getId());
				intent.putExtra("comment", orderadapter.getOrderList().get(position-1).getIs_commen());
				startActivity(intent);
			}
		});

		orderadapter = new OrderAdapter(getActivity());
		accomplish_listview.setAdapter(orderadapter);
	}

	private void getData() {
		controller = new HttpController(getActivity());
		controller.setOnGetOrderListListener(this);
		String flag = "6";
		String paging = page+"";
		controller.getStocksOrderList(flag,paging,pagesize);
	}

	@Override
	public void onResume() {
		super.onResume();
		page = 1;
		getData();
	}
	@Override
	public void onRefresh() {
		page = 1;
		getData();
		onLoad();
	}
	@Override
	public void onLoadMore() {
		page += 1;
		getData();
		onLoad();
	}

	public void onLoad() {
		accomplish_listview.stopRefresh();
		accomplish_listview.stopLoadMore();
		accomplish_listview.setRefreshTime(StringUtil.getTime());
	}
	@Override
	public void getOrderList(List<OrderListBean> orderlist) {
		accomplish_listview.setVisibility(View.VISIBLE);
		if(orderlist == null){
			if(page == 1){
				accomplish_listview.setVisibility(View.GONE);
				showNotData();
			}else{
				page -= 1;
				T.showShort(getActivity(), "没有更多数据了!");
			}
			return;
		}
		hideNotData();
		if(page == 1){
			if(orderadapter != null){
				orderadapter.updataListView(orderlist, 1);
			}
		}else{
			if(orderadapter != null){
				orderadapter.updataListView(orderlist, 2);
			}
		}
	}
}
