package com.yubin.recognize;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.yubin.ocrlibrary.ocr.ui.camera.CameraActivity;
import com.yubin.recognize.activity.BaiduRecogMainActivity;
import com.yubin.recognize.service.RecognizeService;
import com.yubin.recognize.tts.SaveFileActivity;
import com.yubin.recognize.tts.TtsActivity;
import com.yubin.recognize.utils.DialogHelper;
import com.yubin.recognize.utils.FileUtil;
import com.yubin.recognize.utils.ImageUtils;
import com.yubin.recognize.utils.SpManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.button5)
    Button button5;
    @BindView(R.id.button6)
    Button button6;
    @BindView(R.id.button7)
    Button button7;
    @BindView(R.id.button8)
    Button button8;
    @BindView(R.id.button9)
    Button button9;
    @BindView(R.id.button10)
    Button button10;
    private MainActivity activityInstance;
    private String[] permissionArray = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE};
    private static final int REQUEST_PERMISSIONS = 20;
    private static final int REQUEST_CODE_GENERAL_BASIC = 21;
    private static final int REQUEST_CODE_ACCURATE_BASIC = 22;
    private static final int REQUEST_CODE_FRONT_IDCARD = 23;
    private static final int REQUEST_CODE_BACK_IDCARD = 24;
    private static final int REQUEST_CODE_QRCODE = 25;
    private static final int REQUEST_CODE_ZXING_QRCODE = 26;
    private static final int REQUEST_CODE_ZXING_PIC_QRCODE = 27;
    private ClipboardManager cm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setTitle(R.string.main_title);
        activityInstance = this;
        checkPermissions();
        button5.setVisibility(View.GONE);
        cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放内存资源
        OCR.getInstance(this).release();
    }


    private void checkPermissions() {
        if (AndPermission.hasPermission(activityInstance, permissionArray)) {
        } else {
            AndPermission.with(activityInstance)
                    .requestCode(REQUEST_PERMISSIONS)
                    .permission(permissionArray)
                    .callback(new PermissionListener() {
                        @Override
                        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                            if (AndPermission.hasPermission(activityInstance, permissionArray)) {

                            }
                        }

                        @Override
                        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                            if (AndPermission.hasPermission(activityInstance, permissionArray)) {

                                return;
                            }
                            AndPermission.defaultSettingDialog(activityInstance, REQUEST_PERMISSIONS)
                                    .setTitle("权限申请失败")
                                    .setMessage("您已禁用 \"读写手机存储\" 权限，请在设置中授权！")
                                    .setPositiveButton("好，去设置")
                                    .show();
                        }
                    })
                    .rationale(new RationaleListener() {
                        @Override
                        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                            AndPermission.rationaleDialog(activityInstance, rationale).show();
                        }
                    })
                    .start();
        }
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,R.id.button10})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.button1:
                if (!SpManager.getString("token").isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, REQUEST_CODE_GENERAL_BASIC);
                }
                break;
            case R.id.button2:
                if (!SpManager.getString("token").isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, REQUEST_CODE_ACCURATE_BASIC);
                }
                break;
            case R.id.button3:
                if (!SpManager.getString("token").isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE, true);
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                    startActivityForResult(intent, REQUEST_CODE_FRONT_IDCARD);
                }
                break;
            case R.id.button4:
                if (!SpManager.getString("token").isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE, true);
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
                    startActivityForResult(intent, REQUEST_CODE_BACK_IDCARD);
                }
                break;
            case R.id.button5:
                if (!SpManager.getString("token").isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, REQUEST_CODE_QRCODE);
                }
                break;
            case R.id.button6:
                Intent i = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(i, REQUEST_CODE_ZXING_QRCODE);
                break;
            case R.id.button7:
                Intent pickintent = new Intent(Intent.ACTION_PICK);
                pickintent.setType("image/*");
                startActivityForResult(pickintent, REQUEST_CODE_ZXING_PIC_QRCODE);
                break;
            case R.id.button8:
                Intent baidurecog = new Intent(activityInstance, BaiduRecogMainActivity.class);
                startActivity(baidurecog);
                break;
            case R.id.button9:
                Intent ttsintent = new Intent(activityInstance, TtsActivity.class);
                startActivity(ttsintent);
                break;
            case R.id.button10:
                Intent saveintent = new Intent(activityInstance, SaveFileActivity.class);
                startActivity(saveintent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GENERAL_BASIC && resultCode == RESULT_OK) {
            RecognizeService.recGeneral(activityInstance, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            final String text = result;
                            DialogHelper.getConfirmDialog(activityInstance, result, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //创建ClipData对象
                                    ClipData clipData = ClipData.newPlainText("text copy", text);
                                    //添加ClipData对象到剪切板中
                                    cm.setPrimaryClip(clipData);
                                    Toast.makeText(activityInstance,R.string.copy_text,Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                        }
                    });
        } else if (requestCode == REQUEST_CODE_ACCURATE_BASIC && resultCode == RESULT_OK) {
            RecognizeService.recAccurateBasic(activityInstance, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(), new RecognizeService.ServiceListener() {
                @Override
                public void onResult(String result) {
                    final String text = result;
                    DialogHelper.getConfirmDialog(activityInstance, result, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //创建ClipData对象
                            ClipData clipData = ClipData.newPlainText("text copy", text);
                            //添加ClipData对象到剪切板中
                            cm.setPrimaryClip(clipData);
                            Toast.makeText(activityInstance,R.string.copy_text,Toast.LENGTH_SHORT).show();
                        }
                    }).show();
                }
            });
        } else if (requestCode == REQUEST_CODE_FRONT_IDCARD && resultCode == RESULT_OK) {
            RecognizeService.recognizeIDCard(activityInstance, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(), IDCardParams.ID_CARD_SIDE_FRONT, new RecognizeService.ServiceListener() {
                @Override
                public void onResult(String result) {
                    final String text = result;
                    DialogHelper.getConfirmDialog(activityInstance, result, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //创建ClipData对象
                            ClipData clipData = ClipData.newPlainText("text copy", text);
                            //添加ClipData对象到剪切板中
                            cm.setPrimaryClip(clipData);
                            Toast.makeText(activityInstance,R.string.copy_text,Toast.LENGTH_SHORT).show();
                        }
                    }).show();
                }
            });
        } else if (requestCode == REQUEST_CODE_BACK_IDCARD && resultCode == RESULT_OK) {
            RecognizeService.recognizeIDCard(activityInstance, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(), IDCardParams.ID_CARD_SIDE_BACK, new RecognizeService.ServiceListener() {
                @Override
                public void onResult(String result) {
                    final String text = result;
                    DialogHelper.getConfirmDialog(activityInstance, result, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //创建ClipData对象
                            ClipData clipData = ClipData.newPlainText("text copy", text);
                            //添加ClipData对象到剪切板中
                            cm.setPrimaryClip(clipData);
                            Toast.makeText(activityInstance,R.string.copy_text,Toast.LENGTH_SHORT).show();
                        }
                    }).show();
                }
            });
        } else if (requestCode == REQUEST_CODE_QRCODE && resultCode == RESULT_OK) {
            RecognizeService.recQrcode(activityInstance, FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(), new RecognizeService.ServiceListener() {
                @Override
                public void onResult(String result) {
                    final String text = result;
                    DialogHelper.getConfirmDialog(activityInstance, result, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //创建ClipData对象
                            ClipData clipData = ClipData.newPlainText("text copy", text);
                            //添加ClipData对象到剪切板中
                            cm.setPrimaryClip(clipData);
                            Toast.makeText(activityInstance,R.string.copy_text,Toast.LENGTH_SHORT).show();
                        }
                    }).show();
                }
            });
        } else if (requestCode == REQUEST_CODE_ZXING_QRCODE && resultCode == RESULT_OK) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    final String text = result;
                    DialogHelper.getConfirmDialog(activityInstance, result, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //创建ClipData对象
                            ClipData clipData = ClipData.newPlainText("text copy", text);
                            //添加ClipData对象到剪切板中
                            cm.setPrimaryClip(clipData);
                            Toast.makeText(activityInstance,R.string.copy_text,Toast.LENGTH_SHORT).show();
                        }
                    }).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    DialogHelper.getMessageDialog(activityInstance, "解析失败").show();
                }
            }
        } else if (requestCode == REQUEST_CODE_ZXING_PIC_QRCODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                CodeUtils.analyzeBitmap(ImageUtils.getImageAbsolutePath(this, uri), new CodeUtils.AnalyzeCallback() {
                    @Override
                    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                        final String text = result;
                        DialogHelper.getConfirmDialog(activityInstance, result, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //创建ClipData对象
                                ClipData clipData = ClipData.newPlainText("text copy", text);
                                //添加ClipData对象到剪切板中
                                cm.setPrimaryClip(clipData);
                                Toast.makeText(activityInstance,R.string.copy_text,Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                    }

                    @Override
                    public void onAnalyzeFailed() {
                        DialogHelper.getMessageDialog(activityInstance, "解析失败").show();
                    }
                });
            }
        }
    }

}
