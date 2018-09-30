package com.yubin.recognize.utils;

import android.content.Context;

import java.io.File;

/**
 * author : Yubin.Ying
 * time : 2018/9/28
 */
public class FileUtil {
    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }
}
