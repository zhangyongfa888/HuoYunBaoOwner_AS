package zhidanhyb.huozhu.View;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Base.BaseDialog;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.conversionGoldListener;
import zhidanhyb.huozhu.Utils.KeyboradUtils;
import zhidanhyb.huozhu.Utils.T;

/**
 * 用户兑换金币dialog
 * @author lxj
 *
 */
public class User_Conversion_Gold_Dialog extends BaseDialog implements conversionGoldListener{

	private Context mContext;
	private EditText gold_edittext;
	private TextView gold_textview;
	private TextView gold_balance_textview;
	
	public User_Conversion_Gold_Dialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
	}

	/**
	 * @param balance 余额
	 */
	public void showDialog(String balance){
		View view = LayoutInflater.from(mContext).inflate(R.layout.user_converison_gold_layout, null);
		setContentView(view);
		setDialogTitle(R.string.conversion_gold, R.drawable.yellowtips);
		gold_edittext = (EditText)view.findViewById(R.id.converison_gold_edittext);//编辑金币数量
		gold_textview = (TextView)view.findViewById(R.id.converison_gold_textview);//需要金币数
		gold_balance_textview = (TextView)view.findViewById(R.id.converison_gold_balance_textview);//账户余额/￥15000
		setLeftButton(R.string.sendorder_goods_type_cancle, R.drawable.gray_circular_bead_buttons);
		setRightButton(R.string.sure, R.drawable.orange_circular_bead_buttons);
		if(balance != null){
			gold_balance_textview.setText("￥"+balance);
		}
		show();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				//设置可获得焦点  
				gold_edittext.setFocusable(true); 
				gold_edittext.setFocusableInTouchMode(true);  
		        //请求获得焦点  
				gold_edittext.requestFocus(); 
				KeyboradUtils.ShowKeyboard(gold_edittext);				
			}
		}, 200);
		gold_edittext.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				int gold = Integer.parseInt(s.toString().trim());
				gold_textview.setText("需要"+s.toString().trim()+"元");
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		
		switch (v.getId()) {
		case R.id.dialog_back_button://返回
			dismiss();
			closeDialog();
			break;
		case R.id.dialog_sure_button://确认
			if(gold_edittext.getText().toString().trim().isEmpty()){
				T.showShort(mContext, R.string.conversionglod);
				return;
			}
			conversinGold(gold_edittext.getText().toString().trim());
			break;
		default:
			break;
		}
	}
	
	private void conversinGold(String trim) {
		HttpController controller = new HttpController(mContext);
		controller.setConversionGoldListener(this);
		controller.conversionGold("2",trim);
	}
	
	private void closeDialog(){
		gold_edittext = null;
		gold_textview = null;
		gold_balance_textview = null;
	}

	@Override
	public void conversion() {
		if(cListener != null){
			cListener.onConversionSuccess();
		}
		dismiss();
		closeDialog();
		
	}
	
	//兑换金币成功后刷新我的页面
	private conversionSuccessListener cListener;
	public void setConversionSuccessListener(conversionSuccessListener successListener){
		this.cListener = successListener;
	}
	public interface conversionSuccessListener{
		void onConversionSuccess();
	}
}
