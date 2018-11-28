package mhwang.com.hybriddemo;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Author : mhwang
 * Date : 2018/11/28
 * Version : V1.0
 */
public class JSObject {
    private Context mContext;
    public JSObject(Context context) {
        mContext = context;
    }

    @JavascriptInterface
    public int add(int a, int b){
        return a + b;
    }

    @JavascriptInterface
    public void showToast(String msg){
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
