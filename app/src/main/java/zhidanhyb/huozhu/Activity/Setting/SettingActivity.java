package zhidanhyb.huozhu.Activity.Setting;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.utils.StorageUtils;

import zhidanhyb.huozhu.Base.BaseActivity;
import zhidanhyb.huozhu.Bean.UpdataVersionBean;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.onUpdataVersion;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.AppUtil;
import zhidanhyb.huozhu.Utils.Clear_Cache_PopWindow;
import zhidanhyb.huozhu.Utils.Clear_Cache_PopWindow.onClearCacheListener;
import zhidanhyb.huozhu.Utils.DataCleanManager;
import zhidanhyb.huozhu.Utils.T;
import zhidanhyb.huozhu.Utils.UserExitUtils;
import zhidanhyb.huozhu.View.DialogUtils;
import zhidanhyb.huozhu.View.UpdataVersionDialog;

/**
 * 设置
 *
 * @author lxj
 */
public class SettingActivity extends BaseActivity {
    private LinearLayout suggest_linear;
    /**
     *
     */
    private LinearLayout setting_modify_pwd;
    private LinearLayout updata_linear;
    private LinearLayout clearcache_linear;
    private LinearLayout about_linear;
    private LinearLayout about_quanxian;
    private Button loginout_button;
    private TextView clearcache_textview;
    private TextView version_textview;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.settinglayout);
        initView();
    }

    /**
     *
     */
    private void initView() {
        setHideRightButton();
        setLeftButton();
        setTitleText(R.string.setting);
        suggest_linear = (LinearLayout) findViewById(R.id.setting_suggest_linear);// 意见反馈
        setting_modify_pwd = (LinearLayout) findViewById(R.id.setting_modify_pwd);// 意见反馈
        suggest_linear.setOnClickListener(this);
        setting_modify_pwd.setOnClickListener(this);
        updata_linear = (LinearLayout) findViewById(R.id.setting_updata_linear);// 版本更新
        updata_linear.setOnClickListener(this);
        version_textview = (TextView) findViewById(R.id.setting_version_textview);// 版本
        clearcache_linear = (LinearLayout) findViewById(R.id.setting_clearcache_linear);// 清除缓存
        clearcache_linear.setOnClickListener(this);
        clearcache_textview = (TextView) findViewById(R.id.setting_clearcache_textview);// 缓存
        about_linear = (LinearLayout) findViewById(R.id.setting_about_linear);// 关于我们
        about_quanxian = (LinearLayout) findViewById(R.id.setting_about_quanxian);// 权限
        about_quanxian.setVisibility(View.GONE);
//		if (Build.VERSION.SDK_INT >22) {
//			about_quanxian.setVisibility(View.VISIBLE);
//		}
//
        about_quanxian.setOnClickListener(this);
        about_linear.setOnClickListener(this);
        loginout_button = (Button) findViewById(R.id.setting_loginout_button);// 退出登录
        loginout_button.setOnClickListener(this);
        try {
            long size = DataCleanManager
                    .getFolderSize(StorageUtils.getOwnCacheDirectory(getApplicationContext(), getCacheDir().getPath()));
            clearcache_textview.setText(DataCleanManager.getFormatSize(size));
        } catch (Exception e) {
            e.printStackTrace();
        }
        version_textview.setText(AppUtil.getVersionName(this));
    }

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        super.onClick(v);
        switch (v.getId()) {
            case R.id.title_left_rl:// 返回
                finish();
                break;
            case R.id.setting_modify_pwd:
//
                intent.setClass(this, ModifyActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_suggest_linear:// 意见反馈
                intent.setClass(this, Setting_OpinionActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_updata_linear:// 版本更新
                DialogUtils.getVersion(context);
                break;
            case R.id.setting_clearcache_linear:// 清除缓存
                clear_cache(v);
                break;
            case R.id.setting_about_linear:// 关于我们
                intent.setClass(this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_loginout_button:// 退出登录
//                Exit_Login_Dialog exitDialog = new Exit_Login_Dialog(this, R.style.dialog);
//                exitDialog.exitLogin();
                DialogUtils.showDialogBack(context, true, "退出登录", "是否退出?", "返回", "确定", null, new DialogUtils.setOnDialogRightButtonClick() {
                    /**
                     * @param v
                     */
                    @Override
                    public void setOnClickRight(View v) {
                        new UserExitUtils(context).userExit();
                    }
                });
                break;
            case R.id.setting_about_quanxian:

                // startActivity(new Intent(this, Setting_Permission.class));

                goToPermission(this);
                break;
            default:
                break;
        }
    }

    public static void goToPermission(Context context) {
        // miui
        Intent i = new Intent();
        ComponentName componentName = new ComponentName("com.miui.securitycenter",
                "com.miui.permcenter.permissions.RealAppPermissionsEditorActivity");
        i.setComponent(componentName);
        i.putExtra("extra_pkgname", context.getPackageName());

        try {
            context.startActivity(i);
        } catch (Exception e) {

            Uri packageURI = Uri.parse("package:" + context.getPackageName());
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
            context.startActivity(intent);
//			Intent i2= new Intent();
//            ComponentName componentName2 = new ComponentName("com.android.packageinstaller",
//                    "com.android.packageinstaller.permission.ui.ManagePermissionsActivity");
//            i2.setComponent(componentName2);
//            i2.putExtra("package", context.getPackageName());
//           context.startActivity(i2);
        }

        // huawei

    }

    private void updataVersion() {
        HttpController controller = new HttpController(this);
        controller.setOnUpdataVersion(new onUpdataVersion() {
            @Override
            public void getVersion(UpdataVersionBean updataVersionBean) {
                String version = AppUtil.getVersionName(SettingActivity.this);
                if (updataVersionBean.getVer().toString().equals(version)) {
                    T.showShort(SettingActivity.this, "已是最新版!");
                } else {
                    new UpdataVersionDialog(SettingActivity.this, R.style.dialog).show(updataVersionBean);
                }
            }
        });
        controller.updataVersion();
    }

    private void clear_cache(View v) {
        Clear_Cache_PopWindow clear = new Clear_Cache_PopWindow(this, v);
        clear.setOnClearCacheListener(new onClearCacheListener() {
            @Override
            public void onclearend() {
                try {
                    long size = DataCleanManager.getFolderSize(
                            StorageUtils.getOwnCacheDirectory(getApplicationContext(), getCacheDir().getPath()));
                    clearcache_textview.setText(DataCleanManager.getFormatSize(size));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        version_textview = null;
        clearcache_textview = null;
        loginout_button = null;
        about_linear = null;
        clearcache_linear = null;
        updata_linear = null;
        suggest_linear = null;
    }
}
