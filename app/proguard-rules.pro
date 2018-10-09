# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#百度云文字识别
-keep class com.baidu.ocr.sdk.**{*;}
-dontwarn com.baidu.ocr.**

#百度云语音识别
-keep class com.baidu.speech.**{*;}
-dontwarn com.baidu.speech.**


#百度语音合成
-keep class com.baidu.synthesizer.**{*;}
-keep class com.baidu.tts.**{*;}
-dontwarn com.baidu.tts.**
-dontwarn com.baidu.synthesizer.**
