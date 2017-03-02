package zhidanhyb.huozhu.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;

import cn.sharesdk.framework.ShareSDK;
import zhidanhyb.huozhu.R;

//import cn.sharesdk.onekeyshare.OnekeyShare;
//import cn.sharesdk.onekeyshare.OnekeyShareTheme;

public class ShareUtils {

	public ShareUtils() {

	}

	private static Context mContext;
	private static String orderId;
	private static ShareUtils shareUtils;

	public static ShareUtils getInstance() {
		if (shareUtils == null) {
			synchronized (ShareUtils.class) {
				if (shareUtils == null) {
					shareUtils = new ShareUtils();
				}
			}
		}
		return shareUtils;
	}

	public void init(Context context) {
		mContext = context;
		ShareSDK.initSDK(mContext);
		new Thread() {
			public void run() {
				initImagePath(mContext);
			}
		}.start();
	}

	private String logoImg;
	// private String FILE_NAME = "logo_yueyue.png";
	private String FILE_NAME = "share_logo.png";

	private void initImagePath(Context context) {
		try {
			String cachePath = com.mob.tools.utils.R
					.getCachePath(context, null);
			logoImg = cachePath + FILE_NAME;
			File file = new File(logoImg);
			if (!file.exists()) {
				file.createNewFile();
				Bitmap pic = BitmapFactory.decodeResource(
						context.getResources(), R.drawable.app_icon);
				FileOutputStream fos = new FileOutputStream(file);
				pic.compress(CompressFormat.JPEG, 100, fos);
				fos.flush();
				fos.close();
			}
		} catch (Throwable t) {
			t.printStackTrace();
			logoImg = null;
		}
	}

	/**
	 * 
	 * @param url,分享链接   暂时取消   需要集成SHAREsdk  转换成as版本没集成
	 * @param share_content
	 * @param orderId
	 */
	public void showShare(String url,String share_content, String orderId) {
		if (mContext == null) {
			return;
		}
		if(share_content == null){
			return;
		}
//		this.orderId = orderId;
//		OnekeyShare oks = new OnekeyShare();
//		// 关闭sso授权
//		oks.disableSSOWhenAuthorize();
//
//		if (logoImg != null) {
//			File file = new File(logoImg);
//			if (file.exists()) {
//				oks.setImagePath(logoImg);// 确保SDcard下面存在此张图片
//				oks.setFilePath(logoImg);
//			}
//		}
//		oks.setTitle("货运宝(货主端)");
//		// text是分享文本，所有平台都需要这个字段
//		oks.setText(share_content);
//		oks.setTheme(OnekeyShareTheme.CLASSIC);
//		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//		if (logoImg != null) {
//			oks.setImagePath(logoImg);// 确保SDcard下面存在此张图片
//		}
//		// oks.setImageUrl(Urls.LOGO_URL);
//		// url仅在微信（包括好友和朋友圈）中使用
//		//			Log.i("ss", url);
//		//			oks.setUrl(url);
//		if(url.equals("")||url.equals("null")||url == null){
//			//				oks.setUrl("http://www.huoyun8.cn");
//			oks.setUrl("http://www.huoyun8.cn/download.html");
//		}else{
//			oks.setUrl(url);
//		}
//		// 启动分享GUI
//		oks.show(mContext);
//
//		oks.setCallback(new PlatformActionListener() {
//
//			@Override
//			public void onError(Platform arg0, int arg1, Throwable arg2) {
//
//			}
//
//			@Override
//			public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
//				//4-微信好友    5 -微信朋友圈  13-短信
//				String platform = null;
//				if(arg0.getSortId() == 4){
//					platform = "1";//微信好友
//				}else if(arg0.getSortId() == 5){
//					platform = "2";//微信朋友圈
//				}else if(arg0.getSortId() == 13){
//					platform = "3";//短信
//				}
//				HttpController controller = new HttpController(mContext);
//				controller.shareSuccessGetGold(ShareUtils.orderId,platform);
//			}
//
//			@Override
//			public void onCancel(Platform arg0, int arg1) {
//
//			}
//		});
	}



}
