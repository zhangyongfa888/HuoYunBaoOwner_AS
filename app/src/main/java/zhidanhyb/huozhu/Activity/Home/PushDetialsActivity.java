package zhidanhyb.huozhu.Activity.Home;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Base.BaseActivity;
/**
 * 首页推送列表，点击后进入的load网址详情页
 * @author lxj
 *
 */
public class PushDetialsActivity extends BaseActivity {
	private WebView push_webview;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.pushdetailslayout);
		initView();
	}

	private void initView() {
		push_webview = (WebView)findViewById(R.id.push_webview);
		
		String url = getIntent().getExtras().getString("url");
		// 支持javascript
		push_webview.getSettings().setJavaScriptEnabled(true);
		// 设置可以支持缩放
		push_webview.getSettings().setSupportZoom(true);
		// 设置出现缩放工具
		push_webview.getSettings().setBuiltInZoomControls(true);
		// 扩大比例的缩放
		push_webview.getSettings().setUseWideViewPort(true);
		push_webview.setInitialScale(25);// 25缩放到最小比例
		push_webview.loadUrl(url);
	}
	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		push_webview.clearCache(true);
		push_webview.clearHistory();
		push_webview.clearFormData();
	}
	
}
