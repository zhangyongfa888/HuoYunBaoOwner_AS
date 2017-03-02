package zhidanhyb.huozhu.Activity.Me;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import java.io.File;
import java.io.IOException;

import zhidanhyb.huozhu.Activity.Setting.SettingActivity;
import zhidanhyb.huozhu.Base.BaseFragment;
import zhidanhyb.huozhu.Base.HYBHuoZhuApplication;
import zhidanhyb.huozhu.Bean.UserGoldBean;
import zhidanhyb.huozhu.Config.ZDSharedPreferences;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.ImageUtils;
import zhidanhyb.huozhu.Utils.UserVerifyUtils;
import zhidanhyb.huozhu.Utils.ViewUtils;
import zhidanhyb.huozhu.View.CircleImageView;

/**
 * Created by Administrator on 2017/2/20.
 * 邮箱 1399142039@qq.com
 * 项目名称：HuoYunBaoOwner_AS
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2017/2/20 11:24
 * 修改人：Administrator
 * 修改时间：2017/2/20 11:24
 * 修改备注：
 */
public class Main_V2_MeActivity extends BaseFragment
        implements HttpController.getUserGoldListener, HttpController.updataUserHeadListener {
    /**
     *
     */
    private static CircleImageView v2_me_headphoto;
    /**
     *
     */
    private TextView v2_me_name;
    /**
     *
     */
    private TextView v2_me_attestation;
    /**
     *
     */
    private ImageView v2_me_vip, v2_me_level;
    private LinearLayout v2_me_ll_account, v2_me_ll_myInfo, v2_me_ll_v2_me_comments;

    /**
     *
     */
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
    private void setView() {
        v2_me_name.setText(ZDSharedPreferences.getInstance(getActivity()).getUserName());
        new UserVerifyUtils().userVerify(v2_me_attestation,
                ZDSharedPreferences.getInstance(getActivity()).getUserStatus(), getContext());
        ImageLoader imageLoader = ImageLoader.getInstance();
        if (ZDSharedPreferences.getInstance(getActivity()).getUserHead().isEmpty()) {
            v2_me_headphoto.setBackgroundResource(R.drawable.head);
        } else {
            imageLoader.displayImage(ZDSharedPreferences.getInstance(getActivity()).getUserHead(), v2_me_headphoto,
                    HYBHuoZhuApplication.options);
        }
    }

    /**
     * @param bitmap
     */
    // 头像更换成功后将新头像放到imageview
    public static void setHead(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        v2_me_headphoto.setImageBitmap(bitmap);
    }

    /**
     *
     */
    private void initView() {
        setTitleText(R.string.me);
        setHideLeftButton();
        setRightButton(R.drawable.v2_me_setting);
        v2_me_headphoto = (CircleImageView) view.findViewById(R.id.v2_me_headphoto);// 用户头像
        v2_me_headphoto.setOnClickListener(this);
        v2_me_name = (TextView) view.findViewById(R.id.v2_me_name);// 用户姓名
        v2_me_level = (ImageView) view.findViewById(R.id.v2_me_level);// 等级
        v2_me_attestation = (TextView) view.findViewById(R.id.v2_me_attestation);// 是否认证
        v2_me_vip = (ImageView) view.findViewById(R.id.v2_me_vip);
        v2_me_ll_account = (LinearLayout) view.findViewById(R.id.v2_me_accountInfo);
        v2_me_ll_myInfo = (LinearLayout) view.findViewById(R.id.v2_me_myInfo);
        v2_me_ll_v2_me_comments = (LinearLayout) view.findViewById(R.id.v2_me_comments);
        v2_me_ll_account.setOnClickListener(this);
        v2_me_ll_myInfo.setOnClickListener(this);
        v2_me_ll_v2_me_comments.setOnClickListener(this);
        v2_me_level.setOnClickListener(this);
        v2_me_headphoto.setOnClickListener(this);

//        ViewUtils.setViewSize(getContext(), v2_me_ll_account, 640, 128);
//        ViewUtils.setViewSize(getContext(), v2_me_ll_v2_me_comments, 640, 128);
        ViewUtils.setViewSize(getContext(), view.findViewById(R.id.head), 280, 220);
        ViewUtils.setViewSize(getContext(), view.findViewById(R.id.v2_me_top), 640, 285);
        ViewUtils.setViewSize(getContext(), view.findViewById(R.id.v2_me_top_bg), 640, 175);
        ViewUtils.setViewSize(getContext(), view.findViewById(R.id.v2_me_top_bg), 640, 175);
        ViewUtils.setViewSize(getContext(), v2_me_headphoto, 220, 220);
        v2_me_headphoto.setBorderWidth(40);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("result", "---------------------------resultCode=" + resultCode + ",requestCode=" + requestCode);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == 0x101) {
                setView();
            }

        }

    }

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {

            case R.id.v2_me_headphoto:
                AuthenticationImage();
                break;
            case R.id.v2_me_accountInfo:
                intent.setClass(getActivity(), User_AccountActivity.class);
                startActivity(intent);
                break;
            case R.id.v2_me_myInfo:
                intent.setClass(getActivity(), UserEditPersonInfoActivity.class);//个人信息
                startActivityForResult(intent, 0x101);
                break;
            case R.id.v2_me_comments:
                intent.setClass(getActivity(), User_CommentActivity.class);
                startActivity(intent);
                break;
            case R.id.v2_me_level:
                intent.setClass(getActivity(), MeLevelInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.title_right_rl:
                intent.setClass(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        getUserData();
    }

    private void getUserData() {

        HttpController controller = new HttpController(getActivity());
        String type = "2";// (1司机 2货主)
        controller.setGetUserGoldListener(this);
        controller.getUserGold(type);
    }

    @Override
    public void getUserGold(UserGoldBean userGoldBean) {
// {"Code":1,"Message":"操作成功","Date":{"level":"骑士","gold":"622","score":"5","balance":"0.00"}}
        UserVerifyUtils.setUserLevelImage(v2_me_level, userGoldBean.getLevel());
        UserVerifyUtils.setUserVipLevel(v2_me_vip, userGoldBean.getVip());

    }


    /**
     *
     */
    private void AuthenticationImage() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        String[] cities = {"拍照", "相册",};
        dialog.setTitle("选择图片");
        dialog.setItems(cities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        if (hasPermission(Manifest.permission.CAMERA)) {
                            selectPicFromCamera();
                        } else {
                            getPermission(Manifest.permission.CAMERA, REQUEST_PERMISSION_CAMERA);
                        }
                        dialog.dismiss();

                        break;
                    case 1:
                        selectPicFromLocal();
                        dialog.dismiss();
                        break;

                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    @Override
    protected void setUpImageView(final String imagePath) {
        ImageLoader.getInstance().loadImage("file://" + imagePath, new SimpleImageLoadingListener() {
            /**
             * @param imageUri
             * @param view
             * @param loadedImage
             */
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                v2_me_headphoto.setImageBitmap(loadedImage);
                Bitmap b = ImageUtils.getSmallBitmap(imagePath);
                try {
                    Image1 = ImageUtils.saveBitmap2File(getContext(), b, System.currentTimeMillis() + ".jpg");
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

    /**
     *
     */
    @Override
    public void updataUserHead() {
        if (Image1.exists() || Image1 != null) {
            Image1.delete();

        }
    }
}
