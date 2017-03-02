package zhidanhyb.huozhu.Utils;


import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import zhidanhyb.huozhu.Base.AppManager;
import zhidanhyb.huozhu.Config.ConstantConfig;

/**
 * 后台自动更新APK
 * @author lxj
 *
 */
public class UpdataApk_Service extends Service{

	
	 /** 安卓系统下载类 **/  
    DownloadManager manager;  
    
    /** 接收下载完的广播 **/  
    DownloadCompleteReceiver receiver; 
    
    
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	
	 /** 初始化下载器 **/  
    private void initDownManager() {  
          
    	if(ConstantConfig.Url == null){
    		T.showShort(this, "未获取到更新地址,请重试!");
    		return;
    	}
    	
        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);  
          
        receiver = new DownloadCompleteReceiver();  
  
        //设置下载地址  
        Request down = new Request(
                Uri.parse(ConstantConfig.Url));  
          
        // 设置允许使用的网络类型，这里是移动网络和wifi都可以  
        down.setAllowedNetworkTypes(Request.NETWORK_MOBILE
                | Request.NETWORK_WIFI);
          
        // 下载时，通知栏显示途中  
        down.setNotificationVisibility(Request.VISIBILITY_VISIBLE);  
          
        // 显示下载界面  
        down.setVisibleInDownloadsUi(true);  
          
        // 设置下载后文件存放的位置  
        down.setDestinationInExternalFilesDir(this,  
                Environment.DIRECTORY_DOWNLOADS, "huoyunbao_huozhu.apk");  
        // 将下载请求放入队列  
        manager.enqueue(down);  
        //注册下载广播  
        registerReceiver(receiver, new IntentFilter(  
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));  
    } 
    
    @Override  
    public int onStartCommand(Intent intent, int flags, int startId) {  
          
        // 调用下载  
        initDownManager();  
          
        return super.onStartCommand(intent, flags, startId);  
    }  
    
    @Override  
    public void onDestroy() {  
  
        // 注销下载广播  
        if (receiver != null)  
            unregisterReceiver(receiver);  
          
        super.onDestroy();  
    }  
  
    // 接受下载完成后的intent  
    class DownloadCompleteReceiver extends BroadcastReceiver {  
          
        @Override  
        public void onReceive(Context context, Intent intent) {  
            //判断是否下载完成的广播  
            if (intent.getAction().equals(  
                    DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {  
                  
                //获取下载的文件id  
                long downId = intent.getLongExtra(  
                        DownloadManager.EXTRA_DOWNLOAD_ID, -1);  
                  
                //自动安装apk  
                installAPK(manager.getUriForDownloadedFile(downId));  
                  
                //停止服务并关闭广播  
                UpdataApk_Service.this.stopSelf();  
  
            }  
        }  
  
        /** 
         * 安装apk文件 
         */  
        private void installAPK(Uri apk) {
        	Intent intent = new Intent();
    		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    		intent.setAction(Intent.ACTION_VIEW);
    		intent.addCategory("android.intent.category.DEFAULT");
    		intent.setDataAndType(apk,
    				"application/vnd.android.package-archive");
    		startActivity(intent);
    		// 杀死自己的进程
    		AppManager.getAppManager().finishAllActivity();
    		android.os.Process.killProcess(android.os.Process.myPid());
        }  
  
    }  
}
