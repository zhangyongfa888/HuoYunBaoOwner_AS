package zhidanhyb.huozhu.HttpRequest;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import zhidanhyb.huozhu.Activity.Message.Main_MessageActivity;
import zhidanhyb.huozhu.Activity.Setting.Setting_OpinionActivity;
import zhidanhyb.huozhu.Bean.AllDriverLocationBean;
import zhidanhyb.huozhu.Bean.BankListBean;
import zhidanhyb.huozhu.Bean.CommentBean;
import zhidanhyb.huozhu.Bean.DriverInfoBean;
import zhidanhyb.huozhu.Bean.DriverInfo_CommentBean;
import zhidanhyb.huozhu.Bean.Expense_CalendarBean;
import zhidanhyb.huozhu.Bean.HomeBean;
import zhidanhyb.huozhu.Bean.Home_AdvertiseListBean;
import zhidanhyb.huozhu.Bean.Home_NoticeListBean;
import zhidanhyb.huozhu.Bean.Home_PushMessageListBean;
import zhidanhyb.huozhu.Bean.Home_PushMessageListMoreBean;
import zhidanhyb.huozhu.Bean.MessageListBean;
import zhidanhyb.huozhu.Bean.OrderDetials_Bean;
import zhidanhyb.huozhu.Bean.OrderDetials_Driverinfo_Bean;
import zhidanhyb.huozhu.Bean.OrderDetials_Orderinfo_Bean;
import zhidanhyb.huozhu.Bean.OrderDetials_Releaseinfo_Bean;
import zhidanhyb.huozhu.Bean.OrderListBean;
import zhidanhyb.huozhu.Bean.OwnerGoldBean;
import zhidanhyb.huozhu.Bean.ShareContentBean;
import zhidanhyb.huozhu.Bean.UpdataVersionBean;
import zhidanhyb.huozhu.Bean.UserAccountDataBean;
import zhidanhyb.huozhu.Bean.UserGoldBean;
import zhidanhyb.huozhu.Bean.UserLoginBean;
import zhidanhyb.huozhu.Config.ZDSharedPreferences;
import zhidanhyb.huozhu.Utils.AppUtil;
import zhidanhyb.huozhu.Utils.L;
import zhidanhyb.huozhu.Utils.T;
import zhidanhyb.huozhu.Utils.UserExitUtils;
import zhidanhyb.huozhu.View.Http_Upload_PopWindow;

/**
 * Http控制器
 *
 * @author lxj
 */
public class HttpController implements HttpRequestCallback {
    private static Context mContext;
    private Http_Upload_PopWindow popWindow;
    private String Token = null;
    private String Uid = null;
    private static final int GetVerifyCode = 100;// 获取验证码
    private static final int VerifyCode = 200;// 验证验证码
    private static final int UserRegister = 300;// 用户注册
    private static final int UserResetPassword = 400;// 用户重置密码
    private static final int UserLogin = 500;// 用户登录
    private static final int GetHomeData = 600;// 获取首页数据
    private static final int GetHomeDataLoadMore = 650;// 获取首页列表更多数据
    private static final int VerifyImage = 700;// 上传验证图片
    private static final int GetUserStatus = 800;// 获取用户状态
    private static final int UpdataUserHead = 900;// 上传用户头像
    private static final int GetMessageList = 1000;// 获取消息列表
    private static final int DeleteMessage = 1100;// 删除消息
    private static final int GetUserAccount = 1200;// 获取用户账户信息
    private static final int GetUserGold = 1300;// 获取用户金币
    private static final int SendOrder = 1400;// 货主发送订单
    private static final int SendAganiOrder = 1410;// 货主发送订单
    private static final int getStocksOrderList = 1500;// 获取已完成订单列表/进行中订单列表
    private static final int FeedBack = 1700;// 意见反馈
    private static final int ExitLogin = 1800;// 退出登录
    private static final int getOrderDetails = 1900;// 获取订单详情
    private static final int conversionGold = 2000;// 兑换金币
    private static final int rechargeMoney = 2100;// 充值
    private static final int submitPassword = 2200;// 设置提现密码
    private static final int getBankList = 2300;// 获取银行卡列表
    private static final int addBankCard = 2400;// 添加银行卡
    private static final int submitWithdraw = 2500;// 提现
    private static final int deleteBankCard = 2600;// 解除银行卡
    private static final int getDriverInfo = 2700;// 获取司机信息
    private static final int chooseDriver = 2800;// 选择司机
    private static final int updataVersion = 2900;// 更新版本
    private static final int getAllDriver = 3000;// 获取所有司机的位置
    private static final int owenrCommmentDriver = 3100;// 货主评论司机
    private static final int payMentfreight = 3200;// 货主微信支付运费给司机（获取微信支付的订单信息）
    private static final int cancleOrder = 3300;// 货主取消订单前先获取还有几次取消机会
    private static final int cancleSuccess = 3400;// 取消订单成功
    private static final int getOwnerGold = 3500;// 货主发单扣除金币
    private static final int getShare = 3600;// 获取分享内容
    private static final int shareSuccessGold = 3700;// 分享成功后领取奖励金币
    private static final int getCommentList = 3800;// 获取评论列表
    private static final int getExpenseCalendar = 3900;// 用户消费记录（金币记录、用户记录）
    private static final int getPayPow = 4000;// 用户是否设置了交易密码
    private static final int UpLoadNearbyDrivers = 4100;// 获取货主当前位置附近的司机坐标
    private static final int UserRefreshPersonInfo = 4200;// 用户修改信息
    private static final int getPersonInfo = 4300;// 用户修改信息
    private static final int UserModifyPwd = 4400;// 用户修改信息

    public HttpController(Context context) {
        mContext = context;
        if (Uid == null) {
            Uid = ZDSharedPreferences.getInstance(mContext).getUserId();
        }
        if (Token == null) {
            Token = ZDSharedPreferences.getInstance(mContext).getHttpHeadToken();
        }
        popWindow = new Http_Upload_PopWindow(mContext);
        // handler.sendEmptyMessage(1);

    }

    /**
     * +1.2016.08.16 zhangyongfa 用户修改个人信息
     *
     * @param name    姓名
     * @param sex     性别 1 男 0 女
     * @param company gongsi
     */
    public void UserRefreshPersonInfo(String name, String sex, String company) {
        RequestParams params = new RequestParams();

        params.put("token", Token);
        params.put("userId", Uid);
        params.put("sex", sex);
        params.put("name", name);
        params.put("company", company);
        L.i("用户注册的参数==" + params.toString());
        HttpPost(HttpConfigSite.Post_User_UpdatePersonInfo, params, UserRefreshPersonInfo);
    }

    private userRefreshPersonInfo userRefreshPersonInfo;

    public void setUserRefreshPersonInfo(userRefreshPersonInfo userEditPersonInfoActivity) {
        this.userRefreshPersonInfo = userEditPersonInfoActivity;
    }

    /**
     * @param type   1 登录密码 2  支付密码
     * @param OldPwd 旧密码
     * @param newPwd 新密码
     */
    public void exeModifyPwd(String type, String OldPwd, String newPwd) {
        RequestParams params = new RequestParams();

        params.put("token", Token);
        params.put("userId", Uid);
        params.put("type", "2");//1 司机  2货主
        params.put("kind", type);
        params.put("pwd", OldPwd);
        params.put("password", newPwd);
        L.i("用户注册的参数==" + params.toString());
        HttpPost(HttpConfigSite.Post_User_ModifyPwd, params, UserModifyPwd);
    }

    /**
     * @param modify
     */
    public void setOnModifyPwdListener(modifyPwd modify) {
        m = modify;
    }

    private modifyPwd m;

