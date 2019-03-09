//
// Created by wangminghai on 2019/1/16.
//

#include "demo.h"

Demo::Demo() {

}

int Demo::sum(int a, int b) {
    return a + b;
}

int* Demo::twoSum(int nums[], int target) {
    int length = sizeof(nums);
    if (length < 2){
        return nullptr;
    }

    int *result = nullptr;
    for (int i = 0; i < length; i++){
        for (int j = i + 1; j < length; j++){
            if (nums[i] + nums[j] == target){
                result = new int[2];
                result[0] = i;
                result[1] = j;
                break;
            }
        }
    }
    return result;
}