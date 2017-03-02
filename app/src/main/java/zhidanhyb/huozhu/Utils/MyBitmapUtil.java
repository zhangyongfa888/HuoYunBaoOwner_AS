package zhidanhyb.huozhu.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.BitmapUtils;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

import zhidanhyb.huozhu.R;

/**
 * 在Xutils的图片下载方法上封装的单例模式 使用的时候直接调MyBitmapUtil.getInstance(context).setBitmap(imageView,URL);
 */
public class MyBitmapUtil {
    private MyBitmapUtil() {
    }

    private static MyBitmapUtil MyBitmapUtil;

    private static BitmapUtils bitmapUtils;// 这是xutils的图片下载类对象

    private File file;

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    /**
     * @param context
     * @return
     */
    public static MyBitmapUtil getInstance(Context context) {

        if (MyBitmapUtil == null) {
            synchronized (MyBitmapUtil.class) {
                if (MyBitmapUtil == null) {
                    File cacheDir = StorageUtils.getOwnCacheDirectory(context, context.getCacheDir().getPath() + "/DidiLogistics");
                    MyBitmapUtil = new MyBitmapUtil();
                    bitmapUtils =
                            new BitmapUtils(context, cacheDir.getAbsolutePath());
                    bitmapUtils.configThreadPoolSize(5);
                    bitmapUtils.configDefaultLoadingImage(R.drawable.v2_banner);// 下载中显示的图片
                    bitmapUtils.configDefaultLoadFailedImage(R.drawable.v2_banner);// 下载失败后显示的图片
                    bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
                    bitmapUtils.configDiskCacheEnabled(true);
                    bitmapUtils.configMemoryCacheEnabled(true);
                }
            }
        }
        // 默认的bitmap图片
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.v2_banner);
        defaultIcon = bitmap;
        return MyBitmapUtil;
    }

    private static Bitmap defaultIcon; // 默认的订单头像图标

    /**
     * @param imageView
     * @param Url
     */
    // 优先加载sd卡里下载过的图片，没有就网络加载
    public void setBitmap(ImageView imageView, String Url) {
        imageView.setImageBitmap(defaultIcon);
        if (StringUtil.isNotEmpty(Url)) {
            file = bitmapUtils.getBitmapFileFromDiskCache(Url);
            if (file != null) {
                bitmapUtils.display(imageView, file.toString());
            } else {
                try {
                    bitmapUtils.display(imageView, Url);
                } catch (Exception e) {
                    imageView.setImageResource(R.drawable.v2_banner);
                }

            }
        } else {
            imageView.setImageResource(R.drawable.v2_banner);
        }
    }

    //直接加载网络图片
    public void displayImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.pushicon)
                .error(R.drawable.pushicon)
                .centerCrop()
                .crossFade()
                .into(imageView);
    }

    //加载drawable图片
    public void displayImage(Context context, int resId, ImageView imageView) {
        Glide.with(context)
                .load(resourceIdToUri(context, resId))
                .crossFade()
                .into(imageView);
    }
    //将资源ID转为Uri
    public Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }
}
