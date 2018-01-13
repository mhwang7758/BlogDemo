package com.mhwang.filenote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    Button btn_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createProPictureDirectory();
        btn_test = (Button) findViewById(R.id.btn_test);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "you have clicked test!",
                        Toast.LENGTH_SHORT).show();
                // 保存本地日志
                FileUtil.writeNote("you have clicked test!");
            }
        });
    }


    /**
     *  创建日志文件夹
     */
    private void createProPictureDirectory(){
        File file = new File(FileUtil.ERROR_DIR_PATH);
        if (!file.exists()){
            file.mkdirs();
        }
    }
}
