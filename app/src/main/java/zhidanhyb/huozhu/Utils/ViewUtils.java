package zhidanhyb.huozhu.Utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/2/16.
 * 邮箱 1399142039@qq.com
 * 项目名称：HuoYunBaoOwner_AS
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2017/2/16 8:36
 * 修改人：Administrator
 * 修改时间：2017/2/16 8:36
 * 修改备注：
 */
public class ViewUtils {

    /**
     * @param context
     * @param view
     * @param widthPx
     * @param heightPx
     */
    public static void setViewSize(Context context, View view, float widthPx, float heightPx) {
        float setWidth = ScreenUtils.getScreenWidth(context) * widthPx / 640;
        float setHeight = ScreenUtils.getScreenWidth(context) * heightPx / 640;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(setHeight!=0){
            layoutParams.height = (int) setHeight;
        }
        if(setWidth!=0){
            layoutParams.width = (int) setWidth;
        }
        
        view.setLayoutParams(layoutParams);

    }
}
