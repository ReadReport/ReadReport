

#retrofit
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions

#umeng
-keep class com.umeng.** {*;}

#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#baseQuickAdapter
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}

#butterknife
# Retain generated class which implement Unbinder.
-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }

# Prevent obfuscation of types which use ButterKnife annotations since the simple name
# is used to reflectively look up the generated ViewBinding.
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }

#photoview
-keep class uk.co.senab.photoview.** {*;}
-dontwarn uk.co.senab.photoview.**


# rxjava
-keep class rx.** { *; }
-dontwarn rx.**


-dontwarn android.databinding.**
-keep class android.databinding.** { *; }
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-dontwarn com.zyyoona7.lib.**

-keep class com.wy.report.base.model.* { *; }
-keep class * extends com.wy.report.base.model.BaseModel { *; }
-keep class com.wy.report.business.*.model.** { *; }
-keep class com.wy.report.business.*.service.** { *; }

#fastjson
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.**{*;}
-keep class * implements java.io.Serializable { *; }
-keepattributes com.alibaba.fastjson.annotation.**

-keep class in.srain.** {*;}
-keep class android.** {*;}
-keep class com.github.** {*;}
-keep class com.hwangjr.** {*;}

-keep class com.wy.** {*;}
-keepclasseswithmembernames class * { @com.hwangjr.rxbus.annotation.* <methods>; }
