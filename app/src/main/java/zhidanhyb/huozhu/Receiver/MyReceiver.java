package zhidanhyb.huozhu.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.data.JPushLocalNotification;
import zhidanhyb.huozhu.Activity.MainActivity;
import zhidanhyb.huozhu.Activity.Order.OrderDetailsActivity;
import zhidanhyb.huozhu.Config.ConstantConfig;
import zhidanhyb.huozhu.Utils.L;

/**
 * 自定义接收器
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";
	private Context mContext;
	@Override
	public void onReceive(Context context, Intent intent) {
		this.mContext = context;
		Bundle bundle = intent.getExtras();
		L.i("推送过来了=======");
		//		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			//        	T.showLong(context, "推送过来了1");
			String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			//            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
			//send the Registration Id to your server...

		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
			//        	T.showLong(context, "推送过来了2");
			//        	Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
			processCustomMessage(context, bundle);

		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
			//        	Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
			//            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
			//            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
			//            if(Main_Home_Fragment.main_home_fragment.home_message_button != null){
			//            	Main_Home_Fragment.main_home_fragment.home_message_button.setBackgroundResource(R.drawable.home_title_message_unread);
			//            }

		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			//        	T.showLong(context, "推送过来了用户点击了");
			//            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
			notifyJump(bundle);
			//打开自定义的Activity
			//        	Intent i = new Intent(context,Home_Message_Inform_Activity.class);
			//        	i.putExtras(bundle);
			//        	//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			//        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
			//        	context.startActivity(i);

		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
			//        	T.showLong(context, "推送过来了4");
			//            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
			//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

		} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
			//        	T.showLong(context, "推送过来了3");
			//        	boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
			//        	Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
		} else {
			//        	Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
		}
	}
	//send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {
		
		String content = bundle.getString(JPushInterface.EXTRA_MESSAGE);
		String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
		if (!extras.isEmpty()) {
			try {
				JSONObject extraJson = new JSONObject(extras);
				if (null != extraJson && extraJson.length() > 0) {
					Iterator<String> it =  extraJson.keys();
					while (it.hasNext()) {
						String myKey = it.next().toString();
						if(myKey.toString().equals("oid")){
							ConstantConfig.NotifyOrderId = extraJson.optString(myKey);
						}
						if(myKey.toString().equals("type")){
							//推送type4:进订单详情  type5 司机取消竞单  type7身份审核通过  type8身份审核不通过
							ConstantConfig.NotifyType = extraJson.optString(myKey);
							if(ConstantConfig.NotifyType.toString().equals("4") 
									|| ConstantConfig.NotifyType.toString().equals("5")){
								JPushLocalNotification ln = new JPushLocalNotification();
								ln.setBuilderId(1);
								ln.setTitle(content);
								ln.setNotificationId(System.currentTimeMillis());
								ln.setContent(content);
								ln.setBroadcastTime(new Date(System.currentTimeMillis() + 300));//+300是为了有延迟
								Map<String , Object> map = new HashMap<String, Object>() ;
								map.put("oid",ConstantConfig.NotifyOrderId);
								map.put("type",ConstantConfig.NotifyType);	
								JSONObject json = new JSONObject(map);
								ln.setExtras(json.toString());
								JPushInterface.addLocalNotification(context,ln);
								if(ConstantConfig.isOrderDetails){//判断货主当前的页面是不是订单详情页 - 默认false不在   true在
									Intent intent = new Intent(ConstantConfig.updataOrderDetails);
									intent.putExtra("orderid", ConstantConfig.NotifyOrderId);
									mContext.sendBroadcast(intent);
								}
								if(ConstantConfig.isOrderList){//判断货主当前的页面是不是订单列表页面 - 默认false不在   true在	
									Intent intent = new Intent(ConstantConfig.updataOrderList);
									mContext.sendBroadcast(intent);
								}
							}else if(ConstantConfig.NotifyType.toString().equals("7") 
									|| ConstantConfig.NotifyType.toString().equals("8")){
								JPushLocalNotification ln = new JPushLocalNotification();
								ln.setBuilderId(1);
								ln.setTitle(content);
								ln.setNotificationId(System.currentTimeMillis());
								ln.setContent(content);
								ln.setBroadcastTime(new Date(System.currentTimeMillis() + 300));//+300是为了有延迟
								JPushInterface.addLocalNotification(context,ln);
							}
						}
					}
				}
			} catch (JSONException e) {

			}
		}
	}

	
	//点击通知，跳转到指定页面
	private void notifyJump(Bundle bundle){
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				JSONObject json;
				try {
					json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();
					while (it.hasNext()) {
						String myKey = it.next().toString();
						if(myKey.toString().equals("oid")){
							ConstantConfig.NotifyOrderId = json.optString(myKey);
						}
						if(myKey.toString().equals("type")){
							//推送   type3：进附近列表    type4:进订单详情  type5 不跳转
							
							ConstantConfig.NotifyType = json.optString(myKey);
							if(ConstantConfig.NotifyType.toString().equals("4") || ConstantConfig.NotifyType.toString().equals("5")){
								Intent i = new Intent();
								i.setClass(mContext,OrderDetailsActivity.class);
								i.putExtra("orderid",ConstantConfig.NotifyOrderId);
								i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								mContext.startActivity(i);
							}else if(ConstantConfig.NotifyType.toString().equals("7") || ConstantConfig.NotifyType.toString().equals("8")){
								Intent i = new Intent();
								i.setClass(mContext,MainActivity.class);
								i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								mContext.startActivity(i);
							}
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
