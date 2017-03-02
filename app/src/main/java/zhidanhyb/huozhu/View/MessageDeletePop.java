package zhidanhyb.huozhu.View;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import zhidanhyb.huozhu.Base.BaseDialog;
import zhidanhyb.huozhu.Bean.MessageListBean;
import zhidanhyb.huozhu.HttpRequest.HttpController;
import zhidanhyb.huozhu.HttpRequest.HttpController.deleteMessageListener;
import zhidanhyb.huozhu.R;
import zhidanhyb.huozhu.Utils.ScreenUtils;
import zhidanhyb.huozhu.Utils.T;

/**
 * 消息点击右上角删除按钮时弹出的pop
 *
 * @author lxj
 */
public class MessageDeletePop extends PopupWindow implements deleteMessageListener {

    private Context mContext;
    private View mView;
    private ColorDrawable dw;
    /**
     *
     */
    private Button cancle_button;
    /**
     *
     */
    private Button delete_button;
    private List<MessageListBean> list = new ArrayList<MessageListBean>();

    public MessageDeletePop(Context context, View view) {
        this.mContext = context;
        this.mView = view;
    }

    /**
     *
     */
    public void show() {
        View popview = LayoutInflater.from(mContext).inflate(R.layout.message_delete_poplayout, null);
        cancle_button = (Button) popview.findViewById(R.id.message_delete_cancle_button);
        delete_button = (Button) popview.findViewById(R.id.message_delete_button);
        this.setWidth(ScreenUtils.getScreenWidth(mContext));
        this.setHeight(ScreenUtils.dip2px(mContext,70));
        this.setContentView(popview);
        dw = new ColorDrawable(mContext.getResources().getColor(R.color.transparent_dialog_color));
        this.setBackgroundDrawable(dw);
        this.setFocusable(false); //设置SelectPicPopupWindow弹出窗体可点击
        this.setOutsideTouchable(false);  //设置点击屏幕其它地方弹出框消失
        int[] location = new int[2];
        mView.getLocationOnScreen(location);
        this.showAtLocation(mView, Gravity.NO_GRAVITY, location[0], location[1] - this.getHeight());

        cancle_button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (deleteMessageListener == null) {
                    return;
                }
                deleteMessageListener.onColose();
                colse();
            }
        });

        delete_button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onDeleteMessage();
            }
        });
    }


    /**
     *
     */
    public void colse() {
        dismiss();
        cancle_button = null;
        delete_button = null;
    }

    public void UpdataDeleteData(List<MessageListBean> messagelist) {
        list = messagelist;
    }

    /**
     *
     */
    public void onDeleteMessage() {
        if (list == null) {
            return;
        }
//        delelteMessageHintDialog dialog = new delelteMessageHintDialog(mContext, R.style.dialog);
//        dialog.showDialog();

        DialogUtils.showDialogBack(mContext, true, "提示", "是否删除", "返回", "确定", new DialogUtils.setOnDialogLeftButtonClick() {
            /**
             * @param view
             */
            @Override
            public void setOnClickLeft(View view) {
//                if (deleteMessageListener != null) {
//                    deleteMessageListener.onColose();
//                }
//                colse();

            }
        }, new DialogUtils.setOnDialogRightButtonClick() {
            /**
             * @param view
             */
            @Override
            public void setOnClickRight(View view) {
                Ids = "";
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isSelector() == true) {
                        Ids += list.get(i).getId() + ",";
                        deletelist.add(list.get(i).getId());
                    }
                }
                if (Ids.toString().equals("")) {
                    T.showShort(mContext, "请选择要删除的消息");
                    return;
                }
                deleteMessage();
                colse();
            }
        });


    }

    private String Ids = "";
    private List<String> deletelist = new ArrayList<String>();

    private class delelteMessageHintDialog extends BaseDialog {
        public delelteMessageHintDialog(Context context, int theme) {
            super(context, theme);
        }

        public void showDialog() {
            View view = LayoutInflater.from(mContext).inflate(R.layout.messagedelete_dialoglayout, null);
            setContentView(view);
            setDialogTitle(R.string.hint, R.drawable.yellowtips);
            setLeftButton(R.string.back, R.drawable.gray_circular_bead_buttons);
            setRightButton(R.string.ok, R.drawable.orange_circular_bead_buttons);
            show();
        }

        @Override
        public void onClick(View v) {
            super.onClick(v);
            switch (v.getId()) {
                case R.id.dialog_back_button:
                    if (deleteMessageListener != null) {
                        deleteMessageListener.onColose();
                    }
                    dismiss();
                    break;
                case R.id.dialog_sure_button:
                    Ids = "";
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).isSelector() == true) {
                            Ids += list.get(i).getId() + ",";
                            deletelist.add(list.get(i).getId());
                        }
                    }
                    if (Ids.toString().equals("")) {
                        T.showShort(mContext, "请选择要删除的消息");
                        return;
                    }
                    deleteMessage();
                    dismiss();
                    break;
                default:
                    break;
            }
        }
    }

    public void deleteMessage() {
        HttpController controller = new HttpController(mContext);
        String ids = Ids.substring(0, Ids.length() - 1).toString();//消息ID（1，2，3）字符串用逗号隔开
        String type = "2";//1.司机端 2。货主端
        controller.setDeleteMessageListener(this);
        controller.deleteMessage(ids, type);
    }

    private static onDeleteMessageListener deleteMessageListener;

    public void setOnDeleteMessageListener(onDeleteMessageListener onDeleteMessageListener) {
        this.deleteMessageListener = onDeleteMessageListener;
    }

    public interface onDeleteMessageListener {
        void onDeleteMess(List<String> messagelist);

        void onColose();
    }

    //删除成功后的回调
    @Override
    public void deleteMessageSuccess() {
        if (deleteMessageListener == null) {
            return;
        }
        deleteMessageListener.onDeleteMess(deletelist);
    }
}
