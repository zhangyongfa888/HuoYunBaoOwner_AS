package zhidanhyb.huozhu.Utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * @描述 App帮助�?
 * @author Administrator
 * 
 */
public class AppUtil
{
	public final static String APPNAME = "qianxx.egodrugs";

	/**
	 * @描述 打印日志
	 * @方法�? write(String msg)
	 * @param msg 要打印的字符
	 * @return void
	 */
	public static void write(String msg)
	{
		Log.d(APPNAME, msg);
	}

	/**
	 * @描述 显示提示信息
	 * @方法�? showMsg(Context c, int resId)
	 * @param c 上下文对�?
	 * @param resId 资源id
	 * @return void
	 */
	public static void showMsg(Context c, int resId)
	{
		Toast.makeText(c, c.getText(resId), Toast.LENGTH_LONG).show();
	}

	/**
	 * @描述 显示提示信息
	 * @方法�? showMsg(Context c, String res)
	 * @param c 上下文对�?
	 * @param res 资源
	 * @return void
	 */
	public static void showMsg(Context c, String res)
	{
		Toast.makeText(c, res, Toast.LENGTH_LONG).show();
	}

	/**
	 * @描述 �?查网络是否可�?
	 * @方法�? isNetworkConnected(Context c)
	 * @param c 上下文对�?
	 * @return boolean 网络是否可用
	 */
	public static boolean isNetworkConnected(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	/**
	 * �?查sd卡是否存�?
	 * 
	 */
	public static boolean isSdExist()
	{
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取app版本�?
	 * 
	 * @return
	 */
	public static String getVersionName(Context context) {
		// 获取packagemanager的实�?
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名�?0代表是获取版本信�?
		PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		String version = packInfo.versionName;
		return version;
	}
	/**
	 * 获取设备名称
	 * 
	 * @return
	 */
	public static String GetDeviceName(){
		return new Build().MODEL;
	} 
	
	/* 
	 * @param 获取设备号
	 * @return
	 */
	public static String getDeviceId(Context mContext){
		final TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}
	
	/**
     * 判断当前应用程序处于前台还是后台 : 通过RunningAppProcessInfo类判断（不需要额外权限）：
     * 
     * @return 如果返回true 则运行于后台，如果返回 false 则运行于前台
     * */
    public static boolean isBackground(Context context)
    {
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (RunningAppProcessInfo appProcess : appProcesses)
        {
            if (appProcess.processName.equals(context.getPackageName()))
            {
                if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND)
                {
                    Log.i("后台", appProcess.processName);
                    return true;
                }
                else
                {
                    Log.i("前台", appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }
}
