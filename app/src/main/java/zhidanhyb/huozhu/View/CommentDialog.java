package zhidanhyb.huozhu.View;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Activity.Order.Main_OrderActivity;
import zhidanhyb.huozhu.Activity.Order.OrderDetailsActivity;
import zhidanhyb.huozhu.Base.BaseDialog;
import zhidanhyb.huozhu.Config.ConstantConfig;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.onCommentListener;
import zhidanhyb.huozhu.Utils.T;
/**
 * 货主评论司机的dialog
 * @author lxj
 *
 */
public class CommentDialog extends BaseDialog implements onCommentListener{

	private Context mContext;
	private TextView oriderid_textview;
	private TextView money_textview;
	private RatingBar star_ratingbar;
	private EditText content_edittext;
	private float rat = 0;
	private String OrderID = null; 
	private String Price = null;
	public CommentDialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
	}
	
	public void showDialog(String orderId, String price){
		this.OrderID = orderId;
		this.Price = price;
		View view = LayoutInflater.from(mContext).inflate(R.layout.commentlayout, null);
		setContentView(view);
		setDialogTitle(R.string.commentdriver, R.drawable.yellowtips);
		setLeftButton(R.string.back, R.drawable.gray_circular_bead_buttons);
		setRightButton(R.string.ok, R.drawable.orange_circular_bead_buttons);
		oriderid_textview = (TextView)view.findViewById(R.id.comment_oriderid_textview);//订单ID号
		money_textview = (TextView)view.findViewById(R.id.comment_money_textview);//总运费
		star_ratingbar = (RatingBar)view.findViewById(R.id.comment_star_ratingbar);//星级
		content_edittext = (EditText)view.findViewById(R.id.comment_content_edittext);//编辑评价内容
		star_ratingbar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				rat = rating;
			}
		});
		oriderid_textview.setText("单号 "+OrderID+" 司机已确认收款");
		money_textview.setText(Price);
		show();
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.dialog_back_button://返回
			dismiss();
			break;
		case R.id.dialog_sure_button://确定
			if(sure_button != null){
				sure_button.setClickable(false);
			}
			if(!content_edittext.getText().toString().trim().isEmpty()){
				if(OrderID != null){
					HttpController controller = new HttpController(mContext);
					String star = rat+"";
					String content = content_edittext.getText().toString().trim();
					String oid = OrderID;
					controller.setOnCommentListener(this);
					controller.OwnerCommentDriver(star,content,oid);
				}
			}else{
				if(sure_button != null){
					sure_button.setClickable(true);
				}
				T.showShort(mContext, "请为司机进行评价");
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onComment() {
		if(sure_button != null){
			sure_button.setClickable(true);
		}
		if(ConstantConfig.isOrderDetails){
			if(OrderDetailsActivity.detailsActivity != null){
				OrderDetailsActivity.detailsActivity.getData();
			}
		}
		if(ConstantConfig.isOrderList){
			Intent intent = new Intent(ConstantConfig.updataOrderList);
			mContext.sendBroadcast(intent);
		}
		dismiss();
	}
}
