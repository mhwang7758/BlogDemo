package com.mhwang.filenote;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Description :
 * Author :mhwang
 * Date : 2017/6/21
 * Version : V1.0
 */

public class FileUtil {

    private static void showLog(String msg){
        Log.e("FileUtil-->",msg);
    }

    /**
     *  文件路径
     */
    public static final String ROOT_PATH = Environment
            .getExternalStorageDirectory() + File.separator + "Mhwang";
    public static final String ERROR_DIR_PATH = ROOT_PATH + File.separator + "ErrorNote";

    /** 写日志
     * @param message
     */
    public static void writeNote(String message){
        // 保存异常信息的路径
        String path = ERROR_DIR_PATH + File.separator + "note"+DateUtil.getCurrentDate() + ".txt";
        String occurTime = DateUtil.getCurrentTime();
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            // 保存日志文件
            Writer writer = new FileWriter(file, true);
            writer.write(occurTime + message+"\n");
            writer.flush();
            writer.close();
        }catch (Exception e){

        }
    }

    /** 读取数据
     * @param path
     * @return
     */
    public static String read(String path){
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            String str = null;
            reader = new BufferedReader(new FileReader(new File(path)));
            while ((str = reader.readLine()) != null ){
                builder.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return builder.toString();
    }


}
