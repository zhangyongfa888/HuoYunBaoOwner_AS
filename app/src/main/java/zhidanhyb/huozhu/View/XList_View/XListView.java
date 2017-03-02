package zhidanhyb.huozhu.View.XList_View;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import zhidanhyb.huozhu.R;


public class XListView extends ListView implements OnScrollListener {

	private float mLastY = -1; // 保存事件y
	private Scroller mScroller; //用于滚动回
	private OnScrollListener mScrollListener; // 用户滚动的侦听器

	// 触发刷新和加载更多的接口。
	private IXListViewListener mListViewListener;

	// ——标题视图
	private XListViewHeader mHeaderView;
	//标题视图内容,用它来计算头部的高度。和隐藏它
	//当禁用拉刷新。
	private RelativeLayout mHeaderViewContent;
	private TextView mHeaderTimeView;
	private int mHeaderViewHeight; // 标题视图的高度
	private boolean mEnablePullRefresh = true;
	private boolean mPullRefreshing = false; // is refreashing.

	// -- 页脚视图
	private XListViewFooter mFooterView;
	private boolean mEnablePullLoad;
	private boolean mPullLoading;
	private boolean mIsFooterReady = false;

	// 总列表项,用于检测列表视图的底部。
	private int mTotalItemCount;

	// mScroller,滚动页眉或页脚。
	private int mScrollBack;
	private final static int SCROLLBACK_HEADER = 0;
	private final static int SCROLLBACK_FOOTER = 1;

	private final static int SCROLL_DURATION = 400; // scroll back duration
	private final static int PULL_LOAD_MORE_DELTA = 50; //当拉> = 50 px
	//在底部,触发
	// 加载更多。
	private final static float OFFSET_RADIO = 1.8f; //支持iOS像拉
	//特性。

	/**
	 * @param context
	 */
	public XListView(Context context) {
		super(context);
		initWithContext(context);
	}

