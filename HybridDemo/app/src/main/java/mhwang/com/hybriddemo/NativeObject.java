package mhwang.com.hybriddemo;

/** 用于封装拼接调用js方法的语句
 * Author : mhwang
 * Date : 2018/11/28
 * Version : V1.0
 */
public class NativeObject {

    /** 为了方便获取String 类型的字符串
     * @param s 加‘’号的参数
     * @return 加了‘’号的参数
     */
    private static String getJsStringParam(String s){
        return "'"+s+"'";
    }

    public static String makeSentence(String world1, String world2){
        return "javascript:makeSentence("+getJsStringParam(world1)+","+getJsStringParam(world2)+")";
    }

    public static String add(int a, int b){
        // 不是字符串的话不用加‘’号
        return "javascript:add("+a+","+b+")";
    }
}
