package zhidanhyb.huozhu.View;

import java.util.ArrayList;
import com.google.gson.Gson;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Base.BaseDialog;
import zhidanhyb.huozhu.Bean.OrderAgain;
import zhidanhyb.huozhu.Bean.Order_SendSureBean;
import zhidanhyb.huozhu.Bean.OwnerGoldBean;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.Utils.NumberFormatUtils;
import zhidanhyb.huozhu.Utils.StringUtil;
import zhidanhyb.huozhu.Utils.T;

/**
 * 再次确认是否提交定订单的Dialog
 * 
 * @author lxj
 *
 */
public class SendOrder_SureOrder_Dialog extends BaseDialog implements OnClickListener {

	private Context mContext;
	private TextView context_textview;
	private TextView sureorder_consume_textview;
	private Order_SendSureBean order_SendSureBean = null;
	private OrderAgain again = null;
	private ArrayList<Order_SendSureBean> list;
	private TextView sureorder_gold_textview;
	private TextView sureorder_discount_textview;
	private TextView sureorder_textview;
	private TextView sureorder_level_textview;

	public SendOrder_SureOrder_Dialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
		again = new OrderAgain();
	}

	public void showDialog(Order_SendSureBean surebean, OwnerGoldBean ownerGoldBean) {
		order_SendSureBean = surebean;
		if (!StringUtil.isEmpty(surebean.getId())) {
			again.setDeparture_time(surebean.getTime());
			again.setIsPush(surebean.getIsPush());
			again.setOid(surebean.getId());
		}

		View view = LayoutInflater.from(mContext).inflate(R.layout.sendorder_sureorderlayout, null);
		setContentView(view);
		setDialogTitle(R.string.sureorder_hint, R.drawable.yellowtips);
		setLeftButton(R.string.sureorder_change, R.drawable.gray_circular_bead_buttons);
		setRightButton(R.string.sendorder_goods_type_sure, R.drawable.orange_circular_bead_buttons);
		context_textview = (TextView) view.findViewById(R.id.sureorder_context_textview);// 订单内容
		sureorder_textview = (TextView) view.findViewById(R.id.sureorder_textview);// 将扣除：
		sureorder_consume_textview = (TextView) view.findViewById(R.id.sureorder_consume_textview);// 消费
		sureorder_gold_textview = (TextView) view.findViewById(R.id.sureorder_gold_textview);// 金币
		sureorder_discount_textview = (TextView) view.findViewById(R.id.sureorder_discount_textview);// 折扣
		sureorder_level_textview = (TextView) view.findViewById(R.id.sureorder_level_textview);// 级别
		setView(surebean, ownerGoldBean);
		show();
	}

	private void setView(Order_SendSureBean surebean, OwnerGoldBean ownerGoldBean) {
		String ordertime = "发车时间 : " + surebean.getTime();
		String orderstart = "起点 : " + surebean.getStarAdr();
		String orderend = "终点 : " + surebean.getEndAdr();
		String goodstype = "货物类型:" + surebean.getGoodsType();
		String orderevaluate = "估价总值 : " + NumberFormatUtils.NumberType(surebean.getEvaluate()) + "元";
		String orderdetails = "货物详情/备注 : " + surebean.getOrderDetail();
		String context = ordertime + "\n" + orderstart + "\n" + orderend + "\n" + goodstype + "\n" + orderevaluate
				+ "\n" + orderdetails;
		context_textview.setText(context);
		if (ownerGoldBean != null) {
			int s = (int) ((ownerGoldBean.getPoint() * ownerGoldBean.getDiscount() * 0.01));
			if (surebean.getIsPush().toString().equals("1")) {
				s = s + 10;
			}
			sureorder_consume_textview.setText("您是" + ownerGoldBean.getLevel() + "");
			// sureorder_textview.setText("将扣除：");
			// sureorder_gold_textview.setText("" + s + "");
			// sureorder_discount_textview.setText("金币[" +
			// ownerGoldBean.getDiscount() + "]折");
			sureorder_textview.setVisibility(View.GONE);
			sureorder_gold_textview.setVisibility(View.GONE);
			sureorder_discount_textview.setVisibility(View.GONE);
			sureorder_level_textview.setText(ownerGoldBean.getStr());
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_back_button:// 修改
			clearView();
			break;
		case R.id.dialog_sure_button:
			if (order_SendSureBean != null) {
				T.showShort(mContext, "正在发送订单...");
				back_button.setClickable(false);
				sure_button.setClickable(false);
		
				if (!StringUtil.isEmpty(order_SendSureBean.getId())) {
					SendOrderAgain(order_SendSureBean.getTime(), order_SendSureBean.getIsPush(),
							order_SendSureBean.getId());

				} else {
					Gson gson = new Gson();
					list = new ArrayList<Order_SendSureBean>();
					list.add(order_SendSureBean);

					SendOrder(order_SendSureBean.getId(), gson.toJson(list));
				}

			}
			break;
		default:
			break;
		}
	}

	private void SendOrder(String id, String jsonOrder) {
		HttpController controller = new HttpController(mContext);
		controller.SendOrderJson(jsonOrder);
		clearView();
	}

	private void SendOrderAgain(String time, String push, String id) {
		HttpController controller = new HttpController(mContext);
		controller.SendAgainOrderJson(time, push, id);
		clearView();
	}

	private void clearView() {
		dismiss();
		if (mContext != null) {
			mContext = null;
		}
		context_textview = null;
		if (order_SendSureBean != null) {
			order_SendSureBean = null;
		}
		if (list != null) {
			list.clear();
			list = null;
		}
		sureorder_textview = null;
		sureorder_discount_textview = null;
		sureorder_gold_textview = null;
		sureorder_level_textview = null;
	}
}
