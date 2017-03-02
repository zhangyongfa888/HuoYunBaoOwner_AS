package zhidanhyb.huozhu.HttpRequest;

/**
 * Http配置地址
 * 
 * @author lxj
 *
 */
public class HttpConfigSite {

	// 测试基地址
	public static final String PostUrl = "http://139.196.110.242:80/didilogistics/index.php";
	// 正式基地址
//	 public static final String PostUrl ="http://121.41.81.179:8080/didilogistics/index.php";

	//后台上传图片的测试基地址（广告图片的地址）
//	public static final String Base_ImageView_url = "http://139.196.110.242:80/transport/";

	//后台上传图片的正式基地址（广告图片的地址）
	public static final String Base_ImageView_url = "http://121.41.81.179:8080/";

	// 请求延时
	public static final int TimeOut = 20000;

	// 获取验证码
	public static final String Post_Get_Code = "/commonapi/getidentify";

	// 验证码验证
	public static final String Post_Code_Verify = "/commonapi/check_identify";

	// 用户注册
	public static final String Post_User_Register = "/releaserinfoapi/register";

	// 用户个人信息获取

	public static final String Post_User_PersonInfo = "/releaserinfoapi/getInfo";
	// 用户个人信息修改
	public static final String Post_User_UpdatePersonInfo = "/releaserinfoapi/updateInfo";

	// 忘记密码获取验证码
	public static final String Post_User_ForgetPass_GetCode = "/commonapi/getResetIdentify";

	// 忘记密码验证验证码
	public static final String Post_User_ForgetPass_Code_Verify = "/commonapi/check_resetIdentify";

	// 用户重置密码
	public static final String Post_User_ResetPassword = "/commonapi/resetPwd";

	// 用户登录
	public static final String Post_User_Login = "/releaserinfoapi/login";

	// 获取首页数据
	public static final String Post_Home_Data = "/releaserinfoapi/homeInfo";

	//首页公告列表的加载更多
	public static final String Post_Home_Data_LoadMore ="/releaserinfoapi/get_more_news";

	// 上传验证图片
	public static final String Post_Verify_Image = "/releaservalidationapi/releaser_validate";

	// 获取用户状态
	public static final String post_Get_User_Status = "/commonapi/getStatus";

	// 上传用户头像
	public static final String Post_UpdataUserHead = "/commonapi/userPic";

	// 获取消息列表
	public static final String Post_GetMessageList = "/messageapi/getList";

	// 删除消息
	public static final String Post_Delete_Message = "/messageapi/deletemsg";

	// 获取用户账户信息
	public static final String Post_GetUser_Account = "/releasermyinfo/accountinfo";

	// 获取用户金币
	public static final String Post_GetUser_Gold = "/commonapi/myinfo";

	// 货主发货
	public static final String Post_SendOrder = "/releaserorder/newOrder";
	public static final String Post_SendAgainOrder = "/releaserorder/addOrdermore";

	// 获取订单列表
	public static final String Post_OrderList = "/releaserorder/orderList";

	// 意见反馈
	public static final String Post_FeedBack = "/commonapi/feedback";

	// 退出登录
	public static final String Post_ExitLogin = "/commonapi/logout";

	// 获取订单详情
	public static final String Post_GetOrderDetails = "/releaserorder/orderdetail";

	// 兑换金币
	public static final String Post_ConversionGlod = "/exchangeapi/index";

	// 微信充值
	public static final String Post_WxRecharge = "/wxpayapi/wxrecharge";
	// 支付宝充值
	public static final String Post_AliRecharge = "/alipayapi/rechargeUrl";

	// 设置提现密码
	public static final String Post_SubmitPassword = "/outcashapi/set_dealpwd";

	// 获取银行卡列表
	public static final String Post_GetBankList = "/outcashapi/get_bankcards";

	// 添加银行卡
	public static final String Post_AddBankCard = "/outcashapi/add_bankcard";

	// 提现
	public static final String Post_SubmitWithdraw = "/outcashapi/add_outcashinfo";

	// 解除银行卡
	public static final String Post_DeleteBankCard = "/outcashapi/del_bankcard";

	// 获取用户信息
	public static final String Post_GetDriverInfo = "/drivermyinfo/get_driverinfo";

	// 选择司机
	public static final String Post_ChooseDriver = "/releaserorder/choose";

	// 更新版本
	public static final String Post_UpdataVersion = "/version/check_version";

	// 获取所有司机的位置
	public static final String Post_GetAllDriver = "/releaserinfoapi/get_driverssite";

	// 货主评论司机
	public static final String Post_OwenrCommentDriver = "/comment/add_re_comment";

	// 微信支付运费
	public static final String Post_PayMentfreight = "/wxpayapi/wxpay";

	// 余额支付运费
	public static final String Post_PayMentfreights = "/wxpayapi/accountpay";

	// 支付宝支付运费回调页
	public static final String Post_AliPayUpdata = "/alipayapi/notify_url";

	// 签到
	public static final String Post_Signin = "/signin/dosignin?userId=";

	// 货主取消订单前先获取还有几次机会
	public static final String Post_CancleOrderNum = "/releasercancel/get_cancel";

	// 货主取消订单
	public static final String Post_CancleOrder = "/releasercancel/cancel";

	// 货主发单扣除金币调取的接口
	public static final String Post_GetOwnerGold = "/releaserinfoapi/send_golds";

	// 获取分享内容
	public static final String Post_GetShare = "/weixin/getmodel";

	// 分享成功后领取奖励金币
	public static final String Post_ShareSuccessGetGold = "/weixin/sendKind";

	// 获取评论列表
	public static final String Post_GetCommentList = "/comment/findlists";

	// 用户消费记录
	public static final String Post_ExpenseCalendar = "/commonapi/findHistory";

	// 获取货主有没有设置支付密码
	public static final String Post_GetPayPow = "/commonapi/isSetPwd";

	// 获取货主当前位置附近的司机坐标
	public static final String Post_UpLoadNearbyDrivers = "/releaserinfoapi/get_driverssites";

	/**
	 *
	 */
	public static final String Post_User_ModifyPwd ="/commonapi/updatePwd";
}
