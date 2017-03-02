package zhidanhyb.huozhu.Utils;

import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;
import zhidanhyb.huozhu.Bean.Home_NoticeListBean;

/**
 * 首页更新公告类
 * @author lxj
 *
 */
public class Home_CarouselNoticeUtils {


	private static Runnable runnable;

	private static Handler handler;
	public Home_CarouselNoticeUtils(Handler handler) {
		this.handler = handler;
	}

	//更新公告
	public static void carouselNotice(final int s, final List<Home_NoticeListBean> list , final TextView home_notice_textview) {
		if(runnable != null){
			runnable = null;
		}
		runnable = new Runnable() {

			@Override
			public void run() {
				AlphaAnimation aa = new AlphaAnimation(1.0f,0.1f);
				//渐变时间  
				aa.setDuration(1000);
				if(home_notice_textview == null){
					return;
				}
				home_notice_textview.startAnimation(aa);
				aa.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
					}
					@Override
					public void onAnimationRepeat(Animation animation) {
					}
					@Override
					public void onAnimationEnd(Animation animation) {
						Message msg = new Message();
						msg.what = 0;
						msg.arg1 = s;
						msg.obj = list;
						handler.sendMessage(msg);
						handler.postDelayed(runnable, 10000);//设置更新的时间
					}
				});
			}
		};
		handler.postDelayed(runnable,0);
	}

	public void remove() {
		if(runnable == null || handler == null){
			return;
		}
		handler.removeCallbacks(runnable);
	}
	
	public void start(){
		if(runnable == null || handler == null){
			return;
		}
		handler.post(runnable);
	}
}
