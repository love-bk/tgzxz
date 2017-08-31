#-------------------------------------------定制化区域----------------------------------------------
#---------------------------------1.实体类---------------------------------
-keep public class com.tianguo.zxz.bean.**{*;}
-keep public class com.tianguo.zxz.net.**{*;}
-keep public class com.tianguo.zxz.fragment.VidoFragment.**{*;}
-keep public class com.tianguo.zxz.fragment.GaoXiaoFragment.**{*;}
-keep class com.tianguo.video.** { *; }
-dontwarn com.tianguo.video.**
#-------------------------------------------------------------------------

-keep class com.qihoo360.videosdk.export.** {public *;}
-keep class com.qihoo360.videosdk.exportui.** {public *;}
-keep class com.qihoo360.videosdk.page.** extends android.app.Activity {public *;}
-keep class com.qihoo360.videosdk.ui.common.** { public *;}
-keep class com.qihoo360.videosdk.comment.** {public *;}
-keep class com.qihoo360.videosdk.guide.AutoScrollTextView {public *;}
-keep class com.qihoo360.videosdk.video.view.** {public *;}
-keep class com.qihoo360.videosdk.video.widget.** {public *;}
-keep class com.qihoo360.videosdk.view.impl.** {public *;}
-keep class com.qihoo360.videosdk.control.display.ThemeManager {public *;}
-keep class com.qihoo360.videosdk.VideoSDK { public *;}
# 6.0编译环境需要keep类
-keep class org.apache.** {*;}
-dontwarn org.apache.http.**
-keep class android.net.http.** {*;}
-dontwarn android.net.http.**
-keep class net.jarlehansen.protobuf.javame.** {*;}
 # ProGuard configurations for Bugtags
  -keepattributes LineNumberTable,SourceFile

  -keep class com.bugtags.library.** {*;}
  -dontwarn com.bugtags.library.**
  -keep class io.bugtags.** {*;}
  -dontwarn io.bugtags.**
  -dontwarn org.apache.http.**
  -dontwarn android.net.http.AndroidHttpClient

#---------------------------------2.第三方包-------------------------------

#5.ucrop
-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }

#6.photoview
-keep class uk.co.senab.photoview** { *; }
-keep interface uk.co.senab.photoview** { *; }

#7.rxgalleryfinal
-keep class cn.finalteam.rxgalleryfinal.ui.widget** { *; }

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepattributes *Annotation*
-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}
#快眼
-keep class com.wmcsk.**{}
#ijkplayer
-keep class tv.danmaku.ijk.** { *; }
-dontwarn tv.danmaku.ijk.**

#点view广告
-dontwarn com.dianjoy.video.**
-keep class **.R$* { *;  }
-keep class com.dianjoy.video.**{*;}
#TONGDUN
-dontwarn android.os.**
-dontwarn com.android.internal.**
-keep class cn.tongdun.android.**{*;}
#eventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
 # ProGuard configurations for Bugtags
  -keepattributes LineNumberTable,SourceFile

  -keep class com.bugtags.library.** {*;}
  -dontwarn com.bugtags.library.**
  -keep class io.bugtags.** {*;}
  -dontwarn io.bugtags.**
  -dontwarn org.apache.http.**
  -dontwarn android.net.http.AndroidHttpClient

  # End Bugtags
#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
 #sharedsdk
  -keep class cn.sharesdk.**{*;}
  	-keep class com.sina.**{*;}
  	-keep class **.R$* {*;}
  	-keep class **.R{*;}
  	-keep class com.mob.**{*;}
  	-dontwarn com.mob.**
  	-dontwarn cn.sharesdk.**
  	-dontwarn **.R$*
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#百度
-keepclassmembers class * extends android.app.Activity {    public void *(android.view.View); }

-keepclassmembers enum * {     public static **[] values();     public static ** valueOf(java.lang.String); }

