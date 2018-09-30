package com.yubin.recognize.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yubin.recognize.R;

public class ActivityOnlineRecog extends ActivityAbstractRecog {
    public ActivityOnlineRecog() {
        super(R.raw.online_recog, false);
        // uiasr\src\main\res\raw\online_recog.txt 本Activity使用的说明文件
        // false 表示activity不支持离线
    }

}
