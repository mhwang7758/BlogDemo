package com.mhwang.assetspicoperation;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

/**
 * Description :
 * Author :mhwang
 * Date : 2017/6/20
 * Version : V1.0
 */

public class PictureSelectDialog extends Activity{
    private GridView gv_ProductPic;
    private PictureSelectAdapter mAdapter;
    private List<String> mPicturePaths;
    public final static String KEY_PATH = "path";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_select_product_picture);
        gv_ProductPic = (GridView) findViewById(R.id.gv_productPic);
        // 获取所有资源图片
        mPicturePaths = FileUtil.getAssetPicPath(this);
        mAdapter = new PictureSelectAdapter(this, mPicturePaths);
        gv_ProductPic.setAdapter(mAdapter);
        addListener();
    }

    private void addListener(){
        gv_ProductPic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String path = mPicturePaths.get(position);
                Intent data = new Intent();
                data.putExtra(KEY_PATH,path);
                setResult(RESULT_OK,data);
                finish();
            }
        });
    }


}
