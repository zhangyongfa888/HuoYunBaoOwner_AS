package zhidanhyb.huozhu.View;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import zhidanhyb.huozhu.Activity.Order.OrderDetailsActivity;
import zhidanhyb.huozhu.Bean.UpdataVersionBean;
import zhidanhyb.huozhu.Config.ConstantConfig;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.AppUtil;
import zhidanhyb.huozhu.Utils.KeyboradUtils;
import zhidanhyb.huozhu.Utils.L;
import zhidanhyb.huozhu.Utils.StringUtil;
import zhidanhyb.huozhu.Utils.T;
import zhidanhyb.huozhu.Utils.UpdataApk_Service;
import zhidanhyb.huozhu.Utils.ViewUtils;

/**
 * 应用内所有提示dialog
 *
 * @author lxj
 */
public class DialogUtils {

    /**
     * @param context
     * @param positive
     * @param title
     * @param desc
     * @param leftStr
     * @return
     */
    public static Dialog showDialogBack(Context context, boolean positive, String title, String desc,
                                        String leftStr) {
        return showDialogBack(context, positive, title, desc, leftStr, "", null, null);


    }


    /**
     * @param context
     * @param positive
     * @param title
     * @param desc
     * @param leftStr
     * @param rightStr
     * @param left
     * @param right
     * @return
     */
    public static Dialog showDialogBack(Context context, boolean positive, int title, int desc,
                                        int leftStr, int rightStr,
                                        final setOnDialogLeftButtonClick left, final setOnDialogRightButtonClick right) {
        return showDialogBack(context, positive, context.getResources().getString(title), context.getResources().getString(desc),
                context.getResources().getString(leftStr), context.getResources().getString(rightStr), left, right);

    }

