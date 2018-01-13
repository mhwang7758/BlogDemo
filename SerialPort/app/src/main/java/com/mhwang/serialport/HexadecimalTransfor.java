package com.mhwang.serialport;


import java.math.BigInteger;

/**
 * Description :  进制转换类
 * Author :mhwang
 * Date : 2017/5/10
 * Version : V1.0
 */

public class HexadecimalTransfor {
    /**
     *  普通字符转换成16进制字符串
     * @param str
     * @return
     */
    public static String str2HexStr(String str)
    {
        byte[] bytes = str.getBytes();
        BigInteger bigInteger = new BigInteger(1, bytes);
        return bigInteger.toString(16);
    }

    /** 16进制的字符串转换成16进制字符串数组
     * @param src
     * @return
     */
    public static byte[] HexString2Bytes(String src) {
        int len = src.length() / 2;
        byte[] ret = new byte[len];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < len; i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }


    /** 字节数组转换成16进制字符串显示
     * @param b
     * @param length
     * @return
     */
    public static String bytes2HexString(byte[] b,int length) {
        String r = "";

        for (int i = 0; i < length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = "0" + hex;
            }
            r += hex.toUpperCase();
        }

        return r;
    }


    /** int转换成16进制字符串显示
     * @param i
     * @return
     */
    public static String int2HexString(int i) {
        String r = "";
        String hex = Integer.toHexString(i & 0xFF);
        if (hex.length() == 1) {
            hex = "0" + hex;
        }
        r += hex.toUpperCase();

        return r;
    }

    /** 16进制字符串转换10进制
     * @param str
     * @return
     */
    public static int hexString2Int(String str){
        return Integer.parseInt(str,16);
    }

    /** 获取16进制字符串每一位值
     * @param hexValue
     * @return
     */
    public static byte[] getEachByteValues(String hexValue){
        int hexLen = hexValue.length()/2;
        final int byteLength = 8;
        if (hexLen == 0){
            return null;
        }

        byte[] result = new byte[hexLen * byteLength];
        String[] hexs = new String[hexLen];
        int valueIndex = 0;
        // 获得字符串
        for(int i = 0; i < hexLen; i++){
            hexs[i] = hexValue.substring(valueIndex, valueIndex+2);
            valueIndex += 2;
        }
        int resultIndex = 0;
        // 将每个字符串字节复制到结果数组
        for(int i = 0; i < hexs.length; i++){
            byte[] bytes = getEachByteValue(hexs[i]);
            System.arraycopy(bytes, 0, result, resultIndex, byteLength);
            resultIndex += byteLength;
        }
        return result;
    }

    /** 获取16进制值的每一位值
     * @param hexValue (必须保证为2个字节长度的String，否则得不到值)
     * @return 返回长度为8的字节数组，每个值代表每一位的值
     */
    private static byte[] getEachByteValue(String hexValue) {
        if (hexValue.length() != 2){
            return null;
        }
        byte bValue = (byte)Integer.parseInt(hexValue,16);
        byte[] array = new byte[8];
        for(int i = 7; i >= 0; i--){
            array[i] = (byte)(bValue & 1);
            bValue = (byte)(bValue >> 1);
        }
        return array;
    }

    private static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序。
     *
     * @param ary
     *            byte数组
     * @param offset
     *            从数组的第offset位开始
     * @return int数值
     */
    public static int bytesToInt(byte[] ary, int offset) {
        int value;
        value = (int) ((ary[offset]&0xFF)
                | ((ary[offset+1]<<8) & 0xFF00)
                | ((ary[offset+2]<<16)& 0xFF0000)
                | ((ary[offset+3]<<24) & 0xFF000000));
        return value;
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。和intToBytes2（）配套使用
     */
    public static int bytesToInt2(byte[] src, int offset) {
        int value;
        value = (int) ( ((src[offset] & 0xFF)<<24)
                |((src[offset+1] & 0xFF)<<16)
                |((src[offset+2] & 0xFF)<<8)
                |(src[offset+3] & 0xFF));
        return value;
    }

}
