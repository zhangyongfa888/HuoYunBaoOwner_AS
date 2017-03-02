package zhidanhyb.huozhu.Base;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import zhidanhyb.huozhu.R;

public class BaseDialog extends Dialog implements View.OnClickListener {

    /**
     *
     */
    private TextView dialog_title;
    /**
     *
     */
    public Button back_button;
    /**
     *
     */
    public Button sure_button;


    public BaseDialog(Context context) {
        super(context);
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
        setCanceledOnTouchOutside(false);
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    /**
     * @param title
     * @param background
     */
    public void setDialogTitle(int title, int background) {
        if (dialog_title != null) {
            dialog_title = null;
        }
        dialog_title = (TextView) findViewById(R.id.dialog_title);
        dialog_title.setText(title);
        if (background != 0) {
            dialog_title.setBackgroundResource(background);
        }
    }


    /**
     * @param text
     * @param background
     */
    public void setLeftButton(int text, int background) {
        if (back_button != null) {
            back_button = null;
        }
        back_button = (Button) findViewById(R.id.dialog_back_button);//返回
        back_button.setOnClickListener(this);
        if (background != 0) {
            back_button.setBackgroundResource(background);
        }
        back_button.setText(text);
    }


    public void setRightButton(int text, int background) {
        if (sure_button != null) {
            sure_button = null;
        }
        sure_button = (Button) findViewById(R.id.dialog_sure_button);//确定
        sure_button.setOnClickListener(this);
        sure_button.setBackgroundResource(background);
        sure_button.setText(text);
    }


    /**
     * @param text
     * @param background
     */
    public void setHintButton(int text, int background) {

        if (back_button != null) {
            back_button = null;
        }
        back_button = (Button) findViewById(R.id.dialog_back_button);//返回
        back_button.setOnClickListener(this);
        back_button.setBackgroundResource(background);
        back_button.setText(text);

        if (sure_button != null) {
            sure_button = null;
        }
        sure_button = (Button) findViewById(R.id.dialog_sure_button);//确定

        sure_button.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {

    }
}
