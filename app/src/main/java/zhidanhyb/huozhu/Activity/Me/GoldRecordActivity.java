package zhidanhyb.huozhu.Activity.Me;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import zhidanhyb.huozhu.Adapter.GoldRecordAdapter;
import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.Bean.Expense_CalendarBean;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.onGetExpenseCalendarListener;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.StringUtil;
import zhidanhyb.huozhu.Utils.T;
import zhidanhyb.huozhu.View.XList_View.XListView;
import zhidanhyb.huozhu.View.XList_View.XListView.IXListViewListener;
/**
 * 金币记录/账户记录
 * @author lxj
 *
 */
public class GoldRecordActivity extends BaseActivity implements onGetExpenseCalendarListener ,IXListViewListener{
	
	private XListView gold_listview;
	private int page = 1;//默认第一页
	private String pagesize = "10";//每页返回条数 默认10条
	private String action;// 1= 金币记录列表   2=账户记录列表
	private HttpController controller;
	private GoldRecordAdapter recordAdapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.goldrecordlayout);
		initView();
	}

	private void initView() {
		setLeftButton();
		setHideRightButton();
		gold_listview = (XListView)findViewById(R.id.gold_listview);//列表
		gold_listview.setPullLoadEnable(true);
		gold_listview.setXListViewListener(this);
		action = getIntent().getExtras().getString("action");
		if(action.toString().equals("1")){
			setTitleText(R.string.goldrecord);
		}else if(action.toString().equals("2")){
			setTitleText(R.string.accountrecord);
		}
		recordAdapter = new GoldRecordAdapter(this);
		gold_listview.setAdapter(recordAdapter);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		getData();
	}

	private void getData() {
		if(controller == null){
			controller = new HttpController(this);
			controller.setOnGetExpenseCalendarListener(this);
		}
		controller.getExpenseCalendar(page,pagesize,action);
	}

	@Override
	public void getExpenseCalendar(List<Expense_CalendarBean> calendarBeansList) {
		if(calendarBeansList == null){
			if(page == 1){
				gold_listview.setVisibility(View.GONE);
				showNotData();
			}else {
				page -= 1;
				T.showShort(this, "没有更多数据了!");
			}
			return;
		}
		hideNotData();
		if(page == 1){
			recordAdapter.updataList(calendarBeansList,1);
		}else{
			recordAdapter.updataList(calendarBeansList,2);
		}
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
		gold_listview.stopRefresh();
		gold_listview.stopLoadMore();
		gold_listview.setRefreshTime(StringUtil.getTime());
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		gold_listview = null;
		if(recordAdapter != null){
			recordAdapter = null;
		}
		if(controller != null){
			controller = null;
		}
	}
}
