package zhidanhyb.huozhu.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class CallUtils
{
    public static void startSystemDialingActivity(Context context, String phone)
    {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    
}