    /**
     *
     */
    public interface modifyPwd {
        /**
         *
         */
        void modifyBack();
    }

    public interface userRefreshPersonInfo {
        void refreshInfo();
    }

    /**
     * +2 .2016.08.16 zhangyongfa 获取用户个人信息
     */
    public void getUserInfo() {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        // params.put("deviceId", deviceId);

        L.i("用户注册的参数==" + params.toString());
        HttpPost(HttpConfigSite.Post_User_PersonInfo, params, getPersonInfo);

    }

    public void setUserInfo(getUserInfo userInfo) {

        this.userInfo = userInfo;
    }

    public getUserInfo userInfo;

    public interface getUserInfo {
        void getInfo(String name, String sex, String company);
    }

    /**
     * 1.获取验证码
     *
     * @param mobile   - 手机号
     * @param deviceId - 设备唯一识别码
     * @param type     - （1司机端 2货主端）
     * @param intent   - 获取验证码的意图是 1-注册 2-忘记密码
     */
    public void getVerifyCode(String mobile, String deviceId, String type, String intent) {
        RequestParams params = new RequestParams();
        params.put("mobile", mobile);
        params.put("deviceId", deviceId);
        params.put("type", type);
        if (intent.equals("1")) {// 注册
            HttpPost(HttpConfigSite.Post_Get_Code, params, GetVerifyCode);
        } else if (intent.equals("2")) {// 忘记密码
            HttpPost(HttpConfigSite.Post_User_ForgetPass_GetCode, params, GetVerifyCode);
        }
    }

    /**
     * 2.验证验证码
     *
     * @param mobile   - 手机号
     * @param deviceId - 设备唯一识别码
     * @param vode     - 验证码
     * @param intent   - 验证验证码的意图是 1-注册 2-忘记密码
     */
    public void verifyCode(String mobile, String deviceId, String vode, String intent) {
        RequestParams params = new RequestParams();
        params.put("mobile", mobile);
        params.put("deviceId", deviceId);
        params.put("identifyCode", vode);
        if (intent.equals("1")) {// 验证注册用户的
            HttpPost(HttpConfigSite.Post_Code_Verify, params, VerifyCode);
        } else if (intent.equals("2")) {// 验证忘记密码的
            HttpPost(HttpConfigSite.Post_User_ForgetPass_Code_Verify, params, VerifyCode);
        }

    }

    /**
     * 1.1、2.1获取验证码、验证验证码接口 - getVerifyCode(String mobile, String deviceId
     * ,String type, String intent) - verifyCode(String mobile , String deviceId
     * , String vode , String intent)
     */
    private getVerifyCodeListener codeListener;

    public void setGetVerifyCodeListener(getVerifyCodeListener getverifycode) {
        this.codeListener = getverifycode;
    }

    public interface getVerifyCodeListener {
        void getCode();

        void getRegister();
    }

    /**
     * 3.用户注册
     *
     * @param mobile   - 手机号
     * @param deviceId - 设备唯一识别码
     * @param name     - 姓名
     * @param sex      - 性别
     * @param password - 密码
     */
    public void userRegister(String mobile, String deviceId, String name, String sex, String password, String company) {
        RequestParams params = new RequestParams();
        params.put("mobile", mobile);
        params.put("deviceId", deviceId);
        params.put("name", name);
        params.put("sex", sex);
        params.put("password", password);
        params.put("company", company);
        HttpPost(HttpConfigSite.Post_User_Register, params, UserRegister);
    }

    /**
     * 3.1用户注册接口 - userRegister(String mobile,String deviceId,String name,String
     * sex,String password,String company)
     */
    private userRegisterListener registerlistener;

    public void setUserRegisterListener(userRegisterListener uListener) {
        this.registerlistener = uListener;
    }

    public interface userRegisterListener {
        void Register();
    }

    /**
     * 4.用户重置密码
     *
     * @param phone    - 手机号
     * @param password - 密码
     * @param type     - 类型（1司机端 2货主端）
     */
    public void User_ResetPassword(String phone, String password, String type) {
        RequestParams params = new RequestParams();
        params.put("mobile", phone);
        params.put("password", password);
        params.put("type", type);
        HttpPost(HttpConfigSite.Post_User_ResetPassword, params, UserResetPassword);
    }

    /**
     * 4.1用户重置密码接口 - User_ResetPassword(String phone, String password, String
     * type)
     */
    private getUserResetPasswordListener resetPasswordListener;

    public void setGetUserResetPasswordListener(getUserResetPasswordListener userResetPasswordListener) {
        this.resetPasswordListener = userResetPasswordListener;
    }

    public interface getUserResetPasswordListener {
        void ResetPassword();
    }

    /**
     * 5.用户登录
     *
     * @param mobile   - 手机号
     * @param deviceId - 设备唯一识别码
     * @param password - 密码
     * @param jpid     - 极光id
     */
    public void userLogin(String mobile, String deviceId, String password, String jpid) {
        RequestParams params = new RequestParams();
        params.put("mobile", mobile);
        params.put("deviceId", deviceId);
        params.put("password", password);
        params.put("registrationId", jpid);
        params.put("systemName", "Android");
        L.i("登录==" + params);
        HttpPost(HttpConfigSite.Post_User_Login, params, UserLogin);
    }

    /**
     * 5.1用户登录接口 - userLogin(String mobile, String deviceId, String password,
     * String jpid)
     */
    private userLoginSuccessListener uListener;

    public void setUserLoginSuccessListener(userLoginSuccessListener loginSuccessListener) {
        this.uListener = loginSuccessListener;
    }

    public interface userLoginSuccessListener {
        void userLogin(UserLoginBean userLoginBean);
    }

    /**
     * 6.获取首页数据(广告列表，公告列表，推送消息列表，新消息总数)
     *
     * @param -   用户id
     * @param -   请求token
     * @param lng - 经度
     * @param lat - 纬度
     */
    public void getHomeData(double lat, double lng) {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("lat", lat + "");
        params.put("lng", lng + "");
        HttpPost(HttpConfigSite.Post_Home_Data, params, GetHomeData);

    }

    /**
     * 6.1获取首页数据的接口 - getHomeData(String userId, String token)
     */
    private getHomeDataListener dataListener;

    public void setGetHomeDataListener(getHomeDataListener homeDataListener) {
        this.dataListener = homeDataListener;
    }

    public interface getHomeDataListener {
        /**
         * @param homeBean
         */
        void getHomeData(HomeBean homeBean);
    }

    /**
     * 6.2获取首页公告列表更多数据的接口
     *
     * @param
     * @param
     */
    public void getHomeLoadMoreData(String page) {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("paging", page);
        params.put("pagsize", "6");
        HttpPost(HttpConfigSite.Post_Home_Data_LoadMore, params, GetHomeDataLoadMore);

    }

    /**
     * 6.2获取首页公告列表更多数据的接口 - getHomeDataMore
     */
    private getHomeLoadMoreDataListener moreDataListener;

    public void setgetHomeLoadMoreDataListener(getHomeLoadMoreDataListener homeDataListener) {
        this.moreDataListener = homeDataListener;
    }

    public interface getHomeLoadMoreDataListener {
        void getHomeMoreData(Home_PushMessageListMoreBean homeMoreBean);
    }

