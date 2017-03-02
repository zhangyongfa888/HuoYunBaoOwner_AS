package zhidanhyb.huozhu.Activity.Home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.Config.ZDSharedPreferences;
import zhidanhyb.huozhu.HttpRequest.HttpConfigSite;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.ScreenUtils;
import zhidanhyb.huozhu.Utils.SharedPerfenceUtil;
import zhidanhyb.huozhu.Utils.StringUtil;
import zhidanhyb.huozhu.Utils.T;
/**
 * 签到页面
 * @author lxj
 *
 */
public class Signin_Activity extends BaseActivity {
	// check by 张永发 1.背景透明

	private WebView signin_webview;
	private LinearLayout qiandao_linearlayout;

	/**
	 * @param arg0
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		
	    WindowManager.LayoutParams winlp = getWindow()
                .getAttributes();
        winlp.alpha = 1f; // 0.0-1.0

        getWindow().setAttributes(winlp);
		setContentView(R.layout.signinlayout);
		initView();
		
		
	}

	private void initView() {
		qiandao_linearlayout = (LinearLayout) findViewById(R.id.qiandao_linearlayout);
		signin_webview = (WebView)findViewById(R.id.signin_webview);
		// 支持javascript
		signin_webview.getSettings().setJavaScriptEnabled(true);
		// 设置可以支持缩放
		signin_webview.getSettings().setSupportZoom(true);
		// 设置出现缩放工具
		signin_webview.getSettings().setBuiltInZoomControls(true);
		// 扩大比例的缩放
		signin_webview.getSettings().setUseWideViewPort(true);
		signin_webview.setInitialScale(25);// 25缩放到最小比例
		
		signin_webview.setBackgroundColor(0);

		
		
		if(ZDSharedPreferences.getInstance(this).getUserId().toString().equals("") || ZDSharedPreferences.getInstance(this).getUserId() == null){
			T.showLong(this, "用户信息错误,请重试!");
			return;
		}
		SharedPerfenceUtil.setSpParams(this, SharedPerfenceUtil.signData, "date", ZDSharedPreferences.getInstance(this).getUserId().toString()+StringUtil.refFormatNowDate());

		String url = HttpConfigSite.PostUrl+HttpConfigSite.Post_Signin+ZDSharedPreferences.getInstance(this).getUserId();
		signin_webview.loadUrl(url);
		signin_webview.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.MATCH_PARENT));
		try {
			signin_webview.setVerticalScrollbarOverlay(true);
			signin_webview.addJavascriptInterface(new DemoJavaScriptInterface(),"AndroidWebView");

			signin_webview.setWebChromeClient(new chromeClient());
			signin_webview.setWebViewClient(new WebViewClient() {

				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url); // 在当前的webview中跳转到新的url
					return true;
				}

				public void onPageStarted(WebView view, String url,
						Bitmap favicon) {
					super.onPageStarted(view, url, favicon);
				}

				public void onPageFinished(WebView view, String url) {
					qiandao_linearlayout.post(new Runnable() {
							
						@Override
						public void run() {
							qiandao_linearlayout
									.setLayoutParams(new FrameLayout.LayoutParams(
											ScreenUtils.getScreenWidth(getApplicationContext()) - 200,
													FrameLayout.LayoutParams.MATCH_PARENT));
						}
					});

					super.onPageFinished(view, url);
				}
			});
		} catch (Exception e) { 

			e.printStackTrace();
		}
	}
	
	
	private class DemoJavaScriptInterface {
		DemoJavaScriptInterface() {
		}

		// tic事件：返回键与分享键均隐藏。
		@JavascriptInterface
		public void notice(String txt) {
		}

		// 事件：返回键与分享键均显示
		@JavascriptInterface
		public void showInfoFromJs() {
			
			
			
			
			
			
			
			finish();
		}
	}

	class chromeClient extends WebChromeClient {
		public void onProgressChanged(WebView view, int newProgress) {
			// 动态在标题栏显示进度条
			super.onProgressChanged(view, newProgress);
		}

		public void onReceivedTitle(WebView view, String title) {
			// 设置当前activity的标题栏
			super.onReceivedTitle(view, title);
		}
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		signin_webview.clearCache(true);
		signin_webview.clearHistory();
		signin_webview.clearFormData();
	}
}
