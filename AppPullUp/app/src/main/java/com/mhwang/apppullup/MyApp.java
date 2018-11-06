package com.mhwang.apppullup;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

/**
 * Author : mhwang
 * Date : 2018/11/6
 * Version : V1.0
 */
public class MyApp extends Application {

    private int mActivityCount = 0;
    private void showLog(String s){
        Log.d("MyApp=>",s);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this, PullUpService.class);
        startService(intent);
//        startActivityLifeListener();
    }

    /**
     *  监听各个Activity生命周期情况
     */
    private void startActivityLifeListener(){
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                showLog("add activity "+activity.toString());
            }

            @Override
            public void onActivityStarted(Activity activity) {
                mActivityCount++;
                showLog(activity.getComponentName()+" onActivityStarted activityCounts= "+mActivityCount);
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {
                showLog(activity.getComponentName().toString()+" onActivityPaused");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                mActivityCount--;
                showLog(activity.getComponentName()+" onActivityStopped activityCounts= "+mActivityCount);
                if (mActivityCount <= 0){
                    // 5秒后拉起
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showLog("try to restart");
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }, 5000);
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                showLog("remove activity "+activity.toString());
            }
        });
    }
}
