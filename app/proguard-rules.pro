# To enable ProGuard in your project, edit project.properties
# to define the proguard.config property as described in that file.
#
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in ${sdk.dir}/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}


-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*



-libraryjars libs/arm64-v8a/liblocSDK6a.so
-libraryjars libs/arm64-v8a/libjpush206.so
-libraryjars libs/arm64-v8a/libBaiduMapSDK_base_v3_7_0.so
-libraryjars libs/arm64-v8a/libBaiduMapSDK_map_v3_7_0.so
-libraryjars libs/arm64-v8a/libBaiduMapSDK_search_v3_7_0.so
-libraryjars libs/arm64-v8a/libBaiduMapSDK_util_v3_7_0.so
-libraryjars libs/arm64-v8a/libBaiduMapSDK_cloud_v3_7_0.so
-libraryjars libs/arm64-v8a/libBaiduMapSDK_radar_v3_7_0.so


-libraryjars libs/armeabi/liblocSDK6a.so
-libraryjars libs/armeabi/libjpush206.so
-libraryjars libs/armeabi/libBaiduMapSDK_base_v3_7_0.so
-libraryjars libs/armeabi/libBaiduMapSDK_map_v3_7_0.so
-libraryjars libs/armeabi/libBaiduMapSDK_search_v3_7_0.so
-libraryjars libs/armeabi/libBaiduMapSDK_util_v3_7_0.so
-libraryjars libs/armeabi/libBaiduMapSDK_cloud_v3_7_0.so
-libraryjars libs/armeabi/libBaiduMapSDK_radar_v3_7_0.so


-libraryjars libs/armeabi-v7a/liblocSDK6a.so
-libraryjars libs/armeabi-v7a/libjpush206.so
-libraryjars libs/armeabi-v7a/libBaiduMapSDK_base_v3_7_0.so
-libraryjars libs/armeabi-v7a/libBaiduMapSDK_map_v3_7_0.so
-libraryjars libs/armeabi-v7a/libBaiduMapSDK_search_v3_7_0.so
-libraryjars libs/armeabi-v7a/libBaiduMapSDK_util_v3_7_0.so
-libraryjars libs/armeabi-v7a/libBaiduMapSDK_cloud_v3_7_0.so
-libraryjars libs/armeabi-v7a/libBaiduMapSDK_radar_v3_7_0.so

-libraryjars libs/x86/liblocSDK6a.so
-libraryjars libs/x86/libBaiduMapSDK_base_v3_7_0.so
-libraryjars libs/x86/libBaiduMapSDK_map_v3_7_0.so
-libraryjars libs/x86/libBaiduMapSDK_search_v3_7_0.so
-libraryjars libs/x86/libBaiduMapSDK_util_v3_7_0.so
-libraryjars libs/x86/libBaiduMapSDK_cloud_v3_7_0.so
-libraryjars libs/x86/libBaiduMapSDK_radar_v3_7_0.so

-libraryjars libs/x86_64/liblocSDK6a.so
-libraryjars libs/x86_64/libBaiduMapSDK_base_v3_7_0.so
-libraryjars libs/x86_64/libBaiduMapSDK_map_v3_7_0.so
-libraryjars libs/x86_64/libBaiduMapSDK_search_v3_7_0.so
-libraryjars libs/x86_64/libBaiduMapSDK_util_v3_7_0.so
-libraryjars libs/x86_64/libBaiduMapSDK_radar_v3_7_0.so
-libraryjars libs/x86_64/libBaiduMapSDK_cloud_v3_7_0.so

-dontwarn android.support.**
-dontwarn org.dom4j.**
-dontwarn org.slf4j.**
-dontwarn org.http.mutipart.**
-dontwarn org.apache.**
-dontwarn org.apache.log4j.**
-dontwarn org.apache.commons.logging.**
-dontwarn org.apache.commons.codec.binary.**
-dontwarn com.google.android.gms.**
-dontwarn net.poemcode.**
-ignorewarnings

# 成 #-libraryjars libs/locSDK_6.13.jar
# 成 #-libraryjars libs/android-async-http-1.4.5.jar
# 成 #-libraryjars libs/android-support-v4.jar
# 成 #-libraryjars libs/baidumapapi_base_v3_7_0.jar
# 成 #-libraryjars libs/baidumapapi_cloud_v3_7_0.jar
# 成 #-libraryjars libs/baidumapapi_map_v3_7_0.jar
# 成 #-libraryjars libs/baidumapapi_radar_v3_7_0.jar
# 成 #-libraryjars libs/baidumapapi_search_v3_7_0.jar
# 成 #-libraryjars libs/baidumapapi_util_v3_7_0.jar
# 成 #-libraryjars libs/universal-image-loader-1.8.6-with-sources.jar
# 成 #-libraryjars libs/xutils.jar
# 成 #-libraryjars libs/jpush-android-2.0.6.jar
# 成 #-libraryjars libs/libammsdk.jar
# 成 #-libraryjars libs/alipaySdk-20151215.jar
# 成 #-libraryjars libs/MobLogCollector.jar
# 成 #-libraryjars libs/MobTools.jar
# 成 #-libraryjars libs/ShareSDK-Core-2.6.1.jar
# 成 #-libraryjars libs/ShareSDK-ShortMessage-2.6.1.jar
# 成 #-libraryjars libs/ShareSDK-Wechat-2.6.1.jar
# 成 #-libraryjars libs/ShareSDK-Wechat-Core-2.6.1.jar
# 成 #-libraryjars libs/ShareSDK-Wechat-Moments-2.6.1.jar

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep class **.R$* { *; }

-keep public class * extends zhidanhyb.huozhu.Base.BaseActivity
-keep public class * extends zhidanhyb.huozhu.Base.BaseFragment

-keep public class zhidanhyb.huozhu.Config.ConstantConfig{*;}
-keep public class zhidanhyb.huozhu.Bean.UserLoginBean{*;}
-keep public class zhidanhyb.huozhu.Bean.Order_SendSureBean{*;}
-keep public class zhidanhyb.huozhu.Bean.UserGoldBean{*;}
-keep public class zhidanhyb.huozhu.Bean.UserAccountDataBean{*;}
-keep public class zhidanhyb.huozhu.Bean.RechargeWxBean{*;}
-keep public class zhidanhyb.huozhu.Bean.RechargeAliPayBean{*;}
-keep public class zhidanhyb.huozhu.Bean.OrderDetials_Bean{*;}
-keep public class zhidanhyb.huozhu.Bean.OrderDetials_Driverinfo_Bean{*;}
-keep public class zhidanhyb.huozhu.Bean.OrderDetials_Orderinfo_Bean{*;}
-keep public class zhidanhyb.huozhu.Bean.OrderDetials_Releaseinfo_Bean{*;}
-keep public class zhidanhyb.huozhu.Bean.UpdataVersionBean{*;}
-keep public class zhidanhyb.huozhu.Bean.DriverInfoBean{*;}
-keep public class zhidanhyb.huozhu.Bean.OwnerGoldBean{*;}
-keep public class zhidanhyb.huozhu.Bean.ShareContentBean{*;}

-keepattributes Signature

-keep class com.baidu.** { *; }
-keep class vi.com.gdi.bgl.android.**{*;}

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }


-keep class android.net.http.SslError
-keep class android.webkit.**{*;}
-keep class cn.sharesdk.**{*;}


#与js交互需要配置的
-keepclassmembers class zhidanhyb.huozhu.Activity.Home.Signin_Activity$DemoJavaScriptInterface{
      public *;
}
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*

