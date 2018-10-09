package com.yubin.recognize.tts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.tts.client.SpeechSynthesizerListener;
import com.yubin.recognize.R;
import com.yubin.recognize.core.util.FileUtil;
import com.yubin.recognize.tts.control.InitConfig;
import com.yubin.recognize.tts.control.MySyntherizer;
import com.yubin.recognize.tts.listener.FileSaveListener;
import com.yubin.recognize.utils.Constants;

import java.util.Map;

public class SaveFileActivity extends TtsActivity {

    /**
     * 与SynthActivity相比，修改listener为FileSaveListener 可实现保存录音功能。
     * 获取的音频内容同speak方法播出的声音
     * FileSaveListener 在UiMessageListener的基础上，使用 onSynthesizeDataArrived回调，获取音频流
     */
    protected void initialTts() {
        String tmpDir = FileUtil.createTmpDir(this);
        // 设置初始化参数
        // 此处可以改为 含有您业务逻辑的SpeechSynthesizerListener的实现类
        SpeechSynthesizerListener listener = new FileSaveListener(mainHandler, tmpDir);
        Map<String, String> params = getParams();

        // appId appKey secretKey 网站上您申请的应用获取。注意使用离线合成功能的话，需要应用中填写您app的包名。包名在build.gradle中获取。
        InitConfig initConfig = new InitConfig(Constants.APPID, Constants.API_KEY, Constants.SECRET_KEY, ttsMode,  params, listener);
        synthesizer = new MySyntherizer(this, initConfig, mainHandler); // 此处可以改为MySyntherizer 了解调用过程
    }
}
