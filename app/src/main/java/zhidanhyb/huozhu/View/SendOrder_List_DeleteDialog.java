package zhidanhyb.huozhu.View;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Base.BaseDialog;
import zhidanhyb.huozhu.Bean.Order_SendSureBean;
/**
 * 删除订单列表里的数据dialog
 * @author lxj
 *
 */
public class SendOrder_List_DeleteDialog extends BaseDialog {

	private Context mContext;
	private List<Order_SendSureBean>orderlist;
	private int deletePosition;
	public SendOrder_List_DeleteDialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
	}
	
	public void showDialog(int position){
		deletePosition = position;
		View view = LayoutInflater.from(mContext).inflate(R.layout.messagedelete_dialoglayout, null);
		setContentView(view);
		setDialogTitle(R.string.hint, R.drawable.yellowtips);
		setLeftButton(R.string.back, R.drawable.gray_circular_bead_buttons);
		setRightButton(R.string.ok, R.drawable.orange_circular_bead_buttons);
		orderlist = onsendorderlistdeletelistener.getOrderList();
		show();
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.dialog_back_button:
			dismiss();
			break;
		case R.id.dialog_sure_button:
			if(orderlist != null){
				orderlist.remove(deletePosition);
			}
			if(onsendorderlistdeletelistener != null){
				onsendorderlistdeletelistener.onDelete(orderlist);
			}
			dismiss();
			break;
		default:
			break;
		}
	}
	private onSendOrderListDeleteListener onsendorderlistdeletelistener;
	public void setOnSendOrderListDeleteListener(onSendOrderListDeleteListener onDeleteListener){
		this.onsendorderlistdeletelistener = onDeleteListener;
	}
	public interface onSendOrderListDeleteListener{
		void onDelete(List<Order_SendSureBean> list);
		List<Order_SendSureBean> getOrderList();
	}
}
