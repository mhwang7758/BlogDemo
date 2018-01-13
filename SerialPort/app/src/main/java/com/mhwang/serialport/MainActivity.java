package com.mhwang.serialport;

import android.Manifest;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class MainActivity extends AppCompatActivity {
    Button btn_openSerialPort;

    boolean hasPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_openSerialPort = (Button) findViewById(R.id.btn_openSerialPort);
        btn_openSerialPort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasPermission) {
                    SerialPortUtil.getInstance().initPort();
                }else {
                    Toast.makeText(MainActivity.this,"打开失败，没有足够的权限",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 申请文件读写权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionGen.with(MainActivity.this)
                    .addRequestCode(100)
                    .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .request();
        }else {
            hasPermission = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    private void initData(){
        hasPermission = true;
    }

    @PermissionFail(requestCode = 100)
    private void requestPermissionFail(){
        Toast.makeText(this, "permission deny", Toast.LENGTH_SHORT).show();
    }


}