	public XListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initWithContext(context);
	}

	public XListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initWithContext(context);
	}

	private void initWithContext(Context context) {
		mScroller = new Scroller(context, new DecelerateInterpolator());
		// XListView需要滚动事件,它将分派的事件
		//用户的侦听器(作为代理)。
		super.setOnScrollListener(this);

		// init头视图
		mHeaderView = new XListViewHeader(context);
		mHeaderViewContent = (RelativeLayout) mHeaderView
				.findViewById(R.id.xlistview_header_content);
		mHeaderTimeView = (TextView) mHeaderView
				.findViewById(R.id.xlistview_header_time);
		addHeaderView(mHeaderView);

		// init footer view
		mFooterView = new XListViewFooter(context);

		// init header height
		mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					@SuppressWarnings("deprecation")
					@Override
					public void onGlobalLayout() {
						mHeaderViewHeight = mHeaderViewContent.getHeight();
						getViewTreeObserver().removeGlobalOnLayoutListener(this);
					}
				});
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		//确保XListViewFooter最后一页脚视图,只添加一次。
		if (mIsFooterReady == false) {
			mIsFooterReady = true;
			addFooterView(mFooterView);
		}
		super.setAdapter(adapter);
	}

	/**
	 * 启用或禁用下拉刷新功能。
	 * 
	 * @param enable
	 */
	public void setPullRefreshEnable(boolean enable) {
		mEnablePullRefresh=enable;
		if (!enable) { // 禁用,隐藏内容
			mHeaderViewContent.setVisibility(View.INVISIBLE);
		} else {
			mHeaderViewContent.setVisibility(View.VISIBLE);
		}
//		if (!mEnablePullRefresh) { // 禁用,隐藏内容
//			mHeaderViewContent.setVisibility(View.INVISIBLE);
//		} else {
//			mHeaderViewContent.setVisibility(View.VISIBLE);
//		}
	}

	//	@Override
	//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	//
	//		heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
	//				MeasureSpec.AT_MOST);  
	//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	//	}


	/**
	 * 启用或禁用加载更多的特性。
	 * 
	 * @param enable
	 */
	public void setPullLoadEnable(boolean enable) {
		mEnablePullLoad = enable;
		if (!mEnablePullLoad) {
			mFooterView.hide();
			mFooterView.setOnClickListener(null);
		} else {
			mPullLoading = false;
			mFooterView.show();
			mFooterView.setState(XListViewFooter.STATE_NORMAL);
			// “拉”和“点击”将调用加载更多。
			mFooterView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startLoadMore();
				}
			});
		}
	}

	/**
	 * 停止刷新,重置标题视图。
	 */
	public void stopRefresh() {
		if (mPullRefreshing == true) {
			mPullRefreshing = false;
			resetHeaderHeight();
		}
	}

	/**
	 * 停止加载,重置页脚视图。
	 */
	public void stopLoadMore() {

		if (mPullLoading == true) {
			mPullLoading = false;
			mFooterView.setState(XListViewFooter.STATE_NORMAL);
		}
	}
	
	public void setHeight(){
		this.setDividerHeight(2000);
	}
	

	/**
	 * 最后更新时间
	 * 
	 * @param time
	 */
	public void setRefreshTime(String time) {
		mHeaderTimeView.setText(time);
	}

	private void invokeOnScrolling() {
		if (mScrollListener instanceof OnXScrollListener) {
			OnXScrollListener l = (OnXScrollListener) mScrollListener;
			l.onXScrolling(this);
		}
	}

	private void updateHeaderHeight(float delta) {
		mHeaderView.setVisiableHeight((int) delta
				+ mHeaderView.getVisiableHeight());
		if (mEnablePullRefresh && !mPullRefreshing) { // 未处于刷新状态，更新箭头
			if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
				mHeaderView.setState(XListViewHeader.STATE_READY);
			} else {
				mHeaderView.setState(XListViewHeader.STATE_NORMAL);
			}
		}
		setSelection(0); // 每次滚动到顶部
	}

	/**
	 *重置标题视图的高度。
	 */
	private void resetHeaderHeight() {
		int height = mHeaderView.getVisiableHeight();
		if (height == 0) // 不可见的。
			return;
		// 更新和标题不显示完全。什么也不做。
		if (mPullRefreshing && height <= mHeaderViewHeight) {
			return;
		}
		int finalHeight = 0; // 默认值:滚动回头
		// 令人耳目一新,滚动显示所有标题。
		if (mPullRefreshing && height > mHeaderViewHeight) {
			finalHeight = mHeaderViewHeight;
		}
		mScrollBack = SCROLLBACK_HEADER;
		mScroller.startScroll(0, height, 0, finalHeight - height,
				SCROLL_DURATION);
		//触发computeScroll
		invalidate();
	}

	private void updateFooterHeight(float delta) {
		int height = mFooterView.getBottomMargin() + (int) delta;
		if (mEnablePullLoad && !mPullLoading) {
			if (height > PULL_LOAD_MORE_DELTA) { // 高度足以调用加载
				mFooterView.setState(XListViewFooter.STATE_READY);
			} else {
				mFooterView.setState(XListViewFooter.STATE_NORMAL);
			}
		}
		mFooterView.setBottomMargin(height);

		// setSelection(mTotalItemCount - 1);/ /滚动到下
	}

	private void resetFooterHeight() {
		int bottomMargin = mFooterView.getBottomMargin();
		if (bottomMargin > 0) {
			mScrollBack = SCROLLBACK_FOOTER;
			mScroller.startScroll(0, bottomMargin, 0, -bottomMargin,
					SCROLL_DURATION);
			invalidate();
		}
	}

	private void startLoadMore() {
		mPullLoading = true;
		mFooterView.setState(XListViewFooter.STATE_LOADING);
		if (mListViewListener != null) {
			mListViewListener.onLoadMore();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		try {
			if (mLastY == -1) {
				mLastY = ev.getRawY();
			}

			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mLastY = ev.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				final float deltaY = ev.getRawY() - mLastY;
				mLastY = ev.getRawY();
				//			System.out.println("数据监测：" + getFirstVisiblePosition() + "---->"
				//					+ getLastVisiblePosition());
				if (getFirstVisiblePosition() == 0
						&& (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
					// 第一项显示,标题显示或拉下来。
					updateHeaderHeight(deltaY / OFFSET_RADIO);
					invokeOnScrolling();
				} else if (getLastVisiblePosition() == mTotalItemCount - 1
						&& (mFooterView.getBottomMargin() > 0 || deltaY < 0)) {
					// 最后一项,已经停在了或者想拉起。
					updateFooterHeight(-deltaY / OFFSET_RADIO);
				}
				break;
			default:
				mLastY = -1; // 重置
				if (getFirstVisiblePosition() == 0) {
					//调用刷新
					if (mEnablePullRefresh
							&& mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
						mPullRefreshing = true;
						mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
						if (mListViewListener != null) {
							mListViewListener.onRefresh();
						}
					}
					resetHeaderHeight();
				}
				if (getLastVisiblePosition() == mTotalItemCount - 1) {
					// 调用加载更多。
					if (mEnablePullLoad&& mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA) {
						startLoadMore();
					}
					resetFooterHeight();
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.onTouchEvent(ev);
	}

	@Override
	public void computeScroll() {
		try {
			if (mScroller.computeScrollOffset()) {
				if (mScrollBack == SCROLLBACK_HEADER) {
					mHeaderView.setVisiableHeight(mScroller.getCurrY());
				} else {
					mFooterView.setBottomMargin(mScroller.getCurrY());
				}
				postInvalidate();
				invokeOnScrolling();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.computeScroll();
	}

	@Override
	public void setOnScrollListener(OnScrollListener l) {
		mScrollListener = l;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		try {
			if (mScrollListener != null) {
				mScrollListener.onScrollStateChanged(view, scrollState);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// 发送到用户的侦听器
		mTotalItemCount = totalItemCount;
		if (mScrollListener != null) {
			//			handler.sendEmptyMessage(2);
			mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
					totalItemCount);
		}
	}

	public void setXListViewListener(IXListViewListener l) {
		mListViewListener = l;
	}

	/**
	 *你可以听列表视图。OnScrollListener或这一个。它将调用
	 * onXScrolling当页眉/页脚滚动。
	 */
	public interface OnXScrollListener extends OnScrollListener {
		public void onXScrolling(View view);
	}

	/**
	 *实现这个接口来刷新/负载更多的事件。
	 */
	public interface IXListViewListener {
		public void onRefresh();
		public void onLoadMore();
	}
}
