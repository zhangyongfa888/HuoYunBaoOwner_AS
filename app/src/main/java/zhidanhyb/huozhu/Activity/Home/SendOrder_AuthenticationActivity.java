package zhidanhyb.huozhu.Activity.Home;

import android.Manifest;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import java.io.File;

import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.uploadVerifyListener;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.T;
import zhidanhyb.huozhu.Utils.ViewUtils;
import zhidanhyb.huozhu.View.DialogUtils;

/**
 * 用户进行身份验证页面
 *
 * @author lxj
 */
public class SendOrder_AuthenticationActivity extends BaseActivity implements uploadVerifyListener {
    private Button verify_button;
    private ImageView verify_image2;
    private ImageView verify_image1;
    private Builder dialog;
    private File CardPic;
    private File busiLicensePic;
    // private String path;
    // private Bitmap bitmap;
    /* 头像名称 */
    private static final String PHOTO_FILE_NAME = "temp_authentication.jpg";
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private String Choose = "";// 判断选择的是哪个

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        setContentView(R.layout.sendorder_authenitcationlayout);
        initView();
    }

    /**
     *
     */
    private void initView() {
        setTitleText(R.string.authentication);
        setHideRightButton();
        setLeftButton();
        verify_image1 = (ImageView) findViewById(R.id.sendorder_verify_image1);// 图片1
        verify_image1.setOnClickListener(this);
        verify_image2 = (ImageView) findViewById(R.id.sendorder_verify_image2);// 图片2
        verify_image2.setOnClickListener(this);
        verify_button = (Button) findViewById(R.id.sendorder_verify_button);// 验证
        verify_button.setOnClickListener(this);
        ViewUtils.setViewSize(context, findViewById(R.id.v2_auth_banner), 640, 73);
        ViewUtils.setViewSize(context, verify_image1, 259, 197);
        ViewUtils.setViewSize(context, verify_image2, 259, 197);
        ViewUtils.setViewSize(context, verify_button, 640, 115);
    }

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left_rl:
                DialogUtils.showDialogBack(context, true, "提示", "确认放弃?", "返回", "确认", null, new DialogUtils.setOnDialogRightButtonClick() {
                    @Override
                    public void setOnClickRight(View view) {
                        finish();
                    }
                });
                break;
            case R.id.sendorder_verify_button:// 验证
                Authentication();
                break;
            case R.id.sendorder_verify_image1:// 图片1
                Choose = "1";
                AuthenticationImage();
                break;
            case R.id.sendorder_verify_image2:// 图片2
                Choose = "2";
                AuthenticationImage();
                break;
            default:
                break;
        }
    }

    // 开始验证
    private void Authentication() {
        if (CardPic == null) {
            T.showShort(this, "请上传身份证照片");
            return;
        }
        if (busiLicensePic == null) {
            T.showShort(this, "请上传营业执照照片");
            return;
        }
        HttpController controller = new HttpController(this);
        controller.setUploadVerifyListener(this);
        controller.UploadVerifyImage(this.CardPic, this.busiLicensePic);
    }

    // 选择图片
    private void AuthenticationImage() {
        dialog = new Builder(this);
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

    /**
     * @param imagePath
     */
    @Override
    protected void setUpImageView(final String imagePath) {
        // TODO Auto-generated method stub
        super.setUpImageView(imagePath);


        try {
            ImageLoader.getInstance().loadImage("file://" + imagePath, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    Log.e("bigmapSize", bitmap.getByteCount() + "");
                    if (Choose.equals("1")) {
                        verify_image1.setImageBitmap(bitmap);
                        CardPic = new File(imagePath);

                    } else if (Choose.equals("2")) {
                        verify_image2.setImageBitmap(bitmap);
                        busiLicensePic = new File(imagePath);

                    }

                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if (Choose.equals("1")) {
//				verify_image1.setImageBitmap(bitmap);
//				CardPic= new File(imagePath);

            } else if (Choose.equals("2")) {
//				verify_image2.setImageBitmap(bitmap);
//				busiLicensePic= new File(imagePath);

            }
        }


    }


    /**
     * 通过uri获取图片地址
     *
     * @param
     * @return
     */
    private String uriToPath(Context context, Uri selectedImage) {
        // String[] proj = { MediaStore.Images.Media.DATA };
        // Cursor actualimagecursor = managedQuery(uri,proj,null,null,null);
        // int actual_image_column_index =
        // actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        // actualimagecursor.moveToFirst();
        // return actualimagecursor.getString(actual_image_column_index);

        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                Toast toast = Toast.makeText(context, "选择图片错误！", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            return picturePath;
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                Toast toast = Toast.makeText(context, "无法找到该图片！", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }
            return (file.getAbsolutePath());
        }

    }
//
//	// 根据路径获得图片并压缩，返回bitmap用于显示
//	private Bitmap getSmallBitmap(String filePath) {
//		final BitmapFactory.Options options = new BitmapFactory.Options();
//		options.inJustDecodeBounds = true;
//		BitmapFactory.decodeFile(filePath, options);
//		// Calculate inSampleSize
//		options.inSampleSize = calculateInSampleSize(options, 480, 800);
//		// Decode bitmap with inSampleSize set
//		options.inJustDecodeBounds = false;
//		Bitmap bit = BitmapFactory.decodeFile(filePath, options);
//		// 写入文件
//		File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), getCacheDir().getPath());
//		if (Choose.equals("1")) {
//			tempFile = new File(cacheDir.getPath() + "/Image/", Calendar.getInstance().getTimeInMillis() + ".jpg");
//			CardPic = tempFile;
//		} else if (Choose.equals("2")) {
//			tempFile = new File(cacheDir.getPath() + "/Image/", Calendar.getInstance().getTimeInMillis() + ".jpg");
//			busiLicensePic = tempFile;
//		}
//		try {
//			FileOutputStream out = new FileOutputStream(tempFile);
//			bit.compress(Bitmap.CompressFormat.PNG, 90, out);
//			out.flush();
//			out.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return bit;
//	}
//
//	// 计算图片的缩放值
//	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
//		final int height = options.outHeight;
//		final int width = options.outWidth;
//		int inSampleSize = 1;
//
//		if (height > reqHeight || width > reqWidth) {
//			final int heightRatio = Math.round((float) height / (float) reqHeight);
//			final int widthRatio = Math.round((float) width / (float) reqWidth);
//			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
//		}
//		return inSampleSize;
//	}
    //
    //
    //
    // /**
    // * 剪切图片
    // *
    // * @function:
    // * @author:Jerry
    // * @date:2013-12-30
    // * @param uri
    // */
    // private void crop(Uri uri) {
    // // 裁剪图片意图
    // Intent intent = new Intent("com.android.camera.action.CROP");
    // intent.setDataAndType(uri, "image/*");
    // intent.putExtra("crop", "true");
    // // 裁剪框的比例，1：1
    // intent.putExtra("aspectX", 2);
    // intent.putExtra("aspectY", 1);
    // // 裁剪后输出图片的尺寸大小
    // intent.putExtra("outputX", 500);
    // intent.putExtra("outputY", 500);
    // // 图片格式
    // intent.putExtra("outputFormat", "JPEG");
    // intent.putExtra("noFaceDetection", true);// 取消人脸识别
    // intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
    // File cacheDir =
    // StorageUtils.getOwnCacheDirectory(getApplicationContext(),getCacheDir().getPath());
    // if(Choose.equals("1")){
    // tempFile=new
    // File(cacheDir.getPath()+"/Image/"+Calendar.getInstance().getTimeInMillis()+".jpg");
    // CardPic = tempFile;
    // L.i("CardPic==="+CardPic);
    // }else if(Choose.equals("2")){
    // tempFile=new
    // File(cacheDir.getPath()+"/Image/"+Calendar.getInstance().getTimeInMillis()+".jpg");
    // busiLicensePic = tempFile;
    // L.i("busiLicensePic==="+busiLicensePic);
    // }
    // // File temp = new File("/sdcard/ll1x/");//自已项目 文件夹
    // // if (!temp.exists()) {
    // // temp.mkdir();
    // // }
    // intent.putExtra("output", Uri.fromFile(tempFile)); // 专入目标文件
    // intent.putExtra("outputFormat", "JPEG"); //输入文件格式
    //
    // Intent wrapperIntent = Intent.createChooser(intent, "先择图片"); //开始 并设置标题
    // startActivityForResult(wrapperIntent, PHOTO_REQUEST_CUT);
    // }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        verify_button = null;
        verify_image2 = null;
        verify_image1 = null;
    }

    /**
     *
     */
    @Override
    public void uploadVerify() {
        if (CardPic != null) {
            CardPic = null;
        }
        if (busiLicensePic != null) {
            busiLicensePic = null;
        }
        verify_image1 = null;
        verify_image2 = null;
        verify_button = null;

        DialogUtils.showDialogBack(context, false, "上传成功", "证件正在审核中\n审核完成我们会通知您审核结果", "返回", "", new DialogUtils.setOnDialogLeftButtonClick() {
            @Override
            public void setOnClickLeft(View view) {

                if (SendOrder_LocationActivity.locationactivity != null) {
                    SendOrder_LocationActivity.locationactivity.finish();
                }
                finish();
            }
        }, null);


    }
}
