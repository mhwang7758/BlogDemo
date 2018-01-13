package com.mhwang.serialport;

import android.os.SystemClock;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import qingwei.kong.serialportlibrary.SerialPort;

/**
 * Description : 串口工具类
 * Author :mhwang
 * Date : 2017/12/11
 * Version : V1.0
 */

public class SerialPortUtil {
    private static SerialPortUtil sUtil;
    private SerialPort mSerialPort;
    private OutputStream mOutStream;
    private InputStream mInputStream;

    public static final String PORT = "/dev/ttyS3";        //外接设备串口号
    public static final int BOUND_RATE = 115200;        //外接设备波特率

    private SerialPortUtil(){
    }

    public static SerialPortUtil getInstance(){
        if (sUtil == null){
            synchronized (SerialPortUtil.class){
                if (sUtil == null){
                    sUtil = new SerialPortUtil();
                }
            }
        }
        return sUtil;
    }

    private void showLog(String s) {
        Log.d("SerialPortUtil-->",s);
    }


    public void initPort(){
        try {
            mSerialPort = new SerialPort(new File(PORT), BOUND_RATE, 0);
            mInputStream = mSerialPort.getInputStream();
            mOutStream = mSerialPort.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** 发送数据
     * @param b
     */
    public void write2Port(byte[] b){
        try {
            mOutStream.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SystemClock.sleep(200);
        // 如果发送有返回的需要及时读取掉返回的数据，否则会和后来的返回数据一起粘着
        String result = receiveData();
        showLog("shake hands result is "+result);
    }

    /** 取出数据
     * @return
     */
    public String receiveData(){
        // 暂停200毫秒以等待数据返回
        int size;
        try {
            if (mInputStream == null) return null;
            byte[] buffer = new byte[256];
            size = mInputStream.read(buffer);
            if (size > 0) {
                // 获取接收的指令16进制字符串
                final String hexResult = HexadecimalTransfor.bytes2HexString(buffer, size);
                showLog("receive door msg : "+hexResult);
                return hexResult;
            }
        }catch (Exception e){
            showLog(e.toString());
        }
        return null;
    }

}


