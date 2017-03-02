package zhidanhyb.huozhu.View;

import java.util.Arrays;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.TextView;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.ScreenUtils;

/**
 * 填写订单时车型Dialog
 * @author lxj
 *
 */
public class SendOrder_CarTypeDialog extends PopupWindow {

	private Context mContext;
	private View view;
	private View popview;
	private TextView cancle_textview;
	private TextView sure_textview;
	private WheelView car_wheelview;
	private WheelView ton_wheelview;
	private String[] CAR_TYPE = {"可不选","板车","高栏","厢式","箱式","其他"};
	private String[] TON_TYPE1 = {"可不选"};
	private String[] TON_TYPE2 = {"2.5吨","3.5吨","8吨","10吨","12吨","15吨"};
	private String[] TON_TYPE3 = {"2.5吨","3.5吨","8吨","10吨","12吨","15吨"};
	private String[] TON_TYPE4 = {"2.5吨","3.5吨","10吨","12吨","15吨"};
	private String[] TON_TYPE5 = {"8吨"};
	private String[] TON_TYPE6 = {"大于5吨","低于5吨"};
	private String cartype = "可不选";
	private String tontype = "可不选";
	public SendOrder_CarTypeDialog(Context context,View view) {
		this.mContext = context;
		this.view = view;
		showCarType();
	}
	

	public void showCarType(){
		popview = LayoutInflater.from(mContext).inflate(R.layout.sendorder_cartypelayout, null);
		cancle_textview = (TextView)popview.findViewById(R.id.sendorder_cancle_textview);
		sure_textview = (TextView)popview.findViewById(R.id.sendorder_sure_textview);
		car_wheelview = (WheelView)popview.findViewById(R.id.sendorder_car_wheelview);
		ton_wheelview = (WheelView)popview.findViewById(R.id.sendorder_ton_wheelview);
		setContentView(popview);
		this.setBackgroundDrawable(mContext.getResources().getDrawable(R.color.transparent));
		this.setOutsideTouchable(false);
		this.setFocusable(true);
		this.setHeight(ScreenUtils.getScreenHeight(mContext)/3);
		this.setWidth(ScreenUtils.getScreenWidth(mContext));
		showAtLocation(view,Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);


		car_wheelview.setOffset(1);
		car_wheelview.setItems(Arrays.asList(CAR_TYPE));
		car_wheelview.setSeletion(0);
		car_wheelview.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
			@Override
			public void onSelected(int selectedIndex, String item) {
				
				cartype = item;
				switch (selectedIndex) {
				case 1:
					ton_wheelview.setItems(Arrays.asList(TON_TYPE1));
					tontype = TON_TYPE1[0];
					break;
				case 2:
					ton_wheelview.setItems(Arrays.asList(TON_TYPE2));
					tontype = TON_TYPE2[0];
					break;
				case 3:
					ton_wheelview.setItems(Arrays.asList(TON_TYPE3));
					tontype = TON_TYPE3[0];
					break;
				case 4:
					ton_wheelview.setItems(Arrays.asList(TON_TYPE4));
					tontype = TON_TYPE4[0];
					break;
				case 5:
					ton_wheelview.setItems(Arrays.asList(TON_TYPE5));
					tontype = TON_TYPE5[0];
					break;
				case 6:
					ton_wheelview.setItems(Arrays.asList(TON_TYPE6));
					tontype = TON_TYPE6[0];
					break;
				default:
					break;
				}
			}
		});


		ton_wheelview.setOffset(1);
		ton_wheelview.setItems(Arrays.asList(TON_TYPE1));
		ton_wheelview.setSeletion(0);
		ton_wheelview.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
			@Override
			public void onSelected(int selectedIndex, String item) {
				
				tontype = item;
			}
		});

		sure_textview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sendOrderCarTypeListener.onCarType(cartype, tontype);
				dismiss();
			}
		});
		
		cancle_textview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}
	private onSendOrderCarTypeListener sendOrderCarTypeListener;
	public void setonSendOrderCarTypeListener(onSendOrderCarTypeListener onCarTypeListener){
		this.sendOrderCarTypeListener = onCarTypeListener;
	}
	public interface onSendOrderCarTypeListener{
		void onCarType(String car, String ton);
	}

}
