package zhidanhyb.huozhu.Base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import cn.jpush.android.api.JPushInterface;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.Constance;
import zhidanhyb.huozhu.Utils.ImageUtils;
import zhidanhyb.huozhu.Utils.L;
import zhidanhyb.huozhu.Utils.SDCardUtils;

/**
 *
 */
public class BaseActivity extends SwipeBackActivity implements OnClickListener, SwipeBackLayout.SwipeListener {

    private TextView titleView = null;
    private ImageView leftButton;

    private ImageView rightButton;
    private TextView titlerighttextview;
    private RelativeLayout title_right_rl;
    private RelativeLayout title_left_rl;
    private Button title_number_button;
    private ScaleAnimation animation;
    private LinearLayout notdatalinear;
    public Context context;

    /**
     *
     */
    protected void setBeforeAddContent() {

    }

    /**
     * @param arg0
     */
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setBeforeAddContent();
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
        AppManager.getAppManager().addActivity(this);
        context = this;
        mSwipeBackLayout = getSwipeBackLayout();
        //设置可以滑动的区域，推荐用屏幕像素的一半来指定
        mSwipeBackLayout.setEdgeSize(200);
        mSwipeBackLayout.setShadow(R.drawable.shadow_left, SwipeBackLayout.EDGE_LEFT);
        mSwipeBackLayout.addSwipeListener(BaseActivity.this);
        //设定滑动关闭的方向，SwipeBackLayout.EDGE_ALL表示向下、左、右滑动均可。EDGE_LEFT，EDGE_RIGHT，EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitleText(int title) {
        titleView = (TextView) findViewById(R.id.title_textview);
        titleView.setText(title);
    }

    public void setTitleText(String title) {
        titleView = (TextView) findViewById(R.id.title_textview);
        titleView.setText(title);
    }

    /**
     * 初始化返回键
     */
    public void setLeftButton() {
        leftButton = (ImageView) findViewById(R.id.title_left_button);
        title_left_rl = (RelativeLayout) findViewById(R.id.title_left_rl);
        title_left_rl.setOnClickListener(this);
        leftButton.setBackgroundResource(R.drawable.v2_back);
    }

    /**
     * 初始化右键
     */
    public void setRightButton(int back) {
        rightButton = (ImageView) findViewById(R.id.title_right_button);
        title_right_rl = (RelativeLayout) findViewById(R.id.title_right_rl);
        title_right_rl.setOnClickListener(this);
        rightButton.setVisibility(View.VISIBLE);
        rightButton.setBackgroundResource(back);
    }

    /**
     * 隐藏右键
     */
    public void setHideRightButton() {
        rightButton = (ImageView) findViewById(R.id.title_right_button);
        if (rightButton != null) {
            rightButton.setVisibility(View.GONE);
        }
    }

    /**
     * 隐藏左键
     */
    public void setHideLeftButton() {
        leftButton = (ImageView) findViewById(R.id.title_left_button);
        if (leftButton != null) {
            leftButton.setVisibility(View.GONE);
        }
    }

    /**
     * @param text
     */
    public void setRightText(int text) {
        if (titlerighttextview == null) {
            titlerighttextview = (TextView) findViewById(R.id.title_right_textview);
        }
        titlerighttextview.setText(text);
    }

    /**
     * @return
     */
    public TextView getRightText() {
        if (titlerighttextview == null) {
            titlerighttextview = (TextView) findViewById(R.id.title_right_textview);
        }
        Log.e("titlerighttextview", titlerighttextview.getText().toString() + "");
        return titlerighttextview;
    }

    public void setRightText(String text) {
        if (titlerighttextview == null) {
            titlerighttextview = (TextView) findViewById(R.id.title_right_textview);
        }
        titlerighttextview.setText(text);
        titlerighttextview.setOnClickListener(this);
    }

    public void setRightNumberButton(int number) {
        if (title_number_button == null) {
            title_number_button = (Button) findViewById(R.id.title_number_button);
        }
        if (number == 0) {
            title_number_button.setVisibility(View.GONE);
            title_number_button.setText("");
            title_number_button.setBackgroundResource(R.color.transparent);
            return;
        } else if (number > 0) {
            title_number_button.setBackgroundResource(R.drawable.orange_circle);
            title_number_button.setVisibility(View.VISIBLE);
        }
        if (number >= 10 && number < 99) {
            title_number_button.setTextSize(9);
        } else if (number < 10) {
            title_number_button.setTextSize(13);
        } else if (number >= 99) {
            title_number_button.setTextSize(9);
            number = 99;
        }
        if (animation == null) {
            animation = new ScaleAnimation(0.3f, 1.0f, 0.3f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setFillAfter(true);
            animation.setDuration(100);// 设置动画持续时间
        }
        title_number_button.startAnimation(animation);

        title_number_button.setText(number + "+");
    }

    /**
     * 如果列表没有数据就显示
     */
    public void showNotData() {
        notdatalinear = (LinearLayout) findViewById(R.id.notdatalinear);
        if (notdatalinear != null) {
            notdatalinear.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 如果列表有数据就隐藏
     */
    public void hideNotData() {
        notdatalinear = (LinearLayout) findViewById(R.id.notdatalinear);
        if (notdatalinear != null) {
            notdatalinear.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left_rl:
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

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
            if (!(checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)) {
                if (shouldShowRequestPermissionRationale(permission)) {
                    Toast.makeText(BaseActivity.this, "请允许货运宝获取权限", Toast.LENGTH_LONG).show();
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

    String imagePath;

    public static final int REQUEST_CODE_CAMERA = 0X2;
    public static final int REQUEST_CODE_LOCAL = 0X3;
    public static final int REQUEST_PERMISSION_CAMERA = 0x4;
    public static final int REQUEST_PERMISSION_LOCATION = 0x5;
    public static final int REQUEST_PERMISSION_RECORD = 0x6;
    public static final int REQUEST_PERMISSION_Storage = 0x8;
    public static final int REQUEST_PERMISSION_PhoneState = 0x9;
    public static final int PHOTO_REQUEST_CUT = 0x7;

    //
    // /**
    // * capture new image
    // */
    // protected void selectPicFromCamera() {
    //
    // if (!SDCardUtils.isSDCardEnable()) {
    // Toast.makeText(BaseActivity.this, "内存卡不可用！", Toast.LENGTH_SHORT).show();
    // return;
    // }
    //
    // cameraFile = new File(ImageUtils.path, System.currentTimeMillis() +
    // ".jpg");
    // cameraFile.getParentFile().mkdirs();
    // startActivityForResult(
    // new
    // Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT,
    // Uri.fromFile(cameraFile)),
    // REQUEST_CODE_CAMERA);
    // }
    //
    protected void setUpImageView(String imagePath) {
    }
    //
    // /**
    // * select local image
    // */
    // protected void selectPicFromLocal() {
    // Intent intent;
    // // 创建一个临时URItempURI
    // cameraFile = new File(ImageUtils.path, System.currentTimeMillis() +
    // ".jpg");
    // cameraFile.getParentFile().mkdirs();
    // if (Build.VERSION.SDK_INT < 19) {
    // intent = new Intent(Intent.ACTION_GET_CONTENT);
    // intent.setType("image/*");
    //
    // } else {
    //
    // intent = new Intent(Intent.ACTION_PICK,
    // android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    // }
    // startActivityForResult(intent, REQUEST_CODE_LOCAL);
    // }


    Uri imageUri;

    /**
     * capture new image
     */
    protected void selectPicFromCamera() {
        if (hasPermission(Manifest.permission.CAMERA)) {
            if (!SDCardUtils.isSDCardEnable()) {
                Toast.makeText(BaseActivity.this, "内存卡不可用！", Toast.LENGTH_SHORT).show();
                return;
            }

            cameraFile = new File(ImageUtils.path, System.currentTimeMillis() + ".jpg");
            cameraFile.getParentFile().mkdirs();
            imageUri = Uri.fromFile(cameraFile);
            startActivityForResult(
                    new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, imageUri),
                    REQUEST_CODE_CAMERA);

        } else {
            getPermission(Manifest.permission.CAMERA, REQUEST_PERMISSION_CAMERA);
        }

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CAMERA) { // capture new image

                if (cameraFile != null && cameraFile.exists()) {
                    // crop(imageUri);
                    setUpImageView(ImageUtils.getPathByUri(this, imageUri));

                }

            } else if (requestCode == REQUEST_CODE_LOCAL) { // send local image
                if (data != null) {
                    Uri selectedImage = data.getData();
                    Log.d("data", data + "");
                    // crop(selectedImage);
                    setUpImageView(ImageUtils.getPathByUri(this, selectedImage));

                }
            } else if (requestCode == PHOTO_REQUEST_CUT) {
                setUpImageView(ImageUtils.getPathByUri(this, imageUri));

            }

        }
    }

    // // 获取照片后返回结果
    // @Override
    // public void onActivityResult(int requestCode, int resultCode, Intent
    // data) {
    // super.onActivityResult(requestCode, resultCode, data);
    // if (resultCode == Activity.RESULT_OK) {
    // if (requestCode == REQUEST_CODE_CAMERA) { // capture new image
    //
    // if (cameraFile != null && cameraFile.exists())
    // Log.e("data", cameraFile.getAbsolutePath());
    //
    // //// crop(Uri.fromFile(cameraFile));
    // //
    // // // 截图
    // // setUpImageView(ImageUtils.getPathByUri(BaseActivity.this,
    // // Uri.fromFile(cameraFile)));
    // try {
    // Bitmap b1 = ImageUtils.getSmallBitmap(ImageUtils.getPathByUri(this,
    // Uri.fromFile(cameraFile)));
    // String path;
    // path = ImageUtils.saveBitmap(b1, System.currentTimeMillis() + ".jpg");
    // setUpImageView(path);
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    //
    // } else if (requestCode == REQUEST_CODE_LOCAL) { // send local image
    // // if (data != null) {
    // // Uri selectedImage = data.getData();
    // //// crop(selectedImage);
    // //
    // // setUpImageView(ImageUtils.getPathByUri(BaseActivity.this,
    // // selectedImage));
    // //
    // // }
    //
    // if (data.hasExtra("data")) {
    // String path = null;
    // Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
    // try {
    // path = ImageUtils.saveBitmap(thumbnail, System.currentTimeMillis() +
    // ".jpg");
    // setUpImageView(path);
    //
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    //
    // } else {
    // if (data.getData() != null) {
    // Bitmap b = ImageUtils.getSmallBitmap(ImageUtils.getPathByUri(this,
    // data.getData()));
    // String path;
    // try {
    // path = ImageUtils.saveBitmap(b, System.currentTimeMillis() + ".jpg");
    // setUpImageView(path);
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // } else {
    // }
    //
    // }
    // } else if (requestCode == PHOTO_REQUEST_CUT) {
    // // if (data.hasExtra("data")) {
    // // String path = null;
    // // Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
    // // try {
    // // path = ImageUtils.saveBitmap(thumbnail,
    // // System.currentTimeMillis() + ".jpg");
    // // setUpImageView(path);
    // //
    // // } catch (IOException e) {
    // // e.printStackTrace();
    // // }
    // //
    // // } else {
    // // if (data.getData() != null) {
    // // Bitmap b =
    // // ImageUtils.getSmallBitmap(ImageUtils.getPathByUri(this,
    // // data.getData()));
    // // String path;
    // // try {
    // // path = ImageUtils.saveBitmap(b, System.currentTimeMillis() +
    // // ".jpg");
    // // setUpImageView(path);
    // // } catch (IOException e) {
    // // e.printStackTrace();
    // // }
    // // } else {
    // // }
    // //
    // // }
    //
    // //
    // // String path = null;
    // // Log.e("data CUT", data + "");
    // // Bitmap bitmap = data.getParcelableExtra("data");
    // //
    // // try {
    // // path= ImageUtils.saveBitmap(bitmap,
    // // System.currentTimeMillis()+".jpg");
    // //
    // //
    // // } catch (IOException e) {
    // // // TODO Auto-generated catch block
    // // e.printStackTrace();
    // // }
    // //
    // // setUpImageView(path);
    // }
    //
    // }
    // }
    SwipeBackLayout mSwipeBackLayout;

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
        if (Build.VERSION.SDK_INT > 19) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
        }
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
                Toast.makeText(BaseActivity.this, "您已经拒绝了使用相机的权限", Toast.LENGTH_LONG).show();


            }
        }

        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == 0) {

            } else {
                Toast.makeText(BaseActivity.this, "您已经拒绝了共享位置信息的权限 ", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onScrollStateChange(int state, float scrollPercent) {

    }

    @Override
    public void onEdgeTouch(int edgeFlag) {

    }

    @Override
    public void onScrollOverThreshold() {

    }
}
