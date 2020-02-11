package com.paul.easytodo.Fragment;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.allen.library.SuperTextView;
import com.kongzue.baseframework.BaseFragment;
import com.kongzue.baseframework.interfaces.BindView;
import com.kongzue.baseframework.interfaces.Layout;
import com.kongzue.baseframework.util.JumpParameter;
import com.kongzue.baseframework.util.OnJumpResponseListener;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.v3.FullScreenDialog;
import com.kongzue.dialog.v3.MessageDialog;
import com.kongzue.dialog.v3.TipDialog;
import com.paul.easytodo.DataSource.FinalWords;
import com.paul.easytodo.MainActivity;
import com.paul.easytodo.Manager.SettingManager;
import com.paul.easytodo.Manager.SyncManager;
import com.paul.easytodo.R;
import com.paul.easytodo.Utils.DateUtil;
import com.paul.easytodo.Utils.ImageUtil;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;


import es.dmoral.toasty.Toasty;

@Layout(R.layout.fragment_setting)
public class SettingFragment extends BaseFragment<MainActivity> {
    @BindView(R.id.stv_1)
    private SuperTextView stv_1;
    @BindView(R.id.stv_2)
    private SuperTextView stv_2;
    @BindView(R.id.stv_3)
    private SuperTextView stv_3;
    @BindView(R.id.stv_6)
    private SuperTextView stv_6;
    @BindView(R.id.stv_7)
    private SuperTextView stv_7;
    @BindView(R.id.stv_8)
    private SuperTextView stv_8;
    @BindView(R.id.stv_update)
    private SuperTextView stv_update;
    @BindView(R.id.stv_download)
    private SuperTextView stv_download;




