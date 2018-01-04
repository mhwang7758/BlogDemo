package com.mhwang.assetspicoperation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button btn_selectPicture;
    ImageView iv_showPicSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_selectPicture = (Button) findViewById(R.id.btn_selectPicture);
        iv_showPicSelect = (ImageView) findViewById(R.id.iv_showPicSelect);

        btn_selectPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPictureSelect();
            }
        });
    }

    void toPictureSelect(){
        Intent intent = new Intent(this, PictureSelectDialog.class);
        startActivityForResult(intent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK){
            return;
        }

        String selectPicture = data.getStringExtra(PictureSelectDialog.KEY_PATH);

        if (TextUtils.isEmpty(selectPicture)){
            return;
        }

        Bitmap bitmap = FileUtil.getAssetsBitmap(this,selectPicture);

        iv_showPicSelect.setImageBitmap(bitmap);
    }
}
