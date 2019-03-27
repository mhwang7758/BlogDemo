package mhwang.com.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
 import android.util.AttributeSet;

 /**  桃心悦圆字体组件
 * Author : mhwang
 * Date : 2019/3/27
 * Version : V1.0
 */
public class TXYYView extends AppCompatTextView {

    public TXYYView(Context context) {
        super(context);
        initCustomFont(context);
    }

    public TXYYView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCustomFont(context);
    }

    public TXYYView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCustomFont(context);
    }

    private void initCustomFont(Context context){
        Typeface newFont = Typeface.createFromAsset(context.getAssets(), "fonts/taoxinyueyuan.ttf");   // 字体文件存放路径
        setTypeface(newFont);
    }
}
