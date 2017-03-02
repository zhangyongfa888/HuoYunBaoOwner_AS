package zhidanhyb.huozhu.Activity.Me;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import zhidanhyb.huozhu.Adapter.User_CommentAdapter;
import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.Bean.CommentBean;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.onGetCommentListListener;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.StringUtil;
import zhidanhyb.huozhu.View.XList_View.XListView;
import zhidanhyb.huozhu.View.XList_View.XListView.IXListViewListener;

/**
 * 用户评论
 * @author lxj
 *
 */
public class User_CommentActivity extends BaseActivity implements IXListViewListener{

	
	private XListView commnet_listview;
	private List<String> commentlist = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.user_commentlayout);
		initView();
	}

	private void initView() {
		setTitleText(R.string.comment);
		setLeftButton();
		setHideRightButton();
		commnet_listview = (XListView)findViewById(R.id.commnet_listview);//评论列表
		commnet_listview.setPullLoadEnable(false);
		commnet_listview.setXListViewListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		getData();
	}
	
	private void getData() {
		
		HttpController controller = new HttpController(this);
		controller.setOnGetCommentListenenr(new onGetCommentListListener() {
			
			@Override
			public void getComment(List<CommentBean> commentList) {
				if(commentList == null){
					commnet_listview.setVisibility(View.GONE);
					showNotData();
				}else{
					hideNotData();
					commnet_listview.setVisibility(View.VISIBLE);
					User_CommentAdapter adapter = new User_CommentAdapter(User_CommentActivity.this,commentList);
					commnet_listview.setAdapter(adapter);
				}
			}
		});
		controller.getCommentList();
		
		
		
		for (int i = 0; i < 20; i++) {
			commentlist.add(i+"");
		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.title_left_rl://返回
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onRefresh() {
		onLoad();
	}

	@Override
	public void onLoadMore() {
		onLoad();
	}
	
	public void onLoad() {
		commnet_listview.stopRefresh();
		commnet_listview.stopLoadMore();
		commnet_listview.setRefreshTime(StringUtil.getTime());
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		commnet_listview = null;
		if(commentlist != null){
			commentlist.clear();
			commentlist = null;
		}
	}
	
}
