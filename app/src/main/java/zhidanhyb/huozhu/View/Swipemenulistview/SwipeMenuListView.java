package zhidanhyb.huozhu.View.Swipemenulistview;


import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.View.XList_View.XListViewFooter;
import zhidanhyb.huozhu.View.XList_View.XListViewHeader;

/**
 * @author baoyz
 */
public class SwipeMenuListView extends ListView implements OnScrollListener {

    private static final int TOUCH_STATE_NONE = 0;
    private static final int TOUCH_STATE_X = 1;
    private static final int TOUCH_STATE_Y = 2;

    private int MAX_Y = 5;
    private int MAX_X = 3;
    private float mDownX;
    private float mDownY;
    private int mTouchState;
    private int mTouchPosition;
    private SwipeMenuLayout mTouchView;
    private OnSwipeListener mOnSwipeListener;
    private Scroller mScroller; //用于滚动回
    private boolean mPullLoading;
    //标题视图内容,用它来计算头部的高度。和隐藏它
    //当禁用拉刷新。
    private RelativeLayout mHeaderViewContent;
    private TextView mHeaderTimeView;
    private int mHeaderViewHeight; // 标题视图的高度
    public boolean setUpXListView = true;

    /**
     * @return
     */
    public boolean isSetUpXListView() {
        return setUpXListView;
    }

    /**
     * @param setUpXListView
     */
    public void setSetUpXListView(boolean setUpXListView) {
        this.setUpXListView = setUpXListView;
        if (!setUpXListView) {
            mEnablePullRefresh = false;
            setPullLoadEnable(true);
        }
    }

    /**
     *
     */
    private boolean mEnablePullRefresh = true;
    /**
     *
     */
    private boolean mPullRefreshing = false; // is refreashing.
    // ——标题视图
    private XListViewHeader mHeaderView;
    // -- 页脚视图
    private XListViewFooter mFooterView;
    private boolean mEnablePullLoad;
    private boolean mIsFooterReady = false;
    // mScroller,滚动页眉或页脚。
    private int mScrollBack;
    private final static int SCROLLBACK_HEADER = 0;
    private final static int SCROLLBACK_FOOTER = 1;
    private final static int SCROLL_DURATION = 400; // scroll back duration
    private final static int PULL_LOAD_MORE_DELTA = 50; //当拉> = 50 px

    // 总列表项,用于检测列表视图的底部。
    private int mTotalItemCount;
    private OnScrollListener mScrollListener; // 用户滚动的侦听器
    // 触发刷新和加载更多的接口。
    private SwipeMenuListViewListener mListViewListener;

    private SwipeMenuCreator mMenuCreator;
    private OnMenuItemClickListener mOnMenuItemClickListener;
    private Interpolator mCloseInterpolator;
    private Interpolator mOpenInterpolator;
    private float mLastY = -1; // 保存事件y
    // 加载更多。
    private final static float OFFSET_RADIO = 1.8f; //支持iOS像拉

    public SwipeMenuListView(Context context) {
        super(context);
        initWithContext(context);
        init();
    }

