package com.paul.easytodo.Fragment;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toolbar;

import androidx.annotation.Nullable;

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
import com.paul.easytodo.MainActivity;
import com.paul.easytodo.Manager.SettingManager;
import com.paul.easytodo.R;

import java.io.IOException;

import es.dmoral.toasty.Toasty;

@Layout(R.layout.fragment_setting)
public class SettingFragment extends BaseFragment<MainActivity> {
    @BindView(R.id.stv_1)
    private SuperTextView stv_1;
    @BindView(R.id.stv_7)
    private SuperTextView stv_7;
    @BindView(R.id.stv_8)
    private SuperTextView stv_8;




    private SettingManager settingManager;
    @Override
    public void initViews() {
        DialogSettings.style = DialogSettings.STYLE.STYLE_IOS;
    }

    @Override
    public void initDatas() {
        settingManager=new SettingManager(me);
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

        stv_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullScreenDialog.show(me, R.layout.dialog_webview, new FullScreenDialog.OnBindView() {
                    @Override
                    public void onBind(final FullScreenDialog dialog, View rootView) {
                        WebView webView;
                        webView = rootView.findViewById(R.id.webView);
                        webView.setWebViewClient(new WebViewClient());
                        webView.getSettings().setJavaScriptEnabled(true);
                        //设置自适应屏幕，两者合用
                        webView.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
                        webView.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小

                        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
                        //缩放操作
                        webView.getSettings().setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
                        webView.getSettings().setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
                        webView.getSettings().setDisplayZoomControls(false); //隐藏原生的缩放控件
                        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                        webView.getSettings().setDomStorageEnabled(true);
                        webView.loadUrl("https://www.yuque.com/docs/share/eecca65c-442e-4567-9be5-901e41a04bce?#");

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
        }

    }
}
