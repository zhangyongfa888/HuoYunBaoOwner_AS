package zhidanhyb.huozhu.View;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import zhidanhyb.huozhu.R;

/**
 * Created by Administrator on 2017/2/16.
 * 邮箱 1399142039@qq.com
 * 项目名称：HuoYunBaoOwner_AS
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2017/2/16 10:11
 * 修改人：Administrator
 * 修改时间：2017/2/16 10:11
 * 修改备注：
 */
public class DividerView extends LinearLayout {
    public DividerView(Context context) {
        super(context);
        init();
    }

    /**
     *
     */
    private void init() {
        View view=View.inflate(getContext(), R.layout.v2_ll_divider_login,null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(view);
    }

    public DividerView(Context context, AttributeSet attrs) {
        super(context, attrs);init();
    }

    public DividerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DividerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);init();
    }


}