    /**
     * @param context
     * @param positive
     * @param title
     * @param customView
     * @param leftStr
     * @param rightStr
     * @param left
     * @param right
     * @return
     */
    public static Dialog showDialogCustomView(Context context, boolean positive, final String title, View customView, String leftStr, String rightStr,
                                              final setOnDialogLeftButtonClick left, final setOnDialogRightButtonClick right) {

        final Dialog dialog = new Dialog(context, R.style.dialog_v2);
        View view = View.inflate(context, R.layout.v2_back_dialog, null);
        LinearLayout v2_dialog_ll_title = (LinearLayout) view.findViewById(R.id.v2_dialog_ll_title);


        ViewUtils.setViewSize(context, v2_dialog_ll_title, 640, 72);
        if (positive) {
//设置背景
        } else {
            v2_dialog_ll_title.setBackgroundResource((R.drawable.v2_dialog_error_bg));
        }
        TextView tv_title = (TextView) view.findViewById(R.id.v2_dialog_title);
        LinearLayout custom = (LinearLayout) view.findViewById(R.id.dialog_custom_view);
        TextView tv_left = (TextView) view.findViewById(R.id.v2_dialog_left);
        TextView tv_right = (TextView) view.findViewById(R.id.v2_dialog_right);

//        tv_desc.setText(desc);
        custom.removeAllViews();
        custom.addView(customView);

        tv_title.setText(title);
        tv_left.setText(leftStr);
        if (left != null) {
            tv_left.setOnClickListener(new View.OnClickListener() {
                /**
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                    left.setOnClickLeft(v);
                }
            });
        } else {
            tv_left.setOnClickListener(new View.OnClickListener() {
                /**
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (StringUtil.isEmpty(rightStr)) {
            view.findViewById(R.id.v2_dialog_ll_right).setVisibility(View.GONE);
        } else {
            tv_right.setText(rightStr);
            if (right != null) {
                tv_right.setOnClickListener(new View.OnClickListener() {
                    /**
                     * @param v
                     */
                    @Override
                    public void onClick(View v) {

                        if (title.contains("对司机评价") || title.contains("付款详情") || title.contains("兑换金币")) {
                            right.setOnClickRight(v);
                        } else {
                            dialog.dismiss();
                            right.setOnClickRight(v);
                        }
                    }
                });
            }
        }


        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        return dialog;
    }

    /**
     * @param context
     * @param positive 是否亮色
     * @param title    标题
     * @param desc     提示内容
     * @param leftStr  左侧按钮内容
     * @param rightStr 右侧按钮内容
     * @param left     左侧按钮点击事件
     * @param right    右侧按钮点击事件
     * @return
     */
    public static Dialog showDialogBack(Context context, boolean positive, String title, String desc,
                                        String leftStr, String rightStr,
                                        final setOnDialogLeftButtonClick left, final setOnDialogRightButtonClick right) {
        final Dialog dialog = new Dialog(context, R.style.dialog_v2);
        View view = View.inflate(context, R.layout.v2_back_dialog, null);
        LinearLayout v2_dialog_ll_title = (LinearLayout) view.findViewById(R.id.v2_dialog_ll_title);
        ViewUtils.setViewSize(context, v2_dialog_ll_title, 640, 72);
        if (positive) {
//设置背景
        } else {
            v2_dialog_ll_title.setBackgroundResource((R.drawable.v2_dialog_error_bg));
        }
        TextView tv_title = (TextView) view.findViewById(R.id.v2_dialog_title);
        TextView tv_desc = (TextView) view.findViewById(R.id.v2_dialog_desc);
        Button tv_left = (Button) view.findViewById(R.id.v2_dialog_left);
        Button tv_right = (Button) view.findViewById(R.id.v2_dialog_right);

        tv_desc.setText(desc);
        tv_title.setText(title);

        tv_left.setText(leftStr);
        if (left != null) {
            tv_left.setOnClickListener(new View.OnClickListener() {
                /**
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    left.setOnClickLeft(v);
                }
            });
        } else {
            tv_left.setOnClickListener(new View.OnClickListener() {
                /**
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (StringUtil.isEmpty(rightStr)) {
            view.findViewById(R.id.v2_dialog_ll_right).setVisibility(View.GONE);
        } else {
            tv_right.setText(rightStr);
            if (right != null) {
                tv_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        right.setOnClickRight(v);
                    }
                });
            }
        }


        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        return dialog;
    }


    static float rat;

    /**
     * 评价司机
     *
     * @param mContext
     * @param orderID
     * @param Price
     */
    public static void showComentDialog(final Context mContext, final String orderID, String Price) {

//        new CommentDialog(mContext, R.style.dialog).showDialog(string, string2);
        View comment = View.inflate(mContext, R.layout.commentlayout, null);
        TextView oriderid_textview = (TextView) comment.findViewById(R.id.comment_oriderid_textview);//订单ID号
        TextView money_textview = (TextView) comment.findViewById(R.id.comment_money_textview);//总运费
        RatingBar star_ratingbar = (RatingBar) comment.findViewById(R.id.comment_star_ratingbar);//星级
        final EditText content_edittext = (EditText) comment.findViewById(R.id.comment_content_edittext);//编辑评价内容
        star_ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            /**
             * @param ratingBar
             * @param rating
             * @param fromUser
             */
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rat = rating;
            }
        });
        oriderid_textview.setText("单号 " + orderID + " 司机已确认收款");
        money_textview.setText(Price);
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = DialogUtils.showDialogCustomView(mContext, true, "请对司机评价", comment, "返回", "确定", null, new DialogUtils.setOnDialogRightButtonClick() {
            /**
             * @param view
             */
            @Override
            public void setOnClickRight(final View view) {
                if (view != null) {
                    view.setClickable(false);
                }
                if (!content_edittext.getText().toString().trim().isEmpty()) {
                    if (orderID != null) {
                        HttpController controller = new HttpController(mContext);
                        String star = rat + "";
                        String content = content_edittext.getText().toString().trim();
                        controller.setOnCommentListener(new HttpController.onCommentListener() {
                            /**
                             *
                             */
                            @Override
                            public void onComment() {
                                if (view != null) {
                                    view.setClickable(true);
                                }
                                if (ConstantConfig.isOrderDetails) {
                                    if (OrderDetailsActivity.detailsActivity != null) {
                                        OrderDetailsActivity.detailsActivity.getData();
                                    }
                                }
                                if (ConstantConfig.isOrderList) {
                                    Intent intent = new Intent(ConstantConfig.updataOrderList);
                                    mContext.sendBroadcast(intent);
                                }
                                dialog.dismiss();
                            }
                        });
                        controller.OwnerCommentDriver(star, content, orderID);
                    }
                } else {
                    if (view != null) {
                        view.setClickable(true);
                    }


                    T.showShort(mContext, "请为司机进行评价");
                }

            }
        });

    }

    /**
     * 交换金币返回成功的监听
     */
    public interface ExchangeListener {
        void exchangeSuccess();
    }

    /**
     * @param context
     * @param banlance
     * @param exchangeListener
     */
    public static void showExchangeGoldDialog(final Context context, String banlance, final ExchangeListener exchangeListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_converison_gold_layout, null);

        final EditText gold_edittext = (EditText) view.findViewById(R.id.converison_gold_edittext);//编辑金币数量
        final TextView gold_textview = (TextView) view.findViewById(R.id.converison_gold_textview);//需要金币数
        TextView gold_balance_textview = (TextView) view.findViewById(R.id.converison_gold_balance_textview);//账户余额/￥15000
        if (!banlance.equals("")) {

            gold_balance_textview.setText("￥" + banlance);
        } else {
            T.showShort(context, "账户余额不足！");
            return;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //设置可获得焦点
                gold_edittext.setFocusable(true);
                gold_edittext.setFocusableInTouchMode(true);
                //请求获得焦点
                gold_edittext.requestFocus();
                KeyboradUtils.ShowKeyboard(gold_edittext);
            }
        }, 200);


        gold_edittext.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//				int gold = Integer.parseInt(s.toString().trim());
                gold_textview.setText("需要" + s.toString().trim() + "元");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = showDialogCustomView(context, true, "兑换金币", view, "返回", "确认", null, new setOnDialogRightButtonClick() {
            /**
             * @param view
             */
            @Override
            public void setOnClickRight(View view) {
                if (gold_edittext.getText().toString().trim().isEmpty()) {
                    T.showShort(context, R.string.conversionglod);
                    return;
                }
                HttpController controller = new HttpController(context);
                controller.setConversionGoldListener(new HttpController.conversionGoldListener() {
                    @Override
                    public void conversion() {
                        exchangeListener.exchangeSuccess();
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });
                controller.conversionGold("2", gold_edittext.getText().toString().trim());
            }
        });

    }

    /**
     *
     */
    public interface setOnDialogRightButtonClick {
        void setOnClickRight(View view);
    }

    /**
     *
     */
    public interface setOnDialogLeftButtonClick {
        void setOnClickLeft(View view);
    }

    /**
     *
     */
    private static Dialog dialog;

    /**
     * @param context
     */
    public static void getVersion(final Context context) {
        HttpController controller = new HttpController(context);
        controller.setOnUpdataVersion(new HttpController.onUpdataVersion() {
            /**
             * @param updataVersionBean
             */
            @Override
            public void getVersion(final UpdataVersionBean updataVersionBean) {
                if (updataVersionBean == null) {
                    return;
                }
                String version = AppUtil.getVersionName(context);
                if (updataVersionBean.getVer().equals(version)) {
                    L.e("version", updataVersionBean.toString());
                } else {
                    String is_ConstraintUpdata = updataVersionBean.getIs_updata();
                    final String url = updataVersionBean.getUrl();
                    if (is_ConstraintUpdata != null) {
                        if (is_ConstraintUpdata.equals("0")) {//不强制更新并显示更新提示
                            dialog = DialogUtils.showDialogBack(context, true, "发现新版本", updataVersionBean.getMsg(), "下次再说", "更新", null, new setOnDialogRightButtonClick() {
                                /**
                                 * @param view
                                 */
                                @Override
                                public void setOnClickRight(View view) {
                                    if (url != null) {
                                        ConstantConfig.Url = url;
                                        //通知服务去后台下载
                                        context.startService(new Intent(context, UpdataApk_Service.class).setPackage("zhidanhyb.huozhu"));
                                        T.showLong(context, "正在后台下载!");
                                        dialog.dismiss();
                                    }
                                }
                            });
                        } else if (is_ConstraintUpdata.equals("1")) {//强制更新不显示提示
                            if (url != null) {
                                ConstantConfig.Url = url;
                                //通知服务去后台下载
                                context.startService(new Intent(context, UpdataApk_Service.class).setPackage("zhidanhyb.huozhu"));
                                T.showLong(context, "正在后台下载!");
                            }
                        }
                    }

                }
            }
        });
        controller.updataVersion();

    }
}