-keep class com.baidu.mobads.*.** { *; }
#讯飞
-keep class com.iflytek.voiceads.**{*;}
#快眼
-keep class com.wmcsk.**{*;}
#-------点财----------------------------------------------------------------------------------------
-keep class com.dc.wall.* { *;
}
#-------趣米----------------------------------------------------------------------------------------
-keep class com.qm.pw.** {*;}
#-------中亿----------------------------------------------------------------------------------------
-dontwarn com.zy.phone.**
-keep class com.zy.phone.** { *; }
#-------点乐----------------------------------------------------------------------------------------
-dontwarn com.dlnetwork.**
-keep class **.R$* { *;  }
-keep class com.dlnetwork.**{*;}
#-------大头鸟----------------------------------------------------------------------------------------
-keep class com.datouniao.AdPublisher.** {*;}
#immogd
-keepclassmembers class * {public *;}
-keep public class * {public *;}
 -keep class com.five.adwoad.** {*;}
  -keep public class com.wooboo.** {*;}
   -keep public class cn.aduu.android.**{*;}
   -keep public class com.wqmobile.** {*;}
    -keep class com.baidu.mobads.** {   public protected *; }
    -keep public class com.google.android.gms.ads.** {    public *; }
     -keep public class com.google.ads.** {    public *; }
     -keep public class com.millennialmedia.android.* {  <init>(...);  public void *(...);  public com.millennialmedia.android.MMJSResponse *(...); }
-keep class com.qq.e.** {    public protected *;     }
  -keep class com.tencent.gdt.**{     public protected *;     }
# skip the Picasso library classes 
        -dontwarn com.squareup.okhttp.**
         -dontwarn com.moat.** # skip AVID classes
         -keep class com.integralads.avid.library.* {*;}

-keepattributes SourceFile,LineNumberTable
-keep public class com.google.android.gms.**
-keep class com.google.android.gms.common.api.GoogleApiClient$* {public *;}
-keep class com.google.android.gms.location.LocationServices {public *;}
-keep class com.google.android.gms.location.FusedLocationProviderApi {public *;}
-keep class com.google.android.gms.location.ActivityRecognition {public *;}
-keep class com.google.android.gms.location.ActivityRecognitionApi {public *;}
-keep class com.google.android.gms.location.ActivityRecognitionResult {public *;}
-keep class com.google.android.gms.location.DetectedActivity {public *;}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient{
     public *;
}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info{
     public *;
}
-dontwarn com.google.android.gms.**
-dontoptimize
-dontwarn -keep class com.mobisage.android.** {*;}
 -keep interface com.mobisage.android.** {*;}
  -keep class com.msagecore.** {*;}
  -keep interface com.msagecore.** {*;}


 -keep class com.suizong.mobile.** {*;}
 -keep class com.go2map.mapapi.** {*;}

-keep public class cn.Immob.sdk.** {*;}
 -keep public class cn.Immob.sdk.controller.** {*;}

-keep class net.youmi.android.** {*;}

-keeppackagenames cn.smartmad.ads.android
-keeppackagenames I
-keep class cn.smartmad.ads.android.* {*;}
 -keep class I.* {*;}


-keep public class MobWin.* -keep public class MobWin.cnst.*
 -keep class com.tencent.lbsapi.*
 -keep class com.tencent.lbsapi.core.*
 -keep class LBSAPIProtocol.* -keep class com.tencent.lbsapi.core.QLBSJNI { *; }

-keeppackagenames com.adchina.android.ads
-keeppackagenames com.adchina.android.ads.controllers
-keeppackagenames com.adchina.android.ads.views
-keeppackagenames com.adchina.android.ads.animations
 -keep class com.adchina.android.ads.*{*;}
 -keep class com.adchina.android.ads.controllers.*{*;}
  -keep class com.adchina.android.ads.views.*{*;}
   -keep class com.adchina.android.ads.animations.*{*;}



# skip the Picasso library classes
-keep class com.squareup.picasso.** {*;}
-dontwarn com.squareup.picasso.**
# skip Moat classes
-keep class com.moat.** {*;}
-dontwarn com.moat.**
#//umenttuisong
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
# 避免影响升级功能，需要keep住support包的类
-keep class com.db.ta.sdk.** { *; }
 #TONGDUN
  -dontwarn android.os.**
   -dontwarn com.android.internal.**
   -keep class cn.tongdun.android.**{*;}
#-------------------------------------------------------------------------

#---------------------------------3.与js互相调用的类------------------------



#-------------------------------------------------------------------------

#---------------------------------4.反射相关的类和方法-----------------------



#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------

#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-ignorewarnings    # 抑制警告
#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}
#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
#----------------------------------------------------------------------------