    /**
     * 7.上传验证图片
     *
     * @param idCardPic      - 身份证照片
     * @param busiLicensePic - 营业执照照片
     */
    public void UploadVerifyImage(File idCardPic, File busiLicensePic) {
        try {
            RequestParams params = new RequestParams();
            params.put("token", Token);
            params.put("userId", Uid);
            params.put("idcard_pic", idCardPic);
            params.put("busiLicensePic", busiLicensePic);
            HttpPost(HttpConfigSite.Post_Verify_Image, params, VerifyImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 7.1上传图片的接口 - UploadVerifyImage(String token, String userid, File
     * idCardPic, File busiLicensePic)
     */
    private uploadVerifyListener loadverifylistener;

    public void setUploadVerifyListener(uploadVerifyListener upListener) {
        this.loadverifylistener = upListener;
    }

    public interface uploadVerifyListener {
        void uploadVerify();
    }

    /**
     * 8.获取用户状态值
     *
     * @param -    请求token
     * @param -    用户id
     * @param type - 1司机 2货主
     */
    public void getUserStatus(String type) {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("type", type);
        HttpPost(HttpConfigSite.post_Get_User_Status, params, GetUserStatus);
    }

    /**
     * 8.1获取用户状态值的接口 - getUserStatus(String token, String userid, String type)
     */
    private getUserStatusListener getuserstatuslistener;

    public void setGetUserStatusListenenr(getUserStatusListener userStatusListener) {
        this.getuserstatuslistener = userStatusListener;
    }

    public interface getUserStatusListener {
        /**
         * @param status
         */
        void getUserStatus(String status);
    }

    /**
     * 9.上传用户头像
     *
     * @param -        请求token
     * @param -        用户id
     * @param type     - 类型(1司机 2货主)
     * @param tempFile - 头像图片File文件
     */
    public void updataUserHead(String type, File tempFile) {
        try {
            RequestParams params = new RequestParams();
            params.put("token", Token);
            params.put("userId", Uid);
            params.put("type", type);
            params.put("pic", tempFile);
            HttpPost(HttpConfigSite.Post_UpdataUserHead, params, UpdataUserHead);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 9.1上传头像的接口 - updataUserHead(String token, String userId, String type,
     * File tempFile)
     */
    private updataUserHeadListener upuserheadlistener;

    public void setUpdataUserHeadListener(updataUserHeadListener upHeadListener) {
        this.upuserheadlistener = upHeadListener;
    }

    public interface updataUserHeadListener {
        void updataUserHead();
    }

    /**
     * 10.获取消息列表
     *
     * @param -    请求数据的token
     * @param -    用户id
     * @param page - 页数
     * @param type - 类型 1.司机端 2。货主端
     */
    public void getMessageListData(String page, String type) {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("paging", page);
        params.put("type", type);
        L.i("获取消息的参数==" + params.toString());
        HttpPost(HttpConfigSite.Post_GetMessageList, params, GetMessageList);
    }

    /**
     * 10.1获取消息列表的接口getMessageListData(String token, String userId, String page,
     * String type)
     */
    private getMessageListDataListener messageListDataListener;

    public void setGetMessageListDataListener(getMessageListDataListener getmessagelistdatalistener) {
        this.messageListDataListener = getmessagelistdatalistener;
    }

    public interface getMessageListDataListener {
        void getMeesagList(List<MessageListBean> messagelist);
    }

    /**
     * 11.删除消息列表的消息
     *
     * @param -    网络请求token
     * @param -    用户id
     * @param ids  - 要删除消息的id
     * @param type - 类型
     */
    public void deleteMessage(String ids, String type) {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("ids", ids);
        params.put("type", type);
        HttpPost(HttpConfigSite.Post_Delete_Message, params, DeleteMessage);
    }

    /**
     * 删除消息列表的消息接口 - deleteMessage(String token, String userId, String ids,
     * String type)
     */
    private deleteMessageListener deletemessagelistener;

    public void setDeleteMessageListener(deleteMessageListener deListener) {
        this.deletemessagelistener = deListener;
    }

    public interface deleteMessageListener {
        void deleteMessageSuccess();
    }

    /**
     * 12.获取用户账户信息
     *
     * @param - 网络请求的token
     * @param - 用户id
     */
    public void getUserAccountData() {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        L.i("获取用户信息==" + params.toString());
        HttpPost(HttpConfigSite.Post_GetUser_Account, params, GetUserAccount);
    }

    /**
     * 12.1获取用户信息的接口 - getUserAccountData(String token, String userId)
     */
    private getUserAccountDataListener userAccountDataListener;

    public void setGetUserAccountDataListener(getUserAccountDataListener accountDataListener) {
        this.userAccountDataListener = accountDataListener;
    }

    public interface getUserAccountDataListener {
        void getUserAccount(UserAccountDataBean userAccountDataBean);
    }

    /**
     * 13.获取用户金币、等级、星星数
     *
     * @param -    网络请求的token
     * @param -    用户id
     * @param type - (1司机 2货主)
     */
    public void getUserGold(String type) {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("type", type);
        HttpPost(HttpConfigSite.Post_GetUser_Gold, params, GetUserGold);
    }

    /**
     * 13.1获取用户金币、等级、星星数的接口 - getUserGold(String token, String userId, String
     * type)
     */
    private getUserGoldListener userGoldListener;

    public void setGetUserGoldListener(getUserGoldListener getusergoldlistener) {
        this.userGoldListener = getusergoldlistener;
    }

    public interface getUserGoldListener {
        void getUserGold(UserGoldBean userGoldBean);
    }

    /**
     * 14.货主发送订单
     *
     * @param
     * @param
     * @param jsonOrder
     */
    public void SendOrderJson(String jsonOrder) {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("str", jsonOrder);

        HttpPost(HttpConfigSite.Post_SendOrder, params, SendOrder);
    }

    private static onSendOrderJsonListener onJsonListener;

    public void setOnSendOrderJsonListener(onSendOrderJsonListener sendOrderJsonListener) {
        onJsonListener = sendOrderJsonListener;
    }

    public interface onSendOrderJsonListener {
        void sendOrder();
    }

    /**
     * 14+.货主再来一发
     */
    public void SendAgainOrderJson(String time, String push, String id) {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("departure_time", time);
        params.put("isPush", push);
        params.put("oid", id);
        HttpPost(HttpConfigSite.Post_SendAgainOrder, params, SendAganiOrder);
    }

    private static onSendAgainOrderJsonListener onJsonAgainListener;

    public void setOnSendAgainOrderJsonListener(onSendAgainOrderJsonListener sendOrderJsonListener) {
        onJsonAgainListener = sendOrderJsonListener;
    }

    public interface onSendAgainOrderJsonListener {
        void sendAgainOrder();
    }

    /**
     * 15.获取货主端已完成订单\进行中订单
     *
     * @param
     * @param
     * @param flag     1：进行中的订单列表 6 :已完成的订单列表
     * @param paging
     * @param pagesize
     */
    public void getStocksOrderList(String flag, String paging, String pagesize) {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("flag", flag);
        params.put("paging", paging);
        params.put("pagsize", pagesize);
        HttpPost(HttpConfigSite.Post_OrderList, params, getStocksOrderList);
    }

    /**
     * 15.1获取货主端进行中订单接口 - - getStocksOrderList(String token, String uid, String
     * flag, String paging)
     */
    private onGetOrderListListener orderListListener;

    public void setOnGetOrderListListener(onGetOrderListListener getOrderListListener) {
        this.orderListListener = getOrderListListener;
    }

    public interface onGetOrderListListener {
        void getOrderList(List<OrderListBean> orderlist);
    }

    /**
     * 17.提交意见反馈
     *
     * @param
     * @param
     * @param type
     * @param content
     */
    public void SendFeedBack(String type, String content) {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("type", type);
        params.put("content", content);
        HttpPost(HttpConfigSite.Post_FeedBack, params, FeedBack);
    }

    /**
     * 18.退出登录
     *
     * @param
     * @param
     * @param type - (1司机 2货主)
     */
    public void ExitLogin(String type) {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("type", type);
        HttpPost(HttpConfigSite.Post_ExitLogin, params, ExitLogin);
    }

    /**
     * 19.获取订单详情
     *
     * @param -   用户token验证
     * @param -   用户id
     * @param oid - 订单id
     */
    public void getOrderDetails(String oid) {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("oid", oid);
        HttpPost(HttpConfigSite.Post_GetOrderDetails, params, getOrderDetails);
    }

    private getOrderDetails orderDetails;

    public void setGetOrderDetails(getOrderDetails getorderdetails) {
        this.orderDetails = getorderdetails;
    }

    public interface getOrderDetails {
        void OrderDetails(OrderDetials_Bean orderDetials_Bean);
    }

    /**
     * 20.兑换金币
     *
     * @param
     * @param
     * @param type  - (1司机 2货主)
     * @param total - 兑换金币数
     */
    public void conversionGold(String type, String total) {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("type", type);
        params.put("total", total);
        HttpPost(HttpConfigSite.Post_ConversionGlod, params, conversionGold);
    }

    private conversionGoldListener cGoldListener;

    public void setConversionGoldListener(conversionGoldListener goldListener) {
        this.cGoldListener = goldListener;
    }

    public interface conversionGoldListener {
        void conversion();
    }

    /**
     * 21.充值
     *
     * @param
     * @param
     * @param trim    - 充值金额
     * @param paytype 1微信充值 2支付宝充值（如果是支付宝充值，还需要传type字段-(1司机 2货主)）
     */
    public void rechargeMoney(String trim, int paytype) {

        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("money", trim);
        if (paytype == 1) {
            HttpPost(HttpConfigSite.Post_WxRecharge, params, rechargeMoney);
        } else if (paytype == 2) {// 如果是支付宝充值，还需要传type字段-(1司机 2货主)
            params.put("type", "2");
            HttpPost(HttpConfigSite.Post_AliRecharge, params, rechargeMoney);
        }
    }

    private rechargeMoneyListener moneyListener;

    public void setRechargeMoneyListener(rechargeMoneyListener reListener) {
        this.moneyListener = reListener;
    }

    public interface rechargeMoneyListener {
        void onRechargeMoney(String Data);
    }

    /**
     * 22.设置提现密码
     *
     * @param
     * @param
     * @param type    - (1司机 2货主)
     * @param dealpwd - 支付密码
     */
    public void submitPassword(String type, String dealpwd) {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("type", type);
        params.put("dealpwd", dealpwd);
        HttpPost(HttpConfigSite.Post_SubmitPassword, params, submitPassword);
    }

    private submitPasswordListener suListener;

    public void setSubmitPasswordListener(submitPasswordListener passwordListener) {
        this.suListener = passwordListener;
    }

    public interface submitPasswordListener {
        void onSubmitPassword();
    }

    /**
     * 23.获取银行卡列表
     */
    public void getBankList() {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("type", "2");// (1司机 2货主)
        HttpPost(HttpConfigSite.Post_GetBankList, params, getBankList);
    }

    /**
     * 24.添加银行卡
     *
     * @param card_no
     * @param card_bank
     * @param card_name
     */
    public void addBankCard(String card_no, String card_bank, String card_name) {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("type", "2");// (1司机 2货主)
        params.put("card_no", card_no);
        params.put("card_bank", card_bank);
        params.put("card_name", card_name);
        HttpPost(HttpConfigSite.Post_AddBankCard, params, addBankCard);
    }

    private getBankListListener gBankListListener;

    public void setGetBankListListener(getBankListListener bankListListener) {
        this.gBankListListener = bankListListener;
    }

    public interface getBankListListener {
        void BankList(List<BankListBean> banckList);

        void AddBank();
    }

    /**
     * 25.提现
     *
     * @param bankCardId
     * @param money
     * @param pass
     */
    public void submitWithdraw(String bankCardId, String money, String pass) {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("type", "2");// (1司机 2货主)
        params.put("card_id", bankCardId);
        params.put("dealpwd", pass);
        params.put("total", money);
        HttpPost(HttpConfigSite.Post_SubmitWithdraw, params, submitWithdraw);
    }

    private submitWithdrawListener passwordListener;

    public void setSubmitWithdrawListener(submitWithdrawListener sListener) {
        this.passwordListener = sListener;
    }

    public interface submitWithdrawListener {
        void submitWithdraw();
    }

    /**
     * 26.解除银行卡
     *
     * @param bankCardId - 银行卡id
     * @param pass       - 支付密码
     */
    public void deleteBankCard(String bankCardId, String pass) {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("type", "2");// (1司机 2货主)
        params.put("id", bankCardId);
        params.put("dealpwd", pass);
        HttpPost(HttpConfigSite.Post_DeleteBankCard, params, deleteBankCard);
    }

    /**
     * 27.获取司机信息
     *
     * @param id - 司机id
     */
    public void getDriverInfo(String id) {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("driver_id", id);
        HttpPost(HttpConfigSite.Post_GetDriverInfo, params, getDriverInfo);
    }

    private onGetDriverInfo onDriverInfo;

    public void setOnGetDriverInfo(onGetDriverInfo getDriverInfo) {
        this.onDriverInfo = getDriverInfo;
    }

    public interface onGetDriverInfo {
        void DriverInfo(DriverInfoBean driverInfoBean);
    }

    /**
     * 28.选择司机
     *
     * @param orderid  - 订单id
     * @param driverId - 司机id
     */
    public void chooseDriver(String orderid, String driverId) {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("orderId", orderid);
        params.put("driverId", driverId);
        HttpPost(HttpConfigSite.Post_ChooseDriver, params, chooseDriver);
    }

    private onChooseDriver onChooseDriver;

    public void setOnChooseDriver(onChooseDriver chooseDriver) {
        this.onChooseDriver = chooseDriver;
    }

    public interface onChooseDriver {
        void onChoose();
    }

    /**
     * 29.更新版本
     */
    public void updataVersion() {

        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("ver", AppUtil.getVersionName(mContext));// 当前版本号 re 当前版本名称
        params.put("type", "2");// (1司机 2货主)

        HttpPost(HttpConfigSite.Post_UpdataVersion, params, updataVersion);
    }

    private onUpdataVersion onUpdataVersion;

    public void setOnUpdataVersion(onUpdataVersion updataVersion) {
        this.onUpdataVersion = updataVersion;
    }

    public interface onUpdataVersion {
        void getVersion(UpdataVersionBean updataVersionBean);
    }

    /**
     * 30.获取所有司机的位置
     */
    // public void getAllDriverLocation() {
    // RequestParams params = new RequestParams();
    // params.put("token",Token);
    // params.put("userId",Uid);
    // HttpPost(HttpConfigSite.Post_GetAllDriver, params, getAllDriver);
    // }

    /**
     * 30.获取货主当前位置附近的司机坐标
     *
     * @param lat
     * @param lng
     */
    public void upLoadNearbyDriver(double lat, double lng) {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("lat", lat);
        params.put("lng", lng);
        HttpPost(HttpConfigSite.Post_UpLoadNearbyDrivers, params, UpLoadNearbyDrivers);
    }

    private onGetAllDriverListener allDriverListener;

    public void setOnGetAllDriverListener(onGetAllDriverListener getAllDriverListener) {
        this.allDriverListener = getAllDriverListener;
    }

    public interface onGetAllDriverListener {
        /**
         * @param driverlocationlist
         */
        void getAllDriver(List<AllDriverLocationBean> driverlocationlist);
    }

    /**
     * 31.货主评论司机
     *
     * @param star
     * @param content
     * @param
     * @param oid
     */
    public void OwnerCommentDriver(String star, String content, String oid) {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("star", star);
        params.put("content", content);
        params.put("oid", oid);
        L.i("货主评价司机==" + params.toString());
        HttpPost(HttpConfigSite.Post_OwenrCommentDriver, params, owenrCommmentDriver);
    }

    private onCommentListener commentListener;

    public void setOnCommentListener(onCommentListener commentListener) {
        this.commentListener = commentListener;
    }

    public interface onCommentListener {
        void onComment();
    }

    /**
     * 32.货主支付运费给司机---微信支付（获取微信支付信息）
     *
     * @param orderid - 订单id
     * @param type    - 1-余额支付 3-微信支付
     * @param pass    - MD5加密的支付密码
     */
    public void OrderPayFreight(String orderid, String type, String pass) {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("oid", orderid);

        if (type.toString().equals("1")) {
            params.put("pwd", pass);

            HttpPost(HttpConfigSite.Post_PayMentfreights, params, payMentfreight);
        } else if (type.toString().equals("3")) {
            HttpPost(HttpConfigSite.Post_PayMentfreight, params, payMentfreight);
        }
    }

    /**
     * 33.货主取消订单时先获取还有几次取消
     */
    public void getOwnerCancle() {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        HttpPost(HttpConfigSite.Post_CancleOrderNum, params, cancleOrder);
    }

    /**
     * 34.取消订单
     *
     * @param orderId
     */
    public void cancleOrder(String orderId) {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("orderId", orderId);
        HttpPost(HttpConfigSite.Post_CancleOrder, params, cancleSuccess);
    }

    private onGetOwnerCancleListener cancleListener;

    public void setOnGetOwnerCancleListener(onGetOwnerCancleListener ownerCancleListener) {
        this.cancleListener = ownerCancleListener;
    }

    public interface onGetOwnerCancleListener {
        void CancleNum(String num);

        void CancleSuccess();
    }

    /**
     * 35.货主发单扣除金币
     */
    public void getOwnerGold() {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        HttpPost(HttpConfigSite.Post_GetOwnerGold, params, getOwnerGold);
    }

    private onOwnerGoldListener ownerGoldListener;

    public void setOnWonerGoldListener(onOwnerGoldListener goldListener) {
        this.ownerGoldListener = goldListener;
    }

    public interface onOwnerGoldListener {
        void onGold(OwnerGoldBean ownerGoldBean);
    }

    /**
     * 36.获取分享内容
     *
     * @param price - 运费
     * @param oid   - 订单id
     */
    public void getShareContent(String price, String oid) {
        RequestParams params = new RequestParams();
        params.put("type", "2");// 1司机2货主
        params.put("total", price);
        params.put("userId", Uid);
        params.put("oid", oid);
        HttpPost(HttpConfigSite.Post_GetShare, params, getShare);
    }

    private onGetShareContentListener shareContentListener;

    public void setOnGetShareContentListener(onGetShareContentListener getShareContentListener) {
        this.shareContentListener = getShareContentListener;
    }

    public interface onGetShareContentListener {
        void ShareContent(ShareContentBean shareContentBean);
    }

    /**
     * 37.分享成功后领取金币奖励
     *
     * @param orderId
     * @param platform
     */
    public void shareSuccessGetGold(String orderId, String platform) {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("oid", orderId);
        params.put("type", "2");// 1.司机端 2.货主端
        params.put("platform", platform);
        HttpPost(HttpConfigSite.Post_ShareSuccessGetGold, params, shareSuccessGold);
    }

    /**
     * 38.获取评论接口
     */
    public void getCommentList() {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("type", "2");// (1司机 2货主)
        HttpPost(HttpConfigSite.Post_GetCommentList, params, getCommentList);
    }

    private onGetCommentListListener commentListListener;

    public void setOnGetCommentListenenr(onGetCommentListListener getCommentListListener) {
        this.commentListListener = getCommentListListener;
    }

    public interface onGetCommentListListener {
        void getComment(List<CommentBean> commentList);
    }

    /**
     * 39.用户消费记录查询
     *
     * @param page     - 页数
     * @param pagesize - 每页返回条数
     * @param action   - 1= 金币记录列表 2=账户记录列表
     */
    public void getExpenseCalendar(int page, String pagesize, String action) {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("type", "2");// (1司机 2货主)
        params.put("paging", page);//
        params.put("pagsize", pagesize);
        params.put("kind", action);// 1= 金币记录列表 2=账户记录列表
        HttpPost(HttpConfigSite.Post_ExpenseCalendar, params, getExpenseCalendar);

    }

    private onGetExpenseCalendarListener expenseCalendarListener;

    public void setOnGetExpenseCalendarListener(onGetExpenseCalendarListener getExpenseCalendarListener) {
        this.expenseCalendarListener = getExpenseCalendarListener;
    }

    public interface onGetExpenseCalendarListener {
        void getExpenseCalendar(List<Expense_CalendarBean> calendarBeansList);
    }

    /**
     * 40.在用余额支付运费的时候要获取货主有没有设置支付密码
     */
    public void getOwnerPayPow() {
        RequestParams params = new RequestParams();
        params.put("token", Token);
        params.put("userId", Uid);
        params.put("type", "2");// type String (1司机 2货主)
        HttpPost(HttpConfigSite.Post_GetPayPow, params, getPayPow);
    }

    private onGetOwnerPayPowListener ownerPayPowListener;

    public void setOnGetOwnerPayPowListener(onGetOwnerPayPowListener getOwnerPayPowListener) {
        this.ownerPayPowListener = getOwnerPayPowListener;
    }

    public interface onGetOwnerPayPowListener {
        void onPayPow(String string);
    }

    /**
     * @param bean
     * @param requestcode
     */
    @SuppressWarnings("static-access")
    @Override
    public void HttpSuccess(Object bean, int requestcode) {
        L.i("成功返回的数据是==" + bean.toString());
        if (mContext == null) {
            return;
        }
        if (handler == null) {
            return;
        }
        handler.sendEmptyMessage(0);
        if (bean == null || bean.equals("")) {
            T.showLong(mContext, "数据为空,请重试!");
            return;
        }
        try {
            JSONObject object = new JSONObject(bean.toString());
            if (object.getInt("Code") == 1) {// code == 1 请求成功
                Gson gson = new Gson();
                switch (requestcode) {
                    case GetVerifyCode:// 获取验证码
                        T.showShort(mContext, object.getString("Message"));
                        codeListener.getCode();
                        break;
                    case VerifyCode:// 验证验证码
                        T.showShort(mContext, object.getString("Message"));
                        codeListener.getRegister();
                        break;
                    case UserRegister:// 用户注册
                        T.showShort(mContext, object.getString("Message"));
                        registerlistener.Register();
                        break;
                    case UserResetPassword:// 用户重置密码
                        T.showShort(mContext, object.getString("Message"));
                        resetPasswordListener.ResetPassword();
                        break;

                    case UserRefreshPersonInfo:// 用户修改信息
                        T.showShort(mContext, object.getString("Message"));
                        userRefreshPersonInfo.refreshInfo();
                        break;
                    case getPersonInfo:
                        JSONObject data = object.getJSONObject("Date");
                        userInfo.getInfo(data.optString("name"), data.optString("sex"), data.optString("company"));

                        break;
                    case UserLogin:// 用户登录
                        uListener.userLogin(gson.fromJson(object.getString("Date"), UserLoginBean.class));
                        break;
                    case GetHomeData:// 获取首页数据
                        JSONObject jsonObject = new JSONObject(object.getString("Date"));
                        JSONArray jsonArray = new JSONArray(jsonObject.getString("pushmessageList"));
                        HomeBean homeBean = new HomeBean();
                        List<Home_PushMessageListBean> pushmessagelist = new ArrayList<Home_PushMessageListBean>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = new JSONObject(jsonArray.getString(i).toString());
                            Home_PushMessageListBean pushmessagebean = new Home_PushMessageListBean();
                            pushmessagebean.setContent(jsonObject2.getString("content"));
                            pushmessagebean.setImg(jsonObject2.getString("img"));
//						pushmessagebean.setId(jsonObject2.getString("id"));
//						pushmessagebean.setType(jsonObject2.getString("type"));
//						pushmessagebean.setLink(jsonObject2.getString("link"));
//						pushmessagebean.setStatus(jsonObject2.getString("status"));
                            pushmessagelist.add(pushmessagebean);
                        }
                        JSONArray jsonArray2 = new JSONArray(jsonObject.getString("noticeList"));
                        List<Home_NoticeListBean> noticelist = new ArrayList<Home_NoticeListBean>();
                        for (int i = 0; i < jsonArray2.length(); i++) {
                            JSONObject jsonObject2 = new JSONObject(jsonArray2.getString(i).toString());
                            Home_NoticeListBean noticeListBean = new Home_NoticeListBean();
                            noticeListBean.setContent(jsonObject2.getString("content"));
                            noticeListBean.setId(jsonObject2.getString("id"));
                            noticeListBean.setTitle(jsonObject2.getString("title"));
                            noticelist.add(noticeListBean);
                        }

                        JSONArray jsonArray3 = new JSONArray(jsonObject.getString("advertiseList"));
                        List<Home_AdvertiseListBean> advertiselist = new ArrayList<Home_AdvertiseListBean>();
                        for (int i = 0; i < jsonArray3.length(); i++) {
                            JSONObject jsonObject2 = new JSONObject(jsonArray3.getString(i).toString());
                            Home_AdvertiseListBean advertisebean = new Home_AdvertiseListBean();
                            advertisebean.setAdLink(jsonObject2.getString("adLink"));
                            advertisebean.setAdPicUrl(jsonObject2.getString("adPicUrl"));
                            advertisebean.setId(jsonObject2.getString("id"));
                            advertisebean.setRemarks(jsonObject2.getString("remarks"));
                            advertisebean.setSite(jsonObject2.getString("site"));
                            advertiselist.add(advertisebean);
                        }
                        homeBean.setPushmessageList(pushmessagelist);
                        homeBean.setNoticeList(noticelist);
                        homeBean.setAdvertiseList(advertiselist);
                        dataListener.getHomeData(homeBean);
                        break;

                    //蒋米兰
                    case GetHomeDataLoadMore:
                        JSONObject json = new JSONObject(object.getString("Date"));
                        Home_PushMessageListMoreBean pushMoreBean = new Home_PushMessageListMoreBean();
                        List<Home_PushMessageListBean> pushmessagemorelist = new ArrayList<Home_PushMessageListBean>();
                        if (Integer.parseInt(json.getString("count")) > 0) {
                            if (!json.getString("pushmessageList").toString().equals("[]")) {
                                JSONArray jsonArray1 = new JSONArray(json.getString("pushmessageList"));
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject jsonObject2 = new JSONObject(jsonArray1.getString(i).toString());
                                    Home_PushMessageListBean pushmessagemorebean = new Home_PushMessageListBean();
                                    pushmessagemorebean.setContent(jsonObject2.getString("content"));
                                    pushmessagemorebean.setImg(jsonObject2.getString("img"));
                                    pushmessagemorelist.add(pushmessagemorebean);
                                }
                                pushMoreBean.setPushmessageList(pushmessagemorelist);
                                pushMoreBean.setCount(json.getString("count"));
                            } else {
                                T.showShort(mContext, "没有更多数据了!");
                            }
                        } else if (Integer.parseInt(json.getString("count")) == 0) {
                            T.showShort(mContext, object.getString("Message"));
                            pushmessagemorelist = null;
                        }
                        moreDataListener.getHomeMoreData(pushMoreBean);
                        break;
                    case VerifyImage:// 上传验证图片
                        T.showShort(mContext, object.getString("Message"));
                        if (object.getString("Date") != null || !object.getString("Date").equals("")) {
                            // 上传图片成功后更新一下状态值--这里返回的状态值status = 1
                            ZDSharedPreferences.getInstance(mContext).setUserStatus(object.getString("Date"));
                        }
                        loadverifylistener.uploadVerify();
                        break;
                    case GetUserStatus:// 获取用户状态
                        if (object.getString("Date") != null || !object.getString("Date").equals("")) {
                            // 获取用户状态值后随时保存状态值
                            ZDSharedPreferences.getInstance(mContext).setUserStatus(object.getString("Date"));
                            getuserstatuslistener.getUserStatus(object.getString("Date"));
                        }
                        break;
                    case UpdataUserHead:// 上传用户头像
                        T.showShort(mContext, object.getString("Message"));
                        ZDSharedPreferences.getInstance(mContext).setUserHead(object.getString("Date"));
                        upuserheadlistener.updataUserHead();
                        break;
                    case GetMessageList:// 获取消息列表
                        JSONObject jsonObject2 = new JSONObject(object.getString("Date"));
                        List<MessageListBean> messagelist = new ArrayList<MessageListBean>();
                        if (Integer.parseInt(jsonObject2.getString("cnt")) > 0) {
                            if (!jsonObject2.getString("list").toString().equals("[]")) {
                                JSONArray jsonArray4 = new JSONArray(jsonObject2.getString("list"));
                                for (int i = 0; i < jsonArray4.length(); i++) {
                                    JSONObject jsonObject3 = new JSONObject(jsonArray4.getString(i));
                                    MessageListBean messagelistbean = new MessageListBean();
                                    messagelistbean.setContent(jsonObject3.getString("content"));
                                    messagelistbean.setCreated_on(jsonObject3.getString("created_on"));
                                    messagelistbean.setId(jsonObject3.getString("id"));
                                    messagelistbean.setLink_id(jsonObject3.getString("Link_id"));
                                    messagelistbean.setTitle(jsonObject3.getString("title"));
                                    messagelistbean.setType(jsonObject3.getString("type"));
                                    messagelistbean.setSelector(false);
                                    messagelistbean.setDelete(Main_MessageActivity.isDelete);
                                    messagelist.add(messagelistbean);
                                }
                            } else {
                                T.showShort(mContext, "没有更多数据了!");
                            }

                        } else if (Integer.parseInt(jsonObject2.getString("cnt")) == 0) {
                            T.showShort(mContext, object.getString("Message"));
                            messagelist = null;
                        }
                        messageListDataListener.getMeesagList(messagelist);
                        break;
                    case DeleteMessage:// 删除消息
                        T.showShort(mContext, object.getString("Message"));
                        deletemessagelistener.deleteMessageSuccess();
                        break;
                    case GetUserAccount:// 获取用户账户信息
                        userAccountDataListener
                                .getUserAccount(gson.fromJson(object.getString("Date"), UserAccountDataBean.class));
                        break;
                    case GetUserGold:// 获取用户金币
                        userGoldListener.getUserGold(gson.fromJson(object.getString("Date"), UserGoldBean.class));
                        break;
                    case SendOrder:

                        T.showShort(mContext, object.getString("Message"));
                        onJsonListener.sendOrder();

                        break;
                    case SendAganiOrder:
                        T.showShort(mContext, object.getString("Message"));
                        onJsonAgainListener.sendAgainOrder();

                        break;
                    case getStocksOrderList:// 获取货主已完成订单列表
                        List<OrderListBean> orderlist = null;
                        if (object.getString("Date").equals("") || object.getString("Date").equals("[]")) {
                            if (orderListListener != null) {
                                orderListListener.getOrderList(null);
                            }
                            return;
                        }
                        if (!object.getString("Date").equals("")) {
                            JSONArray array = new JSONArray(object.getString("Date"));
                            orderlist = new ArrayList<OrderListBean>();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object2 = new JSONObject(array.getString(i));
                                OrderListBean orderlistbean = new OrderListBean();
                                orderlistbean.setDeparture_time(object2.getString("departure_time"));
                                orderlistbean.setEndCity(object2.getString("endCity"));
                                orderlistbean.setGoodstype(object2.getString("goodstype"));
                                orderlistbean.setId(object2.getString("id"));
                                orderlistbean.setPrice(object2.getString("price"));
                                orderlistbean.setRemarks(object2.getString("remarks"));
                                orderlistbean.setStarCity(object2.getString("starCity"));
                                orderlistbean.setStatus(object2.getString("status"));
                                orderlistbean.setDriver(object2.getString("driver"));
                                orderlistbean.setIs_commen(object2.getString("is_commen"));
                                orderlistbean.setEstimate_price(object2.getString("estimate_price"));
                                orderlist.add(orderlistbean);
                            }
                        }
                        if (orderListListener != null) {
                            orderListListener.getOrderList(orderlist);
                        }
                        break;
                    case UserModifyPwd:
                        T.showLong(mContext, object.getString("Message"));

                        m.modifyBack();
                        break;
                    case FeedBack:// 意见反馈
                        T.showLong(mContext, object.getString("Message"));
                        ((Setting_OpinionActivity) mContext).finish();
                        break;
                    case ExitLogin:// 退出登录
                        new UserExitUtils(mContext).forcedExit();
                        break;
                    case getOrderDetails:// 获取订单详情
                        JSONObject array2 = new JSONObject(object.getString("Date").toString());
                        OrderDetials_Bean orderDetials_Bean = new OrderDetials_Bean();
                        List<OrderDetials_Driverinfo_Bean> userinoflist = new ArrayList<OrderDetials_Driverinfo_Bean>();
                        JSONArray array = new JSONArray(array2.getString("driverinfo"));
                        for (int i = 0; i < (array.length()/* >5?5:array.length() */); i++) {
                            OrderDetials_Driverinfo_Bean userbean = new OrderDetials_Driverinfo_Bean();
                            JSONObject jsonObject3 = new JSONObject(array.getString(i).toString());
                            userbean.setId(jsonObject3.getString("id"));
                            userbean.setLat(jsonObject3.getString("lat"));
                            userbean.setLevel(jsonObject3.getString("level"));
                            userbean.setLng(jsonObject3.getString("lng"));
                            userbean.setMobile(jsonObject3.getString("mobile"));
                            userbean.setName(jsonObject3.getString("name"));
                            userbean.setPrice(jsonObject3.getString("price"));
                            userbean.setScore(jsonObject3.getString("score"));
                            userinoflist.add(userbean);
                        }
                        orderDetials_Bean.setDriverinoflist(userinoflist);
                        orderDetials_Bean.setOrderinfolist(
                                gson.fromJson(array2.getString("orderinfo"), OrderDetials_Orderinfo_Bean.class));
                        orderDetials_Bean.setReleaseinfo(
                                gson.fromJson(array2.getString("userinfo"), OrderDetials_Releaseinfo_Bean.class));
                        orderDetails.OrderDetails(orderDetials_Bean);

                        break;

                    case conversionGold:// 兑换金币
                        T.showShort(mContext, object.getString("Message"));
                        cGoldListener.conversion();
                        break;
                    case rechargeMoney:// 充值
                        moneyListener.onRechargeMoney(object.getString("Date").toString());
                        break;
                    case submitPassword:// 设置提现密码
                        suListener.onSubmitPassword();
                        break;
                    case getBankList:// 获取银行卡列表
                        List<BankListBean> banckList = new ArrayList<BankListBean>();
                        if (object.getString("Date").toString().equals("")) {
                            gBankListListener.BankList(null);
                            return;
                        }
                        JSONArray jsonArray4 = new JSONArray(object.getString("Date").toString());
                        for (int i = 0; i < jsonArray4.length(); i++) {
                            BankListBean banklist = new BankListBean();
                            JSONObject jsonObject3 = new JSONObject(jsonArray4.getString(i).toString());
                            banklist.setCard_bank(jsonObject3.getString("card_bank"));
                            banklist.setCard_name(jsonObject3.getString("card_name"));
                            banklist.setCard_no(jsonObject3.getString("card_no"));
                            banklist.setId(jsonObject3.getString("id"));
                            banckList.add(banklist);
                        }
                        gBankListListener.BankList(banckList);
                        break;
                    case addBankCard:// 添加银行卡
                        T.showShort(mContext, object.getString("Message"));
                        gBankListListener.AddBank();
                        break;
                    case submitWithdraw:// 提现
                        passwordListener.submitWithdraw();
                        T.showShort(mContext, object.getString("Message"));
                        break;
                    case deleteBankCard:// 删除银行卡
                        passwordListener.submitWithdraw();
                        T.showShort(mContext, object.getString("Message"));
                        break;
                    case getDriverInfo:// 获取用户信息
                        JSONObject jsonObject3 = new JSONObject(object.getString("Date"));
                        DriverInfoBean driverInfoBean = new DriverInfoBean();
                        driverInfoBean.setDriver_id(jsonObject3.getString("driver_id"));
                        driverInfoBean.setLevel(jsonObject3.getString("level"));
                        driverInfoBean.setMobile(jsonObject3.getString("mobile"));
                        driverInfoBean.setName(jsonObject3.getString("name"));
                        driverInfoBean.setPic(jsonObject3.getString("pic"));
                        driverInfoBean.setPlate_num(jsonObject3.getString("plate_num"));
                        driverInfoBean.setRate(jsonObject3.getString("rate"));
                        driverInfoBean.setScore(jsonObject3.getString("score"));
                        driverInfoBean.setSuccess_num(jsonObject3.getString("success_num"));
                        driverInfoBean.setVehicle_id(jsonObject3.getString("vehicle_id"));
                        if (jsonObject3.getString("commentlist").toString().equals("[]")) {
                            driverInfoBean.setCommentlist(null);
                        } else {
                            List<DriverInfo_CommentBean> driverInfolist = new ArrayList<DriverInfo_CommentBean>();
                            JSONArray array3 = new JSONArray(jsonObject3.getString("commentlist"));
                            for (int i = 0; i < array3.length(); i++) {
                                JSONObject jsonObject4 = new JSONObject(array3.getString(i).toString());
                                DriverInfo_CommentBean commentBean = new DriverInfo_CommentBean();
                                commentBean.setCid(jsonObject4.getString("cid"));
                                commentBean.setComment(jsonObject4.getString("comment"));
                                commentBean.setCreated_on(jsonObject4.getString("created_on"));
                                commentBean.setMobile(jsonObject4.getString("mobile"));
                                commentBean.setPic(jsonObject4.getString("pic"));
                                commentBean.setScore(jsonObject4.getString("score"));
                                driverInfolist.add(commentBean);
                            }
                            driverInfoBean.setCommentlist(driverInfolist);
                        }
                        onDriverInfo.DriverInfo(driverInfoBean);
                        break;
                    case chooseDriver:// 选择司机

                        onChooseDriver.onChoose();
                        break;
                    case updataVersion:// 更新版本
                        onUpdataVersion.getVersion(gson.fromJson(object.getString("Date"), UpdataVersionBean.class));
                        break;
                    case getAllDriver:// 获取所有司机位置
                        List<AllDriverLocationBean> driverlocationlist = new ArrayList<AllDriverLocationBean>();
                        JSONArray jsonArray5 = new JSONArray(object.getString("Date"));
                        for (int i = 0; i < jsonArray5.length(); i++) {
                            JSONObject jsonObject4 = new JSONObject(jsonArray5.getString(i));
                            AllDriverLocationBean locationbean = new AllDriverLocationBean();
                            locationbean.setLat(jsonObject4.getDouble("lat"));
                            locationbean.setLng(jsonObject4.getDouble("lng"));
                            locationbean.setName(jsonObject4.getString("name"));
                            driverlocationlist.add(locationbean);
                        }
                        allDriverListener.getAllDriver(driverlocationlist);
                        break;
                    case owenrCommmentDriver:// 货主评论司机
                        commentListener.onComment();
                        T.showShort(mContext, object.getString("Message"));
                        break;
                    case payMentfreight:// 获取微信支付返回的信息
                        moneyListener.onRechargeMoney(object.getString("Date").toString());
                        break;
                    case cancleOrder:// 货主取消订单前先获取还有几次取消机会
                        cancleListener.CancleNum(object.getString("Date").toString());
                        break;
                    case cancleSuccess:// 取消订单成功
                        T.showShort(mContext, object.getString("Message"));
                        cancleListener.CancleSuccess();
                        break;
                    case getOwnerGold:// 货主发单扣除金币
                        ownerGoldListener.onGold(gson.fromJson(object.getString("Date"), OwnerGoldBean.class));
                        break;
                    case getShare:// 获取分享内容
                        Log.e("shareContentHuozhu", object + "");
                        shareContentListener.ShareContent(gson.fromJson(object.getString("Date"), ShareContentBean.class));
                        break;
                    case shareSuccessGold:// 分享成功后领取奖励金币
                        T.showShort(mContext, object.getString("Message"));
                        break;
                    case getCommentList:// 获取评论接口
                        List<CommentBean> commentList = null;
                        if (object.getString("Date").equals("") || object.getString("Date").equals("[]")) {
                            commentListListener.getComment(commentList);
                            return;
                        }
                        commentList = new ArrayList<CommentBean>();
                        JSONArray array3 = new JSONArray(object.getString("Date"));
                        for (int i = 0; i < array3.length(); i++) {
                            JSONObject jsonObject4 = new JSONObject(array3.getString(i));
                            CommentBean comment = new CommentBean();
                            comment.setCid(jsonObject4.getString("cid"));
                            comment.setComment(jsonObject4.getString("comment"));
                            comment.setCommented_mobile(jsonObject4.getString("commented_mobile"));
                            comment.setCreated_on(jsonObject4.getString("created_on"));
                            comment.setPic(jsonObject4.getString("pic"));
                            comment.setScore(jsonObject4.getString("score"));
                            commentList.add(comment);
                        }
                        commentListListener.getComment(commentList);
                        break;
                    case getExpenseCalendar:// 用户消费记录

                        List<Expense_CalendarBean> calendarBeansList = null;
                        if (object.getString("Date").equals("") || object.getString("Date").equals("[]")) {
                            expenseCalendarListener.getExpenseCalendar(calendarBeansList);
                            return;
                        }
                        JSONArray array4 = new JSONArray(object.getString("Date"));
                        calendarBeansList = new ArrayList<Expense_CalendarBean>();
                        for (int i = 0; i < array4.length(); i++) {
                            JSONObject jsonObject4 = new JSONObject(array4.getString(i));
                            Expense_CalendarBean calendarBean = new Expense_CalendarBean();
                            calendarBean.setCreated_on(jsonObject4.getString("created_on"));
                            calendarBean.setGolds(jsonObject4.getString("golds"));
                            calendarBean.setId(jsonObject4.getInt("id"));
                            calendarBean.setStatus(jsonObject4.getString("status"));
                            calendarBean.setType(jsonObject4.getString("type"));
                            calendarBeansList.add(calendarBean);
                        }
                        expenseCalendarListener.getExpenseCalendar(calendarBeansList);
                        break;
                    case getPayPow:// 货主是否设置交易密码
                        ownerPayPowListener.onPayPow(object.getString("Date"));
                        break;
                    case UpLoadNearbyDrivers://获取附近司机的位置坐标
                        List<AllDriverLocationBean> driverlocationlists = new ArrayList<AllDriverLocationBean>();
                        JSONArray jsonArray6 = new JSONArray(object.getString("Date"));
                        for (int i = 0; i < jsonArray6.length(); i++) {
                            JSONObject jsonObject4 = new JSONObject(jsonArray6.getString(i));
                            AllDriverLocationBean locationbean = new AllDriverLocationBean();
                            locationbean.setLat(jsonObject4.getDouble("lat"));
                            locationbean.setLng(jsonObject4.getDouble("lng"));
                            locationbean.setName(jsonObject4.getString("name"));
                            driverlocationlists.add(locationbean);
                        }
                        allDriverListener.getAllDriver(driverlocationlists);
                        break;
                    default:
                        break;

                }
            } else if (object.getInt("Code") == 0) {// code == 0 请求成功但是返回内容有问题
                switch (requestcode) {
//                    case VerifyCode://验证码不正确
//                        DialogUtils.showDialogBack(mContext, true, object.getString("Message"), "对不起," + object.getString("Message") + "。\n请重新输入", "返回");
//                        break;
//                    case UserResetPassword :
//                        DialogUtils.showDialogBack(mContext, true, object.getString("Message"), "对不起," + object.getString("Message") + "。\n请重新输入", "返回");
//                        break;
//
                    default:
                        T.showShort(mContext, object.getString("Message"));
                        break;
                }
            } else if (object.getInt("Code") == 2) {// code == 2
                // 请求成功但是Token验证失败需要退出当前账户
                T.showShort(mContext, object.getString("Message"));
                new UserExitUtils(mContext).forcedExit();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */

    /**
     * Get请求
     *
     * @param postGetCode    - 请求url地址
     * @param params         - 参数
     * @param getverifycode2 - 请求回调的code
     */
    @SuppressWarnings({"static-access", "unused"})
    private void HttpGet(String postGetCode, RequestParams params, int getverifycode2) {
        new HttpRestClient().get(mContext, this, postGetCode, params, getverifycode2);
    }

    /**
     *
     */


    /**
     * Post请求
     *
     * @param postGetCode    - 请求url地址
     * @param params         - 参数对象
     * @param getverifycode2 - 请求回调的code
     */
    @SuppressWarnings("static-access")
    private void HttpPost(String postGetCode, RequestParams params, int getverifycode2) {
        if (mContext == null) {
            return;
        }
        new HttpRestClient().post(mContext, this, postGetCode, params, getverifycode2);
    }

    @Override
    public void HttpCancel() {
        handler.sendEmptyMessage(0);
    }

    @Override
    public void HttpFail(int code, Object responseString, int requestcode) {
        if (mContext != null)
            if (responseString == null) {
                T.showShort(mContext, "网络链接失败,请检查网络");
            } else {
                T.showShort(mContext, "" + responseString);
            }
        L.i("联网错误返回的code==" + code + "  responseString===" + responseString + "  requestcode==" + requestcode);
        handler.sendEmptyMessage(0);
    }

    @Override
    public void HttpStart() {
        handler.sendEmptyMessage(1);
    }

    @Override
    public void HttpRetry() {
        handler.sendEmptyMessage(1);
    }

    @Override
    public void HttpProgress(long bytesWritten, long totalSize) {
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    if (popWindow != null) {
                        popWindow.clossPop();
                        popWindow = null;
                    }
                    break;
                case 1:
                    if (mContext != null) {
                        if (popWindow != null) {
                            popWindow.showPop();
                        } else {
                            popWindow = new Http_Upload_PopWindow(mContext);
                            popWindow.showPop();
                        }
                    }
                    break;
                default:
                    break;
            }
        }

        ;
    };
}