package com.yubin.recognize;

import android.app.Application;
import android.util.Log;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.yubin.recognize.utils.Constants;
import com.yubin.recognize.utils.SpManager;

/**
 * author : Yubin.Ying
 * time : 2018/9/28
 */
public class App extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        //initAccessTokenWithAkSk();
        initAccessTokenLicenseFile();
        ZXingLibrary.initDisplayOpinion(this);
    }

    /**
     * 用明文ak，sk初始化
     */
    private void initAccessTokenWithAkSk() {
        OCR.getInstance(this).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
                Log.d("robin","token ="+token);
            }

            @Override
            public void onError(OCRError error) {
                Log.d("robin","error ="+error);
            }
        }, getApplicationContext(), Constants.API_KEY, Constants.SECRET_KEY);
    }

    /**
     * 自定义license的文件路径和文件名称，以license文件方式初始化
     */
    private void initAccessTokenLicenseFile() {
        OCR.getInstance(this).initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                SpManager.putString("token",token);
                Log.d("robin","license file token ="+token);
            }

            @Override
            public void onError(OCRError error) {
                Log.d("robin","license file error ="+error);
            }
        }, "aip.license", getApplicationContext());
    }
}
