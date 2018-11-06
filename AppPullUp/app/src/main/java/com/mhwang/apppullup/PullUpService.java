package com.mhwang.apppullup;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.util.List;

/** 后台拉起服务
 * Author : mhwang
 * Date : 2018/11/6
 * Version : V1.0
 */
public class PullUpService extends Service implements Runnable{
    private boolean running = false;
    private Intent intent;
    private Thread thread;

    private static void showLog(String s){
        Log.d("PullUpService=>", s);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate(){
        super.onCreate();
        intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        showLog("onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showLog("onStartCommand");
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        showLog("onDestroy");
        running = false;
        if(thread != null && !thread.isInterrupted()){
            try {
                thread.interrupt();
                thread.join(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        thread = null;
        super.onDestroy();
    }

    @Override
    public void run() {
        running = true;
        showLog("background service start");
        while(running){
            showLog("check background");
            if(isBackground(PullUpService.this)){
                showLog("restart activity");
                startActivity(intent);
            }
            SystemClock.sleep(10 * 1000);         // 由于模拟器较卡，这里提高10秒检查一次
        }
    }

    /** 判断是否处于后台
     * @param context
     * @return true：处于后台, false：不处于后台
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if(appProcesses == null){
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

}
