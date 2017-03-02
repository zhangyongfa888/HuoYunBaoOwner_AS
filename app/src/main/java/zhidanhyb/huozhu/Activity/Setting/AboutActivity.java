package zhidanhyb.huozhu.Activity.Setting;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Base.BaseActivity;

public class AboutActivity extends BaseActivity {

	private TextView about_url_textview;
	private WebView about_webview;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aboutlayout);
		initView();
	}

	private void initView() {
		setLeftButton();
		setHideRightButton();
		setTitleText(R.string.about);
		about_url_textview = (TextView)findViewById(R.id.about_url_textview);//网址
		about_url_textview.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG ); //下划线
		about_url_textview.getPaint().setAntiAlias(true);//抗锯齿

		about_webview = (WebView)findViewById(R.id.about_webview);//网页

		// 支持javascript
		about_webview.getSettings().setJavaScriptEnabled(true);
		// 设置可以支持缩放
		about_webview.getSettings().setSupportZoom(true);
		// 设置出现缩放工具
		about_webview.getSettings().setBuiltInZoomControls(true);
		// 扩大比例的缩放
		about_webview.getSettings().setUseWideViewPort(true);
		about_webview.setInitialScale(25);// 25缩放到最小比例
		about_url_textview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				about_webview.loadUrl("http://www.huoyun8.cn");
			}
		});
		about_webview.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageFinished(WebView view, String url) {
				about_webview.setVisibility(View.VISIBLE);
				super.onPageFinished(view, url);
			}
		});


	}
}
