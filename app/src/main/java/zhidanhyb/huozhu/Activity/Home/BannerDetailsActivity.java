package zhidanhyb.huozhu.Activity.Home;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.R;
/**
 * 点击首页banner图片进入这个页面查看详情
 * @author lxj
 *
 */
public class BannerDetailsActivity extends BaseActivity {

	private WebView banner_webview;
	private ImageView imageViewBackBannerDetails;
	private TextView textViewRemarksBannerDetails;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.bannerdetailslayout);
		initView();
	}

	private void initView() {
		banner_webview = (WebView)findViewById(R.id.banner_webview);
		imageViewBackBannerDetails= (ImageView) findViewById(R.id.imageView_back_bannerDetails);
		textViewRemarksBannerDetails= (TextView) findViewById(R.id.textView_remarks_bannerDetails);
		String url = getIntent().getExtras().getString("url");
		String remarks=getIntent().getExtras().getString("remarks");

		imageViewBackBannerDetails.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		textViewRemarksBannerDetails.setText(remarks);

		// 支持javascript
		banner_webview.getSettings().setJavaScriptEnabled(true);
		// 设置可以支持缩放
		banner_webview.getSettings().setSupportZoom(true);
		// 设置出现缩放工具
		banner_webview.getSettings().setBuiltInZoomControls(true);
		// 扩大比例的缩放
		banner_webview.getSettings().setUseWideViewPort(true);
		banner_webview.setInitialScale(25);// 25缩放到最小比例
		//不在浏览器中打开网页
		banner_webview.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		banner_webview.loadUrl(url);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		banner_webview.clearCache(true);
		banner_webview.clearHistory();
		banner_webview.clearFormData();
	}
}