    private SettingManager settingManager;
    private refreashImageListener listener;
    EditText editText_username;
    EditText editText_password;
    private SyncManager syncManager;
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case -1:
                Toasty.error(me,"更新失败！请检查账户或者网络",Toasty.LENGTH_SHORT).show();
                    break;
                case 1:
                    stv_update.setRightString("更新时间："+ DateUtil.getCurDate()+" "+DateUtil.getCurrentTime());
                    Toasty.success(me,"更新成功",Toasty.LENGTH_SHORT).show();
                    break;
                case -2:
                    Toasty.error(me,"恢复失败！请检查账户或者网络",Toasty.LENGTH_SHORT).show();
                    break;
                case 2:
                    stv_download.setRightString("备份时间："+ DateUtil.getCurDate()+" "+DateUtil.getCurrentTime());
                    Toasty.success(me,"恢复成功",Toasty.LENGTH_SHORT).show();
                    listener.refreashAllData();
                    break;
            }
            return false;
        }
    });
    @Override
    public void initViews() {
        DialogSettings.style = DialogSettings.STYLE.STYLE_IOS;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window window = me.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(getResources().getColor(R.color.blue));
        }
    }

    @Override
    public void initDatas() {
        settingManager=new SettingManager(me);
        syncManager=new SyncManager(me,handler);
    }

    @Override
    public void setEvents() {
        stv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
        stv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
            }
        });
        stv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });
        stv_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEveryDayWordsEditDialog();
            }
        });
        stv_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullScreenDialog.show(me, R.layout.dialog_webview, new FullScreenDialog.OnBindView() {
                    @Override
                    public void onBind(final FullScreenDialog dialog, View rootView) {
                        TextView textView=rootView.findViewById(R.id.tv_security);
                        textView.setText(FinalWords.security_words);
                    }
                }).setOkButton("关闭", new OnDialogButtonClickListener() {
                    @Override
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        return false;
                    }
                }).setTitle("隐私协议");
            }
        });
        stv_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageDialog.build(me)
                        .setTitle("关于")
                        .setMessage("EasyTodo v"+getAppVersionName(me)+"\n"+"作者：巴塞罗那的余晖"+"\n希望在考研路上陪你一起努力❤")
                        .setOkButton("好", new OnDialogButtonClickListener() {
                            @Override
                            public boolean onClick(BaseDialog baseDialog, View v) {
                                return false;
                            }
                        })
                        .show();
            }
        });
        stv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingManager.canLogin()){
                    Toasty.info(me,"正在备份",Toasty.LENGTH_SHORT).show();
                    syncManager.updateAll();
                }else {
                    showInputDialog();
                }
            }
        });
        stv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingManager.canLogin()){
                    Toasty.info(me,"正在恢复",Toasty.LENGTH_SHORT).show();
                    syncManager.getAll();
                }else {
                    showInputDialog();
                }
            }
        });

    }
    public static String getAppVersionName(Context context) {
        String appVersionName = "";
        try {
            PackageInfo packageInfo = context.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            appVersionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("", e.getMessage());
        }
        return appVersionName;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1){
            if(data!=null){
                ContentResolver resolver = me.getContentResolver();
                Uri originalUri = data.getData(); // 获得图片的uri
                listener.refreashBackground(ImageUtil.getBitmapFromUri(me,originalUri));
                try {
                    MediaStore.Images.Media.getBitmap(resolver, originalUri);
                    String[] proj = {MediaStore.Images.Media.DATA};
                    @SuppressWarnings("deprecation")
                    Cursor cursor = me.managedQuery(originalUri, proj, null, null,
                            null);
                    int column_index = cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String path = cursor.getString(column_index);
                    settingManager.setHomepage_bg_imgdir(path);
                    Toasty.success(me,"设置成功！",Toasty.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }else if(requestCode==2){
            if(data!=null){
                Uri originalUri = data.getData(); // 获得图片的uri
                Bitmap bitmap=ImageUtil.getBitmapFromUri(me,originalUri);
                listener.refreashHeadIcon(bitmap);
                File appDir = new File(me.getExternalFilesDir(Environment.DIRECTORY_PICTURES),"headicon.png");
                Log.d("测试",appDir.getAbsolutePath());
                settingManager.setHomepage_headicon_imgdir(appDir.getAbsolutePath());
                if(appDir.exists()){
                    appDir.delete();
                }
                try {
                    FileOutputStream fos = new FileOutputStream(appDir);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public void showInputDialog() {
        Toasty.info(me,"请认真阅读教程填写账号!",Toasty.LENGTH_SHORT).show();
        //对于未实例化的布局：
        MessageDialog.show(me, "登录", "目前仅支持WebDav", "知道了")
                .setCustomView(R.layout.dialog_webdav_account, new MessageDialog.OnBindView() {
                    @Override
                    public void onBind(MessageDialog dialog, View v) {
                        //绑定布局事件，可使用v.findViewById(...)来获取子组件
                        editText_username=v.findViewById(R.id.et_name);
                        editText_password=v.findViewById(R.id.et_password);
                        editText_password.setText(settingManager.getPassword());
                        editText_username.setText(settingManager.getUsername());
                    }
                })
        .setOkButton("好了", new OnDialogButtonClickListener() {
            @Override
            public boolean onClick(BaseDialog baseDialog, View v) {
                settingManager.setUsername(editText_username.getText().toString());
                settingManager.setPassword(editText_password.getText().toString());
                Toasty.success(me,"保存成功！",Toasty.LENGTH_SHORT).show();
                return false;
            }
        });
    }
    /**
     * 裁剪图片方法实现
     * @param uri
     */
    public void clipPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop = true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例，这里设置的是正方形（长宽比为1:1）
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 1);
    }

    public interface refreashImageListener{
        public void refreashHeadIcon(Bitmap bitmap);
        public void refreashBackground(Bitmap bitmap);
        public void refreashAllData();
    }
    public void setOnRefreashImageListener(refreashImageListener listener){
        this.listener=listener;
    }

    public void showEveryDayWordsEditDialog(){
        FullScreenDialog.show(me, R.layout.dialog_everydaywords_view, new FullScreenDialog.OnBindView() {
            @Override
            public void onBind(final FullScreenDialog dialog, View rootView) {

            }
        }).setOkButton("关闭", new OnDialogButtonClickListener() {
            @Override
            public boolean onClick(BaseDialog baseDialog, View v) {
                return false;
            }
        }).setTitle("编辑");
    }
}
