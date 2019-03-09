package mhwang.com.jnidemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import mhwang.com.jni.Demo;

public class JniDemoActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        Log.d("MainActivity=>","static");
        System.loadLibrary("native-lib");
        System.loadLibrary("demo-lib");
    }

    private int[] nums = {2,3,3,4,5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni_demo);
        Log.d("MainActivity=>","onCreate");

        // Example of a call to a native method
        final TextView tv = findViewById(R.id.sample_text);
        Button btn_sure = findViewById(R.id.btn_sure);
        final EditText et_input = findViewById(R.id.et_input);

        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = et_input.getText().toString().trim();
                int target = Integer.parseInt(text);
                int[] result = new Demo().twoSum(nums, target);
                if (result == null){
                    tv.setText("没有符合target的数组下标");
                }else {
                    tv.setText("jni 执行 twoSum [" + result[0] + "," + result[1] + "]");
                }
            }
        });

//        tv.setText(stringFromJNI());

//        int sumResult = new Demo().sum(2, 3);
//        tv.setText("jni 执行 2 + 3 = "+sumResult);

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
