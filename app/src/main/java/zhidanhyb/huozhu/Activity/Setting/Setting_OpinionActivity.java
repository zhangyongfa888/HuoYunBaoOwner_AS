package zhidanhyb.huozhu.Activity.Setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.T;

/**
 * 意见反馈
 * @author lxj
 *
 */
public class Setting_OpinionActivity extends BaseActivity {

	private EditText content_edittext;
	private Button sure_button;
	private HttpController controller;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.setting_opinionlaout);
		initView();
	}

	private void initView() {
		setTitleText(R.string.setting_suggest);
		setHideRightButton();
		setLeftButton();
		content_edittext = (EditText)findViewById(R.id.opinion_content_edittext);//意见内容
		sure_button = (Button)findViewById(R.id.opinion_sure_button);//提交按钮
		sure_button.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		
		switch (v.getId()) {
		case R.id.title_left_rl://返回
			finish();
			break;
		case R.id.opinion_sure_button://提交
			if(content_edittext.getText().toString().isEmpty()){
				T.showShort(this, "内容不能为空!");
				return;
			}
			submitOpinion();
			break;
		default:
			break;
		}
	}

	private void submitOpinion() {
		controller = new HttpController(this);
		String type = "2";
		String content = content_edittext.getText().toString().trim();
		controller.SendFeedBack(type,content);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		content_edittext = null;
		sure_button = null;
		if(controller != null){
			controller = null;
		}
	}
}
