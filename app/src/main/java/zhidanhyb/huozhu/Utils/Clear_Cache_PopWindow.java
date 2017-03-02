package zhidanhyb.huozhu.Utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import zhidanhyb.huozhu.R;

/**
 * 清除缓存
 * @author lxj
 *
 */
public class Clear_Cache_PopWindow extends PopupWindow{

	private ColorDrawable dw;

	public Clear_Cache_PopWindow(final Context context,View view) {
		
		
		this.setContentView(LayoutInflater.from(context).inflate(R.layout.clear_cache_layout, null));
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.MATCH_PARENT);
		dw = new ColorDrawable(context.getResources().getColor(R.color.transparent_dialog_color));
		this.setBackgroundDrawable(dw);
		this.setFocusable(true); //设置SelectPicPopupWindow弹出窗体可点击  
		this.setOutsideTouchable(false);  //设置点击屏幕其它地方弹出框消失      
		this.showAtLocation(view, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
		DataCleanManager.cleanInternalCache(context);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				dismiss();
				T.showShort(context, "清除成功!");
				clearCacheListener.onclearend();
			}
		}, 2000);
	}
	private onClearCacheListener clearCacheListener;
	public void setOnClearCacheListener(onClearCacheListener onCacheListener){
		clearCacheListener = onCacheListener;
	}
	public interface onClearCacheListener{
		void onclearend();
	}
}
