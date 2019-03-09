package mhwang.com.importmoduledemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mhwang.com.jni.Demo;
import mhwang.com.jnidemo.JniDemoActivity;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("demo-lib");
    }
    TextView tv_result;
    Button btn_test;
    Button btn_intent2Test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_result = findViewById(R.id.tv_result);
        btn_test = findViewById(R.id.btn_test);
        btn_intent2Test = findViewById(R.id.btn_intent2Test);

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Demo demo = new Demo();
                int result = demo.sum(1,1);
                tv_result.setText(""+result);
            }
        });

        btn_intent2Test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JniDemoActivity.class);
                startActivity(intent);
            }
        });
    }
}
