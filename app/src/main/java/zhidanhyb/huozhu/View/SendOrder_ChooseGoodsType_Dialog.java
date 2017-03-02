package zhidanhyb.huozhu.View;

import java.util.Arrays;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Base.BaseDialog;
import zhidanhyb.huozhu.Utils.T;

/**
 *  发货时选择货物类型的dialog
 * @author lxj
 *
 */
public class SendOrder_ChooseGoodsType_Dialog extends BaseDialog implements View.OnClickListener{
	private String[] type = {"重货","泡货","其他"};
	private Context mContext;
	private Button back_button;
	private Button sure_button;
	private EditText ton_textview;
	private String typeitem ="重货";//默认重货
	private String ton;
	public SendOrder_ChooseGoodsType_Dialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
	}
	
	public void Show(){
		View view = getLayoutInflater().from(mContext).inflate(R.layout.choosegoods_typelayout, null);
		setContentView(view);
		setDialogTitle(R.string.sendorder_goods_type , R.drawable.yellowtips);
		sure_button = (Button)view.findViewById(R.id.dialog_sure_button);//确定
		sure_button.setOnClickListener(this);
		sure_button.setText(R.string.sendorder_goods_type_sure);
		back_button = (Button)view.findViewById(R.id.dialog_back_button);//返回
		back_button.setOnClickListener(this);
		back_button.setText(R.string.sendorder_goods_type_cancle);
		ton_textview = (EditText)view.findViewById(R.id.goods_type_ton_textview);//吨位数
		WheelView wv = (WheelView) view.findViewById(R.id.choosegoods_type_wheelview);
		wv.setOffset(1);
		wv.setItems(Arrays.asList(type));
		wv.setSeletion(0);
		wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
		    @Override
		    public void onSelected(int selectedIndex, String item) {
		    	typeitem = item;
		    }
		});
		show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_sure_button:
			ton = ton_textview.getText().toString().trim();
			
			if(ton.isEmpty()){
				T.showLong(mContext, "请输入吨位!");
				return;
			}
			onChooseGoodsTypeListener.getTonNum(typeitem, ton);
			dismiss();
			break;
		case R.id.dialog_back_button:
			dismiss();
			break;
		default:
			break;
		}
	}
	
	private OnChooseGoodsTypeListener onChooseGoodsTypeListener;
	public void setOnChooseGoodsTypeListener(OnChooseGoodsTypeListener typeListener){
		onChooseGoodsTypeListener = typeListener;
	}
	
	public interface OnChooseGoodsTypeListener{
		void getTonNum(String type, String ton);
	}
}

