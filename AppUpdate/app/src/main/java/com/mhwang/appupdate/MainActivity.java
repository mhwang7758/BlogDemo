package com.mhwang.appupdate;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class MainActivity extends AppCompatActivity {
    private void showLog(String s) {
        Log.d("MainActivity-->",s);
    }

    private boolean mHasFilePermission;

    private static final String DOWN_LOAD_APK_PATH = Environment.getExternalStorageDirectory()
            + File.separator+"upgradeTest.apk";

    /**
     *  这里先给个固定的下载地址，具体可以根据实际情况来变动是否固定
     */
    private static final String UPGRADE_URL = "http://117.48.203.76:8888/mhwang7758/images/upgradeTest.apk";
    Button btn_upgrade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FileDownloader.setup(getApplicationContext());
        btn_upgrade = (Button) findViewById(R.id.btn_upgrade);
        btn_upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mHasFilePermission){
                    Toast.makeText(MainActivity.this,
                            "更新失败，没有文件读写权限",Toast.LENGTH_SHORT).show();
                }
                downloadApk(UPGRADE_URL);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionGen.with(MainActivity.this)
                    .addRequestCode(100)
                    .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .request();
            showLog("sdk more than 6.0");
        }else{
            mHasFilePermission = true;
            showLog("sdk less than 6.0");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    private void initData(){
        mHasFilePermission = true;
        showLog("permission success");
    }

    @PermissionFail(requestCode = 100)
    private void requestPermissionFail(){
        Toast.makeText(this, "permission deny", Toast.LENGTH_SHORT).show();
    }

    /** 下载apk文件
     * @param url
     */
    private void downloadApk(String url){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("程序升级中……");
        progressDialog.setCanceledOnTouchOutside(false);
        FileDownloader.getImpl().create(url)
                .setPath(DOWN_LOAD_APK_PATH)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        showLog("pending");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.show();
                            }
                        });
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                        showLog("connected");
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        showLog("progress "+soFarBytes+" total is "+totalBytes);
                        final StringBuilder builder = new StringBuilder();
                        builder.append("程序升级中……")
                                .append(soFarBytes/1024)
                                .append("KB/")
                                .append(totalBytes/1024)
                                .append("KB");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.setMessage(builder.toString());
                            }
                        });
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        showLog("down completed");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                            }
                        });
                        installApk();
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                            }
                        });
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        showLog("error "+e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this,
                                        "升级失败，请稍后再试",Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();
    }

    /**
     *  安装apk
     */
    void installApk() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File apkFile = new File(DOWN_LOAD_APK_PATH);
        intent.setDataAndType(Uri.fromFile(apkFile),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }
}
