package mhwang.com.wxpaydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.SortedMap;
import java.util.TreeMap;

import mhwang.com.util.WxPayHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tv_signResult = findViewById(R.id.tv_signResult);
        Button btn_createWxPayParams = findViewById(R.id.btn_createWxPayParams);
        btn_createWxPayParams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = "192006250b4c09247ec02edce69f6a2d";
                SortedMap<Object, Object> map = new TreeMap<>();
                map.put("appid","wxd930ea5d5a258f4f");
                map.put("mch_id","10000100");
                map.put("device_info","1000");
                map.put("body","test");
                map.put("nonce_str","ibuaiVcKdpRxkhJA");           // 这里为了跟文档的结果对应，直接使用文档的随机数
                String sign = WxPayHelper.createSign(key, map);
                map.put("sign",sign);
                String xmlParam = WxPayHelper.map2XML(map);
                tv_signResult.setText(xmlParam);
            }
        });
    }
}
