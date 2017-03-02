package zhidanhyb.huozhu.pay;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.content.Context;
import zhidanhyb.huozhu.Bean.RechargeWxBean;
import zhidanhyb.huozhu.Config.ConstantConfig;
/**
 * 调起 微信支付
 * @author lxj
 *
 */
public class WxPay {

	private Context mContext;
	private IWXAPI api;
	public WxPay(Context context) {
		this.mContext = context;
	}
	
	public void pay(RechargeWxBean wxbean){
		api = WXAPIFactory.createWXAPI(mContext, ConstantConfig.APP_ID);//将app注册到微信
		api.registerApp(ConstantConfig.APP_ID); 
		PayReq request = new PayReq();
		request.appId=ConstantConfig.APP_ID;
		request.partnerId = wxbean.getPartnerid();
		request.prepayId=wxbean.getPrepay_id();
		request.packageValue=wxbean.getPackagevalue();
		request.nonceStr=wxbean.getNonceStr();
		request.timeStamp=wxbean.getTimestamp();
		request.sign=wxbean.getSign();
		api.sendReq(request);
	}
}
