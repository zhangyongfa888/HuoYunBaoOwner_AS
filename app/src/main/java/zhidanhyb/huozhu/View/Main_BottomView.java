package zhidanhyb.huozhu.View;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

import zhidanhyb.huozhu.View.XList_View.XListView;


public class Main_BottomView extends LinearLayout {

    private LinearLayout container_main, top_main;
    private Context context;

    public LinearLayout getContainer_main() {
        return container_main;
    }

    public void setContainer_main(LinearLayout container_main) {
        this.container_main = container_main;
    }

    public Main_BottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public Main_BottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Main_BottomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    public LinearLayout getTop_main() {
        return top_main;
    }

    public void setTop_main(LinearLayout top_main) {
        this.top_main = top_main;
    }

    public Main_BottomView(Context context) {
        super(context);
        initView();
    }

    // 滚动辅助类
    private Scroller mScroller;
    private int mTouchSlop;
    // listview scrollView...
    private View mTarget;
    private float lastY;
    private float downY;
    private float moveY;
    private float destY;

    private void initView() {
        context = getContext();
        mScroller = new Scroller(context);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(viewConfiguration);

    }

    private int headerViewHeight;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            autoScrollerHandler();
        }
    };

    /**
     * 暂时不用了
     *
     * @param scrollY
     */
    private void conditionScroll(final int scrollY) {
        if (scrollY < headerViewHeight / 10) {// show
            if (scrollY != 0)
                mScroller.startScroll(0, scrollY, 0, -scrollY);
        } else {// hide
            if (headerViewHeight != scrollY)
                mScroller.startScroll(0, scrollY, 0, headerViewHeight - scrollY);
        }
    }

    public boolean isHeadVisable;

    public void setHeadInVisable() {
        // isHeadVisable = true;
        // if(mScroller.getCurrY()==0){//
        mTarget = null;// 下滑ListView 判断不走下滑程序
        mScroller.startScroll(0, 0, 0, headerViewHeight - 0);
        // }else{
        // mTarget = null;// 下滑ListView 判断不走下滑程序
        // mScroller.startScroll(0, 0, 0, 0);
        // }

    }

    /**
     *
     */
    public void setHeadVisable() {
        // isHeadVisable = true;
        // if(mScroller.getCurrY()==0){//
        mTarget = null;// 下滑ListView 判断不走下滑程序
        mScroller.startScroll(0, 0, 0,  0);
        // }else{
        // mTarget = null;// 下滑ListView 判断不走下滑程序
        // mScroller.startScroll(0, 0, 0, 0);
        // }

    }

    /**
     * 自动还原处理 不出现显示部分的情况
     */
    private void autoScrollerHandler() {
        final int scrollY = getScrollY();
        if (mTarget instanceof AbsListView) {
            final AbsListView absListView = (AbsListView) mTarget;
            int firstVisiblePosition = absListView.getFirstVisiblePosition();
            if (firstVisiblePosition == 0) {
                View childView = getChildAt(0);
                if (childView.getTop() < headerViewHeight) {// 显示
                    if (scrollY != 0)
                        mScroller.startScroll(0, scrollY, 0, -scrollY);
                } else {// 自动判断
                    conditionScroll(scrollY);
                }
            } else {
                conditionScroll(scrollY);
            }
        } else if (mTarget instanceof ScrollView) {// scrollview .....
            final ScrollView scrollView = (ScrollView) mTarget;
            if (scrollView.getScrollY() < headerViewHeight) {
                if (scrollY != 0)
                    mScroller.startScroll(0, scrollY, 0, -scrollY);
            } else {
                conditionScroll(scrollY);
            }
        }

        ViewCompat.postInvalidateOnAnimation(this);
    }

    /**
     * 设置能滚动的view
     *
     * @param view
     */
    public void setTargetView(View view) {
        this.mTarget = view;

    }

    public interface isScrollTopListener {
        void scrollTop(boolean isTop);
    }

    /**
     *
     */
    isScrollTopListener isScrollTopListener;

    /**
     * @param isScrollTopListener
     */
    public void setScrollTopListener(isScrollTopListener isScrollTopListener) {
        this.isScrollTopListener = isScrollTopListener;
    }


    /**
     * 控制父view 的滚动
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handler.removeCallbacksAndMessages(null);
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                downY = lastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = ev.getY();
                destY = moveY - lastY;
                // 条件判断
                judgeCondination();
                if (!isCanRefresh && !isCanMore) {
                    if (!isTouchSlopOk) {
                        final float touchDestY = downY - moveY;
                        if (Math.abs(touchDestY) > mTouchSlop) {
                            isTouchSlopOk = true;
                        }
                    } else {
                        // 处理跟随滚动
                        handleMove();
                    }
                }
                lastY = moveY;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                resetState();
                // 为了使头部在滚动view到头部时一定可见
                handler.sendMessageDelayed(handler.obtainMessage(), 100);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 滚动前的必要条件判断
     */
    public void judgeCondination() {

        if (isFrist && destY != 0) {
            isCanRefresh = !canChildScrollUp();
//            Log.i("canChildScrollUp", "judgeCondination: ---->>"+canChildScrollUp());
//            Log.e("judgeCondination", "isCanRefresh" + isCanRefresh);
            // 向上还是能滚动
            if (destY < 0) {
                isCanRefresh = false;
//                Log.e("judgeCondination", "isCanRefresh" + isCanRefresh);
            }
            isCanMore = !canChildScrollDown();
//            Log.e("judgeCondination", "isCanMore" + isCanMore);

            // 向下还是能滚动
            if (destY > 0) {
                isCanMore = false;
            }
            isFrist = false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 还能否向上滚动
     *
     * @return
     */
    public boolean canChildScrollUp() {
        if (mTarget == null) {
//            Log.e("canChildScrollUp ", "mTarget == null !");

            return true;
        }
        if (Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTarget;
                return absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0
                        || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
            } else {
                return mTarget.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mTarget, -1);
        }
    }

    /**
     * 还能否向下滚动
     *
     * @return
     */
    private boolean canChildScrollDown() {
        if (mTarget == null) {
//            Log.e("canChildScrollDown ", "mTarget == null !");
            return true;
        }
        if (Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTarget;
                final int lastVisiblePosition = absListView.getLastVisiblePosition();
                final int childIndex = lastVisiblePosition - absListView.getFirstVisiblePosition();
                return absListView.getChildCount() > 0 && (lastVisiblePosition < absListView.getCount() || absListView
                        .getChildAt(childIndex).getBottom() > absListView.getHeight() - absListView.getPaddingTop());
            } else {
                return mTarget.getScrollY() < mTarget.getHeight() - getHeight();
            }
        } else {

            return ViewCompat.canScrollVertically(mTarget, 1);
        }
    }

    /**
     * 处理跟随滚动
     */
    private void handleMove() {

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(this, InputMethodManager.SHOW_FORCED);
        imm.hideSoftInputFromWindow(this.getWindowToken(), 0); // 强制隐藏键盘


        final int scrollY = getScrollY();
        int sy = (int) (-destY * 1.15f);
        if (destY < 0) {// 向上滑

//             setHeadInVisable();

//             if (mTarget instanceof XListView) {
//             final XListView absListView = (XListView) mTarget;
//             // absListView.setCi
//             Log.e("last", absListView.getLastVisiblePosition() + " vs " +
//             absListView.getAdapter().getCount());
//
//             int last = absListView.getLastVisiblePosition();
//             if (last < absListView.getAdapter().getCount()) {
//             setHeadInVisable();
//
//             } else {
//
//             final int futureY = scrollY + sy;
//             if (futureY > headerViewHeight) {
//             sy = headerViewHeight - scrollY;
//             }
//             if (scrollY < headerViewHeight) {
//             scrollBy(0, sy);
//             }
//             }
//
//             }
            final int futureY = scrollY + sy;
            if (futureY > headerViewHeight) {
                sy = headerViewHeight - scrollY;
                if(isScrollTopListener!=null){
                    isScrollTopListener.scrollTop(true);
                }
            }
            if (scrollY < headerViewHeight) {
                scrollBy(0, sy);
            }

        } else {// 向下滑
            if (mTarget instanceof XListView) {
				final XListView absListView = (XListView) mTarget;
				int firstVisiblePosition = absListView.getFirstVisiblePosition();
				if (firstVisiblePosition == 0) {// 第一项可见 才允许下滑

					final int futureY = scrollY + sy;
					if (futureY < 0) {
						sy = -scrollY;
					}

					if (scrollY > 0) {
						scrollBy(0, sy);
					}
				} else {

				}

			}

//            final int futureY = scrollY + sy;
//            if (futureY < 0) {
//                sy = -scrollY;
//            }
//
//            if (scrollY > 0) {
//                scrollBy(0, sy);
//            }
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            int currY = mScroller.getCurrY();
            scrollTo(0, currY);
            //判断是否滑动到顶部
            if (isScrollTopListener != null) {
                if (currY == 0) {
                    isScrollTopListener.scrollTop(false);
                }else if(currY==headerViewHeight){
                    isScrollTopListener.scrollTop(true);
                }
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    // 是否第一次
    private boolean isFrist = true;
    // 能刷新不
    private boolean isCanRefresh;
    // 能加载更多不
    private boolean isCanMore;
    // 是否满足最小滚动距离
    private boolean isTouchSlopOk;

    /**
     * 还原状态
     */
    private void resetState() {
        isFrist = true;
        isCanRefresh = true;
        isCanMore = true;
        isTouchSlopOk = false;
    }

    @Override
    protected void onFinishInflate() {
        // TODO Auto-generated method stub
        super.onFinishInflate();
        if (getChildCount() > 2) {
            throw new IllegalStateException("Main_BottomView 只能拥有两个childview");
        }
        top_main = (LinearLayout) getChildAt(0);
        container_main = (LinearLayout) getChildAt(getChildCount() - 1);

    }

    /**
     * 拉长容器
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getDefaultSize(0, widthMeasureSpec);
        // 一个头一个容器
        measureChild(container_main, widthMeasureSpec, heightMeasureSpec);
        measureChild(top_main, widthMeasureSpec, heightMeasureSpec);
        headerViewHeight = top_main.getMeasuredHeight();
        int measuredHeight = getDefaultSize(0, heightMeasureSpec) + headerViewHeight;
        setMeasuredDimension(measuredWidth, measuredHeight);

    }

}