    public SwipeMenuListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWithContext(context);
        init();
    }

    public SwipeMenuListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithContext(context);
        init();
    }

    private void init() {
        MAX_X = dp2px(MAX_X);
        MAX_Y = dp2px(MAX_Y);
        mTouchState = TOUCH_STATE_NONE;
    }

    /**
     *
     */
    private int MenuBgRes;

    public int getMenuBgRes() {
        return MenuBgRes;
    }

    /**
     * @param menuBgRes
     */
    public void setMenuBgRes(int menuBgRes) {
        MenuBgRes = menuBgRes;
    }

    /**
     * @param context
     */
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
        if (isSetUpXListView()) {
            mHeaderView.setVisibility(INVISIBLE);
            addHeaderView(mHeaderView);

        }

        // init footer view
        mFooterView = new XListViewFooter(context);

        // init header height
        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {
                    @SuppressWarnings("deprecation")
                    @Override
                    public void onGlobalLayout() {
                        mHeaderViewHeight = mHeaderViewContent.getHeight();
                        getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });


    }

    /**
     * @param adapter
     */
    @Override
    public void setAdapter(ListAdapter adapter) {
        if (isSetUpXListView()) {
            //确保XListViewFooter最后一页脚视图,只添加一次。
            if (mIsFooterReady == false) {
                mIsFooterReady = true;
                addFooterView(mFooterView);
            }
        }

        super.setAdapter(adapter);

        super.setAdapter(new SwipeMenuAdapter(getContext(), adapter, MenuBgRes) {
            @Override
            public void createMenu(SwipeMenu menu) {
                if (mMenuCreator != null) {
                    mMenuCreator.create(menu);
                }
            }

            @Override
            public void onItemClick(SwipeMenuView view, SwipeMenu menu,
                                    int index) {
                if (mOnMenuItemClickListener != null) {
                    mOnMenuItemClickListener.onMenuItemClick(
                            view.getPosition(), menu, index);
                }
                if (mTouchView != null) {
                    mTouchView.smoothCloseMenu();
                }
            }
        });
    }

    public void setCloseInterpolator(Interpolator interpolator) {
        mCloseInterpolator = interpolator;
    }

    public void setOpenInterpolator(Interpolator interpolator) {
        mOpenInterpolator = interpolator;
    }

    public Interpolator getOpenInterpolator() {
        return mOpenInterpolator;
    }

    public Interpolator getCloseInterpolator() {
        return mCloseInterpolator;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//		if (ev.getAction() != MotionEvent.ACTION_DOWN && mTouchView == null)
//		int action = MotionEventCompat.getActionMasked(ev);
//		action = ev.getAction();

        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();

                int oldPos = mTouchPosition;
                mDownX = ev.getX();
                mDownY = ev.getY();
                mTouchState = TOUCH_STATE_NONE;

                mTouchPosition = pointToPosition((int) ev.getX(), (int) ev.getY());

                if (mTouchPosition == oldPos && mTouchView != null
                        && mTouchView.isOpen()) {
                    mTouchState = TOUCH_STATE_X;
                    mTouchView.onSwipe(ev);
                    return true;
                }

                View view = getChildAt(mTouchPosition - getFirstVisiblePosition());

                if (mTouchView != null && mTouchView.isOpen()) {
                    mTouchView.smoothCloseMenu();
                    mTouchView = null;
                    return super.onTouchEvent(ev);
                }
                if (view instanceof SwipeMenuLayout) {
                    mTouchView = (SwipeMenuLayout) view;
                }
                if (mTouchView != null) {
                    mTouchView.onSwipe(ev);
                }
                break;
            case MotionEvent.ACTION_MOVE:


                float dy = Math.abs((ev.getY() - mDownY));
                float dx = Math.abs((ev.getX() - mDownX));
                if (mTouchState == TOUCH_STATE_X) {
                    if (mTouchView != null) {
                        mTouchView.onSwipe(ev);
                    }
                    getSelector().setState(new int[]{0});
                    ev.setAction(MotionEvent.ACTION_CANCEL);
                    super.onTouchEvent(ev);
                    return true;
                } else if (mTouchState == TOUCH_STATE_NONE) {
                    if (Math.abs(dy) > MAX_Y) {
                        mTouchState = TOUCH_STATE_Y;
                    } else if (dx > MAX_X) {
                        mTouchState = TOUCH_STATE_X;
                        if (mOnSwipeListener != null) {
                            mOnSwipeListener.onSwipeStart(mTouchPosition);
                        }
                    }
                }


                //下拉上滑
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
            case MotionEvent.ACTION_UP:

                if (mTouchState == TOUCH_STATE_X) {
                    if (mTouchView != null) {
                        mTouchView.onSwipe(ev);
                        if (!mTouchView.isOpen()) {
                            mTouchPosition = -1;
                            mTouchView = null;
                        }
                    }
                    if (mOnSwipeListener != null) {
                        mOnSwipeListener.onSwipeEnd(mTouchPosition);
                    }
                    ev.setAction(MotionEvent.ACTION_CANCEL);
                    super.onTouchEvent(ev);
                    return true;
                }

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
                    if (mEnablePullLoad && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA) {
                        startLoadMore();
                    }
                    resetFooterHeight();
                }
                break;
            default:

                break;
        }
        return super.onTouchEvent(ev);
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

    public void smoothOpenMenu(int position) {
        if (position >= getFirstVisiblePosition()
                && position <= getLastVisiblePosition()) {
            View view = getChildAt(position - getFirstVisiblePosition());
            if (view instanceof SwipeMenuLayout) {
                mTouchPosition = position;
                if (mTouchView != null && mTouchView.isOpen()) {
                    mTouchView.smoothCloseMenu();
                }
                mTouchView = (SwipeMenuLayout) view;
                mTouchView.smoothOpenMenu();
            }
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getContext().getResources().getDisplayMetrics());
    }

    public void setMenuCreator(SwipeMenuCreator menuCreator) {
        this.mMenuCreator = menuCreator;
    }

    public void setOnMenuItemClickListener(
            OnMenuItemClickListener onMenuItemClickListener) {
        this.mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public void setOnSwipeListener(OnSwipeListener onSwipeListener) {
        this.mOnSwipeListener = onSwipeListener;
    }

    public static interface OnMenuItemClickListener {
        void onMenuItemClick(int position, SwipeMenu menu, int index);
    }

    public static interface OnSwipeListener {
        void onSwipeStart(int position);

        void onSwipeEnd(int position);
    }

    private void startLoadMore() {
        mPullLoading = true;
        mFooterView.setState(XListViewFooter.STATE_LOADING);
        if (mListViewListener != null) {
            mListViewListener.onLoadMore();
        }
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

    public void setXListViewListener(SwipeMenuListViewListener l) {
        mListViewListener = l;
    }

    /**
     * 实现这个接口来刷新/负载更多的事件。
     */
    public interface SwipeMenuListViewListener {
        public void onRefresh();

        public void onLoadMore();
    }

    /**
     * 重置标题视图的高度。
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

    private LinearLayout radio;

    public void MyRadioGroup(LinearLayout invis) {
        this.radio = invis;
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    radio.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    radio.setVisibility(View.GONE);
                    break;
                case 2:

                    break;
                default:
                    break;
            }
        }

        ;
    };

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // 发送到用户的侦听器
        mTotalItemCount = totalItemCount;

        if (firstVisibleItem >= 2) {
            if (radio != null) {
                handler.sendEmptyMessage(0);
            }
        } else {
            if (radio != null) {
                handler.sendEmptyMessage(1);
            }
        }
        if (mScrollListener != null) {
            //				handler.sendEmptyMessage(2);
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
                    totalItemCount);
        }
    }

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

    /**
     * 你可以听列表视图。OnScrollListener或这一个。它将调用
     * onXScrolling当页眉/页脚滚动。
     */
    public interface OnXScrollListener extends OnScrollListener {
        public void onXScrolling(View view);
    }

    /**
     * 最后更新时间
     *
     * @param time
     */
    public void setRefreshTime(String time) {
        mHeaderTimeView.setText(time);
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
}
