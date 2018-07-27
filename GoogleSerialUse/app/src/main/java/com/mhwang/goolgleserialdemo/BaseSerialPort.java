package com.mhwang.goolgleserialdemo;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import android_serialport_api.SerialPort;

/**
 * Description : 串口通信基类，定义串口通信的常量及操作方法
 * Author :mhwang
 * Date : 2018/6/14
 * Version : V1.0
 */

abstract class BaseSerialPort {

    InputStream mInputStream = null;
    OutputStream mOutputStream = null;
    SerialPort mSerialPort = null;
    Map<String,String> mSerialMap;

    abstract public boolean write(String data);
    abstract public boolean write(byte[] data);
    abstract public String receive();
    abstract public byte[] receiveBytes();
    abstract public boolean open();
    abstract public boolean close();

    /** 读取数据
     * @param inputStream 串口对象
     * @return byte[]数组，若没有,返回null
     */
    byte[] readData(InputStream inputStream){
//        // 暂停200毫秒以等待数据返回       有些串口延迟会出现粘包，比如门卡板，因此根据实际情况添加
//        SystemClock.sleep(200);
        int size;
        try {
            if (inputStream == null) {
                showLog("inputStream is null");
                return null;
            }
            byte[] buffer = new byte[256];
            size = inputStream.read(buffer);
            byte[] data = new byte[size];
            System.arraycopy(buffer,0,data,0,size);
            if (size > 0) {
                return data;
            }else{
                showLog("receive no data");
            }
        }catch (Exception e){
            showLog(e.toString());
        }
        return null;
    }

    /** 打开串口
     * @param device 要打开的串口文件
     * @param baudrate 波特率
     * @param flag 标志
     * @return true,打开成功，false，打开失败
     */
    boolean openSerialPort(File device, int baudrate, int flag){
        try {
            mSerialPort = new SerialPort(device, baudrate, flag);
            mInputStream = mSerialPort.getInputStream();
            mOutputStream = mSerialPort.getOutputStream();
        } catch (IOException e) {
            mInputStream = null;
            mOutputStream = null;
            e.printStackTrace();
            return false;
        }

        if (mInputStream == null || mOutputStream == null) return false;

        return true;
    }

    boolean closeSerialPort(){
//        try {
//            if (mInputStream != null){
//                mInputStream.close();
//                mInputStream = null;
//            }
//            if (mOutputStream != null){
//                mOutputStream.close();
//                mOutputStream = null;
//            }
//            if (mSerialPort != null){
//                mSerialPort.close();
//                mSerialPort = null;
//            }
//        }catch (Exception e){
//            FileUtil.writeNote("BaseSerialPort-->"+ AppError.SERIAL_CLOSE_FAIL+e.toString());
//            return false;
//        }

        return true;
    }

    private void showLog(String s) {
        Log.d("BaseSerialPort-->",s);
    }
}
