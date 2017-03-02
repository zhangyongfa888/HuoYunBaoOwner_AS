package zhidanhyb.huozhu.Activity.Me;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import java.io.File;
import java.io.IOException;

import zhidanhyb.huozhu.Activity.Setting.SettingActivity;
import zhidanhyb.huozhu.Base.BaseDialog;
import zhidanhyb.huozhu.Base.BaseFragment;
import zhidanhyb.huozhu.Base.HYBHuoZhuApplication;
import zhidanhyb.huozhu.Bean.UserGoldBean;
import zhidanhyb.huozhu.Config.ConstantConfig;
import zhidanhyb.huozhu.Config.ZDSharedPreferences;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.getUserGoldListener;
import zhidanhyb.huozhu.HttpRequest.HttpController.updataUserHeadListener;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.CallUtils;
import zhidanhyb.huozhu.Utils.ScreenUtils;
import zhidanhyb.huozhu.Utils.UserVerifyUtils;
import zhidanhyb.huozhu.View.ChoosePayTypeDialog;
import zhidanhyb.huozhu.View.CircleImageView;
import zhidanhyb.huozhu.View.User_Conversion_Gold_Dialog.conversionSuccessListener;

/**
 * 我的
 * 
 * @author lxj
 *
 */
public class Main_MeActivity extends BaseFragment
		implements getUserGoldListener, conversionSuccessListener, updataUserHeadListener {

	/**
	 *
	 */
	private static CircleImageView head_imageview;
	private TextView name_textview;
	private TextView attestation_textview;
	private TextView company_textview;
	private TextView phone_textview;
	private TextView gold_textview;
	private RatingBar star_ratingbar;
	private TextView grade_textview;
	private Button details_button;
	private LinearLayout comment_linearlayout, me_personinfo_linearlayout;
	private LinearLayout raffle_linearlayout;
	private LinearLayout setting_linearlayout;
	private Button exchange_gold_button;
	private Button recharge_button;
	private ImageLoader imageLoader;
	private TextView servicenumber_textview;
	private String balance = null;
	private ImageView me_level_imageview;
	private File tempFile;
	private ChangeHeadDialog headDialog;

	/* 头像名称 */
	private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
	private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (view == null) {
			view = inflater.inflate(R.layout.main_me_layout, null);
			initView();
			setView();
		}
		return view;
	}

	/**
	 *
	 */
	private void initView() {
		setTitleText(R.string.me);
		setHideLeftButton();
		setHideRightButton();
		head_imageview = (CircleImageView) view.findViewById(R.id.me_head_imageview);// 用户头像
		head_imageview.setOnClickListener(this);
		name_textview = (TextView) view.findViewById(R.id.v2_me_name);// 用户姓名
		me_level_imageview = (ImageView) view.findViewById(R.id.me_level_imageview);// 等级
		attestation_textview = (TextView) view.findViewById(R.id.me_attestation_textview);// 是否认证
		company_textview = (TextView) view.findViewById(R.id.me_company_textview);// 企业名称
		phone_textview = (TextView) view.findViewById(R.id.me_phone_textview);// 联系电话
		gold_textview = (TextView) view.findViewById(R.id.me_gold_textview);// 金币数量
		star_ratingbar = (RatingBar) view.findViewById(R.id.me_star_ratingbar);// 星级
		grade_textview = (TextView) view.findViewById(R.id.me_grade_textview);// 等级
		details_button = (Button) view.findViewById(R.id.me_details_button);// 查看详情
		servicenumber_textview = (TextView) view.findViewById(R.id.main_me_servicenumber_textview);// 打电话
		servicenumber_textview.setOnClickListener(this);
		details_button.setOnClickListener(this);
		comment_linearlayout = (LinearLayout) view.findViewById(R.id.me_comment_linearlayout);// 用户评论
		comment_linearlayout.setOnClickListener(this);
		me_personinfo_linearlayout = (LinearLayout) view.findViewById(R.id.me_personinfo_linearlayout);
		me_personinfo_linearlayout.setOnClickListener(this);
		raffle_linearlayout = (LinearLayout) view.findViewById(R.id.me_raffle_linearlayout);// 抽奖
		raffle_linearlayout.setOnClickListener(this);
		setting_linearlayout = (LinearLayout) view.findViewById(R.id.me_setting_linearlayout);// 设置
		setting_linearlayout.setOnClickListener(this);
		exchange_gold_button = (Button) view.findViewById(R.id.me_exchange_gold_button);// 兑换金币
		exchange_gold_button.setOnClickListener(this);
		recharge_button = (Button) view.findViewById(R.id.me_recharge_button);// 充值
		recharge_button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.me_head_imageview:// 用户头像
			headDialog = new ChangeHeadDialog(getActivity(), R.style.dialog);
			headDialog.showDialog();
			break;
		case R.id.me_details_button:// 查看详情
			intent.setClass(getActivity(), User_AccountActivity.class);
			startActivity(intent);
			break;
		case R.id.me_comment_linearlayout:// 用户评论
			intent.setClass(getActivity(), User_CommentActivity.class);
			startActivity(intent);
			break;
		case R.id.me_raffle_linearlayout:// 抽奖

			break;
		case R.id.me_personinfo_linearlayout:
			intent.setClass(getActivity(), UserEditPersonInfoActivity.class);
			startActivityForResult(intent, 0x101);
			break;
		case R.id.me_setting_linearlayout:// 设置
			intent.setClass(getActivity(), SettingActivity.class);
			startActivity(intent);
			break;
		case R.id.me_exchange_gold_button:// 兑换金币  没了
//			User_Conversion_Gold_Dialog dialog = new User_Conversion_Gold_Dialog(getActivity(), R.style.dialog);
//			dialog.setConversionSuccessListener(this);
//			dialog.showDialog(balance);
			break;
		case R.id.me_recharge_button:// 充值    没了
			ChoosePayTypeDialog paydialog = new ChoosePayTypeDialog(getActivity(), R.style.dialog);
			paydialog.showDialog();
			break;
		case R.id.main_me_servicenumber_textview:// 打电话
			CallUtils.startSystemDialingActivity(getActivity(), ConstantConfig.TELEPHONE);
			break;
		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.e("result", "---------------------------resultCode="+resultCode+",requestCode="+requestCode);
		if (resultCode == getActivity().RESULT_OK) {
			if (requestCode == 0x101) {
				setView();
			}

		}

	}

	@Override
	public void onResume() {
		super.onResume();
		getUserData();
	}

	// 主要获取用户金币数、级别、星星指数
	private void getUserData() {
		HttpController controller = new HttpController(getActivity());
		String type = "2";// (1司机 2货主)
		controller.setGetUserGoldListener(this);
		controller.getUserGold(type);
	}

	// view赋值
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@SuppressWarnings("static-access")
	private void setView() {
		name_textview.setText(ZDSharedPreferences.getInstance(getActivity()).getUserName());
		new UserVerifyUtils().userVerify(attestation_textview,
				ZDSharedPreferences.getInstance(getActivity()).getUserStatus(),getContext());
		company_textview.setText(ZDSharedPreferences.getInstance(getActivity()).getUserCompany());
		phone_textview.setText("联系电话: " + ZDSharedPreferences.getInstance(getActivity()).getUserMobile());
		imageLoader = ImageLoader.getInstance();
		if (ZDSharedPreferences.getInstance(getActivity()).getUserHead().isEmpty()) {
			head_imageview.setBackground(getResources().getDrawable(R.drawable.head));
		} else {
			imageLoader.displayImage(ZDSharedPreferences.getInstance(getActivity()).getUserHead(), head_imageview,
					HYBHuoZhuApplication.options);
		}
	}

	// 头像更换成功后将新头像放到imageview
	public static void setHead(Bitmap bitmap) {
		if (bitmap == null) {
			return;
		}
		head_imageview.setImageBitmap(bitmap);
	}

	/**
	 * @param userGoldBean
	 */
	// 获取用户金币成功后的回调并赋值
	@Override
	public void getUserGold(UserGoldBean userGoldBean) {
		if (userGoldBean == null) {
			return;
		}

		gold_textview.setText(userGoldBean.getGold());
		grade_textview.setText("级别：" + userGoldBean.getLevel());
		star_ratingbar.setRating(userGoldBean.getScore());
		balance = userGoldBean.getBalance();
		UserVerifyUtils.setUserLevelImage(me_level_imageview, userGoldBean.getLevel());
	}

	/**
	 *
	 */
	// 兑换金币成功后的回调-重新获取用户金币数
	@Override
	public void onConversionSuccess() {
		getUserData();
	}

	// 设置头像
	class ChangeHeadDialog extends BaseDialog {

		private Context mContext;
		private TextView camera_textview;
		private TextView photo_album_textview;

		public ChangeHeadDialog(Context context, int theme) {
			super(context, theme);
			this.mContext = context;
		}

		public void showDialog() {
			View view = LayoutInflater.from(mContext).inflate(R.layout.changeheadlayout, null);
			setContentView(view);
			camera_textview = (TextView) findViewById(R.id.change_head_camera_textview);// 从相机
			camera_textview.setOnClickListener(this);
			photo_album_textview = (TextView) findViewById(R.id.change_head_photo_album_textview);// 从相册
			photo_album_textview.setOnClickListener(this);
			show();
			WindowManager.LayoutParams params = this.getWindow().getAttributes();
			params.width = ScreenUtils.getScreenWidth(getActivity()) - 200;
			// params.height = 350;
			getWindow().setAttributes(params);
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			super.onClick(v);
			switch (v.getId()) {
			case R.id.change_head_camera_textview:// 从相机
				// Intent intent = new
				// Intent("android.media.action.IMAGE_CAPTURE");
				// // 判断存储卡是否可以用，可用进行存储
				// if (SDCardUtils.isSDCardEnable()) {
				// intent.putExtra(MediaStore.EXTRA_OUTPUT,
				// Uri.fromFile(new File(Environment
				// .getExternalStorageDirectory(), PHOTO_FILE_NAME)));
				// }
				// startActivityForResult(intent, PHOTO_REQUEST_CAMERA);

				if (hasPermission(Manifest.permission.CAMERA)) {
					selectPicFromCamera();
				} else {
					getPermission(Manifest.permission.CAMERA, REQUEST_PERMISSION_CAMERA);
				}

				break;
			case R.id.change_head_photo_album_textview:// 从相册
				// // 激活系统图库，选择一张图片
				// Intent intent_photo = new Intent(Intent.ACTION_PICK);
				// intent_photo.setType("image/*");
				// startActivityForResult(intent_photo, PHOTO_REQUEST_GALLERY);

				selectPicFromLocal();
				break;
			default:
				break;
			}
		}

	}
	// @Override
	// public void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// if (requestCode == PHOTO_REQUEST_GALLERY) {
	// if (data != null) {
	// // 得到图片的全路径
	// Uri uri = data.getData();
	// crop(uri);
	// }
	//
	// } else if (requestCode == PHOTO_REQUEST_CAMERA) {
	// if (SDCardUtils.isSDCardEnable()) {
	// tempFile = new File(Environment.getExternalStorageDirectory(),
	// PHOTO_FILE_NAME);
	// crop(Uri.fromFile(tempFile));
	// } else {
	// T.showShort(getActivity(), "未找到存储卡，无法存储照片！");
	// }
	//
	// } else if (requestCode == PHOTO_REQUEST_CUT) {
	// try {
	// Bitmap bitmap = data.getParcelableExtra("data");
	// Main_MeActivity.setHead(bitmap);
	// send_head();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }
	// super.onActivityResult(requestCode, resultCode, data);
	// }

	@Override
	protected void setUpImageView(final String imagePath) {
		ImageLoader.getInstance().loadImage("file://" + imagePath, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				head_imageview.setImageBitmap(loadedImage);
				Bitmap b = zhidanhyb.huozhu.Utils.ImageUtils.getSmallBitmap(imagePath);
				try {
					Image1 = zhidanhyb.huozhu.Utils.ImageUtils.saveBitmap2File(getContext(),b, System.currentTimeMillis() + ".jpg");
					send_head(Image1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

			}
		});

	}
	File Image1;
	// 向后台发送图片
	private void send_head(File tempFile) {
		// bitmap.recycle();

		HttpController controller = new HttpController(getActivity());
		String type = "2";// (1司机 2货主)
		controller.setUpdataUserHeadListener(this);
		controller.updataUserHead(type, tempFile);
	}

	@Override
	public void updataUserHead() {
		if (headDialog.isShowing()) {
			headDialog.dismiss();
			headDialog = null;
		}
		if (Image1.exists() || Image1 != null) {
			Image1.delete();

		}
	}
}
