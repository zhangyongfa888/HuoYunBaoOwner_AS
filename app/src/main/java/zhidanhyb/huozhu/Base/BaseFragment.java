package zhidanhyb.huozhu.Base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.Constance;
import zhidanhyb.huozhu.Utils.ImageUtils;
import zhidanhyb.huozhu.Utils.L;
import zhidanhyb.huozhu.Utils.SDCardUtils;

@SuppressLint("ResourceAsColor")
public class BaseFragment extends Fragment implements OnClickListener {

    private TextView titleView = null;
    private ImageView leftButton;

    private ImageView rightButton;
    protected View view;
    private RelativeLayout title_left_rl;
    private RelativeLayout title_right_rl;
    private TextView titlerighttextview;
    private LinearLayout notdatalinear;

    /**
     * 请求权限
     */
    @SuppressLint("NewApi")
    public void getPermission(String permission, int reqCode) {
        requestPermissions(new String[]{permission}, reqCode);

    }

    @SuppressLint("NewApi")
    public void getPermissions(String[] permission, int reqCode) {
        requestPermissions(permission, reqCode);

    }

    /**
     * 判断是不是有权限
     */
    @SuppressLint("NewApi")
    public boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(getContext().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)) {
                if (((Activity) getContext()).shouldShowRequestPermissionRationale(permission)) {
                    Toast.makeText(getContext(), "请允许货运宝获取权限", Toast.LENGTH_LONG).show();
                    return false;
                }
            } else {
                L.i("RECORD_AUDIO PERMISSION", "onClick granted");
                return true;

            }
        } else {
            return true;
        }
        return false;
    }

    public File cameraFile;

    public static final int REQUEST_CODE_CAMERA = 0X2;
    public static final int REQUEST_CODE_LOCAL = 0X3;
    public static final int REQUEST_PERMISSION_CAMERA = 0x4;
    public static final int REQUEST_PERMISSION_LOCATION = 0x5;
    public static final int REQUEST_PERMISSION_RECORD = 0x6;
    public static final int PHOTO_REQUEST_CUT = 0x7;
    public static final int REQUEST_PERMISSION_Storage = 0x8;

    /**
     * capture new image
     */
    protected void selectPicFromCamera() {
        if (hasPermission(Manifest.permission.CAMERA)) {
            if (!SDCardUtils.isSDCardEnable()) {
                Toast.makeText(getActivity(), "内存卡不可用！", Toast.LENGTH_SHORT).show();
                return;
            }

            cameraFile = new File(ImageUtils.path, System.currentTimeMillis() + ".jpg");
            cameraFile.getParentFile().mkdirs();
            // Uri表标识着图片的地址
            imageUri = Uri.fromFile(cameraFile);
            startActivityForResult(
                    new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, imageUri),
                    REQUEST_CODE_CAMERA);
        } else {
            getPermission(Manifest.permission.CAMERA, REQUEST_PERMISSION_CAMERA);
        }

    }

    Uri imageUri;

    protected void setUpImageView(String imagePath) {
    }

    /**
     * select local image
     */
    protected void selectPicFromLocal() {

        if (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                && hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Intent intent;
            cameraFile = new File(ImageUtils.path, System.currentTimeMillis() + ".jpg");
            cameraFile.getParentFile().mkdirs();
            imageUri = Uri.fromFile(cameraFile);

            if (Build.VERSION.SDK_INT < 19) {
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

            } else {
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

            }
            startActivityForResult(intent, REQUEST_CODE_LOCAL);
        } else {
            getPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_Storage);

        }

    }

    // 获取照片后返回结果
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CAMERA) { // capture new image
                if (cameraFile != null && cameraFile.exists())
                    L.e("data CAMERA", data + "");
                // setUpImageView(ImageUtils.getPathByUri(getContext(),
                // Uri.fromFile(cameraFile)));
                crop(Uri.fromFile(cameraFile));

            } else if (requestCode == REQUEST_CODE_LOCAL) { // send local image
                if (data != null) {
                    Uri selectedImage = data.getData();
                    crop(selectedImage);
                    // setUpImageView(ImageUtils.getPathByUri(getContext(),
                    // selectedImage));

                }
            } else if (requestCode == PHOTO_REQUEST_CUT) {
                // String path = null;
                setUpImageView(ImageUtils.getPathByUri(getActivity(), imageUri));
            }

        }
    }

    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");

        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", Constance.pic_x);
        intent.putExtra("aspectY", Constance.pic_y);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", Constance.pic_x_width);
        intent.putExtra("outputY", Constance.pic_y_width);
        // 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", false);// true:不返回uri，false：返回uri
        intent.putExtra("outputFormat", "JPEG"); // 输入文件格式
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        Intent wrapperIntent = Intent.createChooser(intent, "选择图片"); // 开始 并设置标题
        startActivityForResult(wrapperIntent, PHOTO_REQUEST_CUT);
    }

    // 获取权限返回结果
    @SuppressLint("NewApi")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (grantResults[0] == 0) {
                selectPicFromCamera();
            } else {
                Toast.makeText(getActivity(), "您已经拒绝了使用相机的权限", Toast.LENGTH_LONG).show();

            }
        }

        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == 0) {

            } else {
                Toast.makeText(getActivity(), "您已经拒绝了共享位置信息的权限", Toast.LENGTH_LONG).show();

            }

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view;
    }

    /**
     * 设置标题
     *
     * @param
     */
    public void setTitleText(int text) {
        titleView = (TextView) view.findViewById(R.id.title_textview);
        titleView.setText(text);
    }

    public String getTitleText() {
        titleView = (TextView) view.findViewById(R.id.title_textview);
        return titleView.getText().toString();
    }

    /**
     * @return
     */
    public String getRightText() {
        titlerighttextview = (TextView) view.findViewById(R.id.title_right_textview);
        return titlerighttextview.getText().toString();
    }

    /**
     * 初始化返回键
     */
    public void setLeftButton() {
        leftButton = (ImageView) view.findViewById(R.id.title_left_button);
        title_left_rl = (RelativeLayout) view.findViewById(R.id.title_left_rl);
        title_left_rl.setOnClickListener(this);
        leftButton.setBackgroundResource(R.drawable.back);
    }

    /**
     * 初始化右键
     * @param back
     */
    public void setRightButton(int back) {
        rightButton = (ImageView) view.findViewById(R.id.title_right_button);
        title_right_rl = (RelativeLayout) view.findViewById(R.id.title_right_rl);
        title_right_rl.setOnClickListener(this);
        rightButton.setVisibility(View.VISIBLE);
        rightButton.setBackgroundResource(back);
    }

    /**
     * 隐藏右键
     */
    public void setHideRightButton() {
        rightButton = (ImageView) view.findViewById(R.id.title_right_button);
        if (rightButton != null) {
            rightButton.setVisibility(View.GONE);
        }
    }

    /**
     * 隐藏左键
     */
    public void setHideLeftButton() {
        leftButton = (ImageView) view.findViewById(R.id.title_left_button);
        if (leftButton != null) {
            leftButton.setVisibility(View.GONE);
        }
    }

    public void setRightText(int text) {
        titlerighttextview = (TextView) view.findViewById(R.id.title_right_textview);
        title_right_rl = (RelativeLayout) view.findViewById(R.id.title_right_rl);
        title_right_rl.setVisibility(View.VISIBLE);
        title_right_rl.setOnClickListener(this);
        titlerighttextview.setText(text);
    }

    public void setHideRightText() {
        if (title_right_rl != null) {
            title_right_rl.setVisibility(View.GONE);
        }
    }

    /**
     * 如果列表没有数据就显示
     */
    public void showNotData() {
        notdatalinear = (LinearLayout) view.findViewById(R.id.notdatalinear);
        if (notdatalinear != null) {
            notdatalinear.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 如果列表有数据就隐藏
     */
    public void hideNotData() {
        notdatalinear = (LinearLayout) view.findViewById(R.id.notdatalinear);
        if (notdatalinear != null) {
            notdatalinear.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
