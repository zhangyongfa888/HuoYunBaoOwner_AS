package zhidanhyb.huozhu.Utils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import zhidanhyb.huozhu.Activity.Home.SendOrder_AuthenticationActivity;
import zhidanhyb.huozhu.Activity.Order.SendOrderActivity;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.View.DialogUtils;

/**
 * 用户认证
 *
 * @author lxj
 */
public class UserVerifyUtils {
    /**
     * 用户验证
     *
     * @param t       - view
     * @param status  - 状态码 状态0初始 1文件上传未审核 2审核通过 9未通过
     * @param context
     */
    public static void userVerify(TextView t, String status, final Context context) {
        if (t == null) {
            return;
        }
        String type = "";
        int stat;
        try {
            stat = Integer.parseInt(status);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            stat = 0;
        }
        switch (stat) {
            case 0:
                type = "[未认证]";
                break;
            case 1:
                type = "[待审核]";
                break;
            case 2:
                type = "[已认证]";
                break;
            case 9:
                type = "[未通过]";
                break;
            default:
                break;
        }
        t.setText(type);
        if (stat == 2) {
            t.setVisibility(View.INVISIBLE);
        }
        final int finalStat = stat;
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalStat == 0 || finalStat == 9) {
                    context.startActivity(new Intent(context, SendOrder_AuthenticationActivity.class));
                }
            }
        });
    }

    /**
     *
     */

    /**
     * 用户验证
     *
     * @param context
     * @param status  - 状态码 状态0初始 1文件上传未审核 2审核通过 9未通过
     */
    public static void VerifyImage(final Context context, String status) {
        switch (Integer.parseInt(status)) {
            case 0:
                DialogUtils.showDialogBack(context, false, "您尚未验证", context.getResources().getString(R.string.verificationhint),
                        "返回", "验证", null, new DialogUtils.setOnDialogRightButtonClick() {
                            /**
                             * @param v
                             */
                            @Override
                            public void setOnClickRight(View v) {

                                context.startActivity(new Intent(context, SendOrder_AuthenticationActivity.class));
                            }
                        });
//			User_VerificationDialog dialog = new User_VerificationDialog(context, R.style.dialog);
//			dialog.showDialog();
                break;
            case 1:
                T.showShort(context, "证件照已上传,待审核!");
                break;
            case 2:
                context.startActivity(new Intent(context, SendOrderActivity.class));
                break;
            case 9:
                T.showShort(context, "审核未通过,请重新上传证件照");

                DialogUtils.showDialogBack(context, false, "审核未通过", context.getResources().getString(R.string.verificationhint),
                        "返回", "验证", null, new DialogUtils.setOnDialogRightButtonClick() {
                            /**
                             * @param v
                             */
                            @Override
                            public void setOnClickRight(View v) {
                                context.startActivity(new Intent(context, SendOrder_AuthenticationActivity.class));

                            }
                        });

//
// User_VerificationDialog dialog1 = new User_VerificationDialog(context, R.style.dialog);
//			dialog1.showDialog();
                break;
            default:
                break;
        }
    }

    /**
     * 根据等级的不同更换图片
     *
     * @param imageview
     * @param level     1排长2连长3营长4团长5旅长6师长7军长
     */
    public static void setDriverLevelImage(ImageView imageview, String level) {
        if (level.toString().equals("1")) {
            imageview.setImageResource(R.drawable.sj1);
        } else if (level.toString().equals("2")) {
            imageview.setImageResource(R.drawable.sj2);
        } else if (level.toString().equals("3")) {
            imageview.setImageResource(R.drawable.sj3);
        } else if (level.toString().equals("4")) {
            imageview.setImageResource(R.drawable.sj4);
        } else if (level.toString().equals("5")) {
            imageview.setImageResource(R.drawable.sj5);
        } else if (level.toString().equals("6")) {
            imageview.setImageResource(R.drawable.sj6);
        } else if (level.toString().equals("7")) {
            imageview.setImageResource(R.drawable.sj7);
        }
    }

    /**
     * 根据等级的不同更换图片
     *
     * @param imageview
     * @param level     1骑士2爵士3男爵4子爵5伯爵6侯爵7公爵
     */
    public static void setUserLevelImage(ImageView imageview, String level) {
        if (level.toString().equals("骑士")) {
            imageview.setImageResource(R.drawable.hz1);
        } else if (level.toString().equals("爵士")) {
            imageview.setImageResource(R.drawable.hz2);
        } else if (level.toString().equals("男爵")) {
            imageview.setImageResource(R.drawable.hz3);
        } else if (level.toString().equals("子爵")) {
            imageview.setImageResource(R.drawable.hz4);
        } else if (level.toString().equals("伯爵")) {
            imageview.setImageResource(R.drawable.hz5);
        } else if (level.toString().equals("侯爵")) {
            imageview.setImageResource(R.drawable.hz6);
        } else if (level.toString().equals("公爵")) {
            imageview.setImageResource(R.drawable.hz7);
        }
    }

    /**
     * @param imageview
     * @param level
     */
    public static void setUserVipLevel(ImageView imageview, String level) {
        if (level.equals("1")) {
            imageview.setImageResource(R.drawable.v2_v1);
        } else if (level.equals("2")) {
            imageview.setImageResource(R.drawable.v2_v2);
        } else if (level.equals("3")) {
            imageview.setImageResource(R.drawable.v2_v3);
        } else if (level.equals("4")) {
            imageview.setImageResource(R.drawable.v2_v4);
        } else if (level.equals("5")) {
            imageview.setImageResource(R.drawable.v2_v5);
        } else if (level.equals("6")) {
            imageview.setImageResource(R.drawable.v2_v6);
        } else if (level.equals("7")) {
            imageview.setImageResource(R.drawable.v2_v7);
        }
    }

}
