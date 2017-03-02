package zhidanhyb.huozhu.wxapi;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.Config.ConstantConfig;
import zhidanhyb.huozhu.View.PayMentfreightDialog;
import zhidanhyb.huozhu.View.User_Recharge_Gold_Dialog;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler{
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    	api = WXAPIFactory.createWXAPI(this, ConstantConfig.APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
		
	}

	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			switch (resp.errCode) {
			case -1://错误
				Toast.makeText(WXPayEntryActivity.this, "支付错误,请重新支付!", 0).show();
				finish();
				break;
			case -2://用户取消
				Toast.makeText(WXPayEntryActivity.this, "支付已取消!", 0).show();
				finish();
				break;
			case 0://成功
				Toast.makeText(WXPayEntryActivity.this, "支付成功!", 0).show();
				if(User_Recharge_Gold_Dialog.recharge_gold != null){
					User_Recharge_Gold_Dialog.recharge_gold.dismiss();
					User_Recharge_Gold_Dialog.recharge_gold = null;
				}
				if(PayMentfreightDialog.mentfreightDialog != null){
					PayMentfreightDialog.mentfreightDialog.ClooseDialog();
				}
				finish();
				break;
			default:
				break;
			}
		}
	}
}