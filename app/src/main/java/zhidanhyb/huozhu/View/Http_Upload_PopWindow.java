package zhidanhyb.huozhu.View;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import zhidanhyb.huozhu.R;
/*
 * 网络请求加载时的progressbar
 */
public class Http_Upload_PopWindow extends PopupWindow{


	private Context mContext;
	private View popview;
	public Http_Upload_PopWindow(Context context) {
		mContext = context;
	}

	public void showPop(){
		if(mContext != null){
			popview = LayoutInflater.from(mContext).inflate(R.layout.http_upload_popwindow_layout, null);
			this.setWidth(LayoutParams.MATCH_PARENT);
			this.setHeight(LayoutParams.MATCH_PARENT);
			setFocusable(true);
			ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
			setBackgroundDrawable(dw);
			this.setContentView(popview);
			if(mContext != null){
				this.showAtLocation(popview, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
			}
			popview.setOnKeyListener(new OnKeyListener() {
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
						clossPop();
					}
					return false;
				}
			});
		}
	}
	public void clossPop(){
		dismiss();
		if(mContext != null){
			mContext = null;
		}
		if(popview != null){
			popview = null;
		}
	}
}
