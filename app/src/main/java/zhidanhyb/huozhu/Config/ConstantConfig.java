package zhidanhyb.huozhu.Config;

import java.util.List;

import zhidanhyb.huozhu.Bean.Order_SendSureBean;

/**
 * 常量
 * @author lxj
 *
 */
public class ConstantConfig {


	public static double DriverLat = 0f;//获取司机纬度 0-99
	public static double DriverLng = 0f;//获取司机经度
	
	
	//定位后的城市是
	public static String City = null;
	//添加订单用的集合
	public static List<Order_SendSureBean> sendSureList ;
	//发送订单成功后改为true并且跳转到订单列表
	public static boolean sendOrderSucc = false;
	//客服电话 
	public static String TELEPHONE = "400-7969898";
	//微信AppId
	public static final String APP_ID = "wxd7c6148b92e8c155";
	// 商户PID
	public static final String PARTNER = "2088021314945395";
	// 商户收款账号
	public static final String SELLER = "xmzhidan@qq.com";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE =
			"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALpi2ziCqMjzQuIH4Zx6kkAthCEiSbbf/0AdKZwnjRziyG3PHhhv4TqX7avHvO10QLpgtFYWq5zYfRpeoSRSC990E+XaHc6BD6gDynU+srCOK1JoXWOTC4iSBjH0OecsTHLLr8PmzdDCXGKzLm/gy+BtjT5Esz4C9bJP88luZnFzAgMBAAECgYB+AWsXLy4QfHTQ/mcGNM8+2Aalp9qR6alUnOKOkSaTSrYZlpZQCwMHa8LgCS6ukZNY7Ru9v6HRbYJWG3dxaI5hIEPwz4XBSFuuZVO3J+BuaFjqfzpzXD8NUpYomTxOwKU3RIlVpmz9aVko9I0/HTDMjq+YvEtzdjUH42cChmIPwQJBAO6ogj6qiQKo4XEtvrtW95RJHyrO6slEXuCM5x5ToC4xLJpi+OxV6+lxWcx58vhEDhHoQ9UFZttAR5p/wZ8ylyMCQQDH7f0exPTGQzyTo9cXgjDagFy5xDcicdvmfubVXNp84zdv30mEl+2X4K3IqISRQppNhao0Hk4Im87llCsMEIlxAkEArZGR/99o+601IGnjkD4Emyy+5RRNfwkhc3AmWlD5av7KcJHQdZlblJCifWqngVWFYhKo2pEuKADM0pldiNlzPwJBAKJgVMLnvdFEjG9wkuHh+CGOPUEW7pyIEswqZvoE9uKVe9HdbI1HA03lyd79luWPTVkG5Ux9DG2LdccZxrh+udECQCnBns0oxvwzvuvsheIr+hT8BJMSHzutuqvT5+ur/hzrlTdrTU9MCii2icJ/l3cvQn4EsBr1h4zUbEIzWXZPSVQ=";
	// 支付宝公钥
	public static final String RSA_PUBLIC =
			"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	public static final int SDK_PAY_FLAG = 1;
	public static final int SDK_CHECK_FLAG = 2;

	public static String Url = null;//更新版本的地址


	//通知过来的订单id
	public static String NotifyOrderId = null;
	//通知类型--推送type3：进附近列表    type4:进订单详情  type5 不跳转
	public static String NotifyType = null;

	/**
	 * 以下是订单状态
	 * 1 = 等待抢单
	 * 2 = 待选司机
	 * 3 = 等待付款
	 * 5 = 订单完成
	 * 9 = 订单取消
	 */
	public static final int OrderStatusOne = 1;
	public static final int OrderStatusTwo = 2;
	public static final int OrderStatusThree = 3;
	public static final int OrderStatusFive = 5;
	public static final int OrderStatusNine = 9;

	
	//判断司机当前的页面是不是订单详情页 - 默认false不在   true在
	public static boolean isOrderDetails = false;
	//Action - 根据 isOrderDetails == true 的时候在订单详情页更新订单状态的广播接收器做处理
	public static final String updataOrderDetails = "UpdataOrderDetails";
	//Action - 根据 isOrderDetails == true 订单已经被司机取消竞单
	public static final String orderCancle = "OrderCancle";
	
	//判断司机当前的页面是不是订单列表页面 - 默认false不在   true在		
	public static boolean isOrderList = false;
	//Action - 根据 isOrderList == true 的时候在订单列表页更新订单状态的广播接收器做处理
	public static final String updataOrderList = "UpdataOrderList";
	//Action - 根据 isOrderList == true 订单已经被司机取消竞单
	public static final String orderCancleList = "OrderCancleList";
}
