package com.mhwang.gesturepresstest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Description :
 * Author :mhwang
 * Date : 2018/1/17
 * Version : V1.0
 */

public class GestureService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    GestureDetector mDetector;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mDetector = new GestureDetector(new MyGesture());
//        int i = 0;
//        while (i < 1000){
//            i++;
//            SystemClock.sleep(1000);
//        }
        return super.onStartCommand(intent, flags, startId);
    }

    class MyGesture extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d("-->","onFling");
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.d("-->","onLongPress");
            super.onLongPress(e);
        }
    }
}
