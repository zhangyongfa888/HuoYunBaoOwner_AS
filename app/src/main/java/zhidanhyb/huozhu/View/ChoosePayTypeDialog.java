package zhidanhyb.huozhu.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Base.BaseDialog;
/**
 * 选择充值的支付方式dialog
 * @author lxj
 *
 */
public class ChoosePayTypeDialog extends BaseDialog {
	private Context mContext;
	private LinearLayout wx_linearlayout;
	private LinearLayout zfb_linearlayout;
	
	public ChoosePayTypeDialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
	}
	public void showDialog(){
		View view = LayoutInflater.from(mContext).inflate(R.layout.choosepaytypelayout, null);
		setContentView(view);
		
		wx_linearlayout = (LinearLayout)view.findViewById(R.id.paytype_wx_linearlayout);//微信支付
		zfb_linearlayout = (LinearLayout)view.findViewById(R.id.paytype_zfb_linearlayout);//支付宝支付
		wx_linearlayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				User_Recharge_Gold_Dialog rechargedialog = new User_Recharge_Gold_Dialog(mContext, R.style.dialog);
				rechargedialog.showDialog(1);
				dismiss();
			}
		});
		
		zfb_linearlayout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				User_Recharge_Gold_Dialog rechargedialog = new User_Recharge_Gold_Dialog(mContext, R.style.dialog);
				rechargedialog.showDialog(2);
				dismiss();
			}
		});
		show();
		
	}
}
