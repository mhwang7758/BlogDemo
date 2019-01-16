package mhwang.com.jni;

/** jni interface
 * Author : mhwang
 * Date : 2019/1/16
 * Version : V1.0
 */
public class Demo {
    public native int sum(int a, int b);
    public native int[] twoSum(int[] nums, int target);
}
