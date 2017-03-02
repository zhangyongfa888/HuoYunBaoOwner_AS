package zhidanhyb.huozhu.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharedPreferences存取
 *
 * @author Administrator
 */
public class SharedPerfenceUtil {
    public static final String signData = "signData";

    /**
     * 设置SharedPreferences
     *
     * @param context      上下文
     * @param fileName     文件名
     * @param paramName    参数名
     * @param paramContent 参数值
     */
    public static void setSpParams(Context context, String fileName, String paramName, String paramContent) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString(paramName, paramContent);
        editor.commit();
        editor.clear();
    }

    /**
     * 获取SharedPreferences
     *
     * @param context   上下文
     * @param fileName  文件名
     * @param paramName 参数名
     * @return 参数值
     */
    public static String getSpParams(Context context, String fileName, String paramName) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getString(paramName, "");
    }

    /**
     * 是否包含
     *
     * @param context
     * @param fileName
     * @param paramName
     * @return
     */
    public static boolean isContainsParams(Context context, String fileName, String paramName) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.contains(paramName);
    }

    public static void deleteShareInfo(String fileName,Context context) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }
}
