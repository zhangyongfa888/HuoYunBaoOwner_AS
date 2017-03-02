package zhidanhyb.huozhu.Config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 存储用户信息
 * @author lxj
 *
 */
public class ZDSharedPreferences {
	private static Context mContext;
	private static ZDSharedPreferences zdSharedPreferences;
	private SharedPreferences sharedPreferences;
	private Editor editor;
	private static final String RrgistrationId = "RrgistrationId";//极光id
	private static final String HpptHeadToken = "HpptHeadToken";//网络请求的token
	private static final String UserId = "UserId";//用户id
	private static final String UserMobile = "UserMobile";//用户手机号
	private static final String UserName = "UserName";//用户姓名
	private static final String UserStatus = "UserStatus";//用户状态---状态0初始 1文件上传未审核 2审核通过 9未通过
	private static final String UserHead = "UserHead";//用户头像
	private static final String UserCompany = "UserCompany";//用户企业名称
	public static ZDSharedPreferences getInstance(Context context){
		mContext = context;
		if(zdSharedPreferences == null){
			synchronized (ZDSharedPreferences.class) {
				if(zdSharedPreferences == null){
					zdSharedPreferences = new ZDSharedPreferences();
				}
			}
		}
		return zdSharedPreferences;
	}
	
	private Editor getEditor(){
		if(editor== null || sharedPreferences == null){
			editor = getSharePreferences().edit();
		}
		return editor;
	}
	
	private SharedPreferences getSharePreferences(){
		if(sharedPreferences == null){
			sharedPreferences = mContext.getSharedPreferences("ZDHuoZhu", Context.MODE_PRIVATE);
		}
		return sharedPreferences;
	}
	
	//设置Token
	public void setHttpHeadToken(String token){
		getEditor().putString(HpptHeadToken,token);
		getEditor().commit();
	}
	public String getHttpHeadToken(){
		return getSharePreferences().getString(HpptHeadToken,"");
	}
	//设置用户id
	public void setUserId(String uid){
		getEditor().putString(UserId, uid);
		getEditor().commit();
	}
	public String getUserId(){
		return getSharePreferences().getString(UserId, "");
	}
	//设置用户手机号
	public void setUserMobile(String mobile){
		getEditor().putString(UserMobile, mobile);
		getEditor().commit();
	}
	public String getUserMobile(){
		return getSharePreferences().getString(UserMobile, "");
	}
	//设置用户姓名
	public void setUserName(String name){
		getEditor().putString(UserName, name);
		getEditor().commit();
	}
	public String getUserName(){
		return getSharePreferences().getString(UserName, "");
	}
	
	//用户状态
	public void setUserStatus(String status){
		getEditor().putString(UserStatus, status);
		getEditor().commit();
	}
	public String getUserStatus(){
		return getSharePreferences().getString(UserStatus, "");
	}
	
	//用户头像
	public void setUserHead(String head){
		getEditor().putString(UserHead, head);
		getEditor().commit();
	}
	public String getUserHead(){
		return getSharePreferences().getString(UserHead, "");
	}
	//用户的企业名称
	public void setUserCompany(String company){
		getEditor().putString(UserCompany, company);
		getEditor().commit();
	}
	public String getUserCompany(){
		return getSharePreferences().getString(UserCompany, "");
	}
	//设置极光id
	public void setJPushRrgistrationId(String id){
		getEditor().putString(RrgistrationId, id);
		getEditor().commit();
	}
	public String getJPushRrgistrationId(){
		return getSharePreferences().getString(RrgistrationId, "");
	}
}
