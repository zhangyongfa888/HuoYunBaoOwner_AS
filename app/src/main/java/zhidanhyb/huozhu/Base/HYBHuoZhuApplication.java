package zhidanhyb.huozhu.Base;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

import cn.jpush.android.api.JPushInterface;
import zhidanhyb.huozhu.Utils.L;

public class HYBHuoZhuApplication extends Application {

	public static DisplayImageOptions options;
	public static HYBHuoZhuApplication application;
	public boolean isDebug;

	@Override
	public void onCreate() {
		super.onCreate();

		initJpush();

		// 初始化 JPush
		SDKInitializer.initialize(getApplicationContext());
		initImageLoader();
		if (application == null) {
			application = this;
		}
	}

	/**
	 *
	 */
	public void initJpush() {
		JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
		JPushInterface.init(this);
	}

	/**
	 *
	 */
	private void initImageLoader() {
		// 设置缓存目录
		File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), getCacheDir().getPath() + "/Image");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Not necessary in common
				.discCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
				.build();

		// Initialize ImageLoader with configuration
		ImageLoader.getInstance().init(config);

		if (isDebug) {
			L.isDebug = true;
		} else {
			L.isDebug = false;
		}
		options = new DisplayImageOptions.Builder()
//				 .showStubImage(R.drawable.pre)
//				 .showImageForEmptyUri(R.drawable.upload2)
//				 .showImageOnFail(R.drawable.upload2)
				.cacheInMemory(true).cacheOnDisc(true).displayer(new RoundedBitmapDisplayer(0)) // 圆角
				.build();
	}
}
