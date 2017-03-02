package zhidanhyb.huozhu.View;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Base.BaseDialog;
import zhidanhyb.huozhu.Bean.UpdataVersionBean;
import zhidanhyb.huozhu.Config.ConstantConfig;
import zhidanhyb.huozhu.Utils.AppUtil;
import zhidanhyb.huozhu.Utils.T;
import zhidanhyb.huozhu.Utils.UpdataApk_Service;
/**
 * 更新版本提示页面
 * @author lxj
 *
 */
public class UpdataVersionDialog extends BaseDialog{

	private Context mContext;
	private String url = null;
	private TextView updata_content_textview;
	private String is_ConstraintUpdata = null;
	private String isVersion = null;
	public UpdataVersionDialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
	}
	
	public void show(UpdataVersionBean updataVersionBean){
		if(updataVersionBean == null){
			return;
		}
		isVersion = updataVersionBean.getVer();
		is_ConstraintUpdata = updataVersionBean.getIs_updata();
		url = updataVersionBean.getUrl();
		if(is_ConstraintUpdata != null){
			if(is_ConstraintUpdata.toString().equals("0")){//不强制更新并显示更新提示
				View view = LayoutInflater.from(mContext).inflate(R.layout.updataversionlayot, null);
				updata_content_textview = (TextView)view.findViewById(R.id.updata_content_textview);//
				updata_content_textview.setText(updataVersionBean.getMsg());
				setContentView(view);
				setDialogTitle(R.string.updataversion, R.drawable.yellowtips);
				setRightButton(R.string.updatabutton, R.drawable.orange_circular_bead_buttons);
				setLeftButton(R.string.updatacancle, R.drawable.gray_circular_bead_buttons);
				show();
			}else if(is_ConstraintUpdata.toString().equals("1")){//强制更新不显示提示
				updata();
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.dialog_back_button://不更新
			dismiss();
			break;
		case R.id.dialog_sure_button://更新
			updata();
			break;
		default:
			break;
		}
	}

	public  void updata() {
		if(url != null){
			String version = AppUtil.getVersionName(mContext);
			if(isVersion.toString().equals(version)){//如果返回的版本号和当前应用版本号一致就不更新
				return;
			}else{//反之更新
				ConstantConfig.Url = url;
				//通知服务去后台下载
				mContext.startService(new Intent(mContext, UpdataApk_Service.class).setPackage("zhidanhyb.huozhu"));
				T.showLong(mContext, "正在后台下载!");	
				dismiss();
			}
		}
	}
}