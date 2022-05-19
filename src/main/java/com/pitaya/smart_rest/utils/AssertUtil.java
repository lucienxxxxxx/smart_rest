package com.pitaya.smart_rest.utils;


import com.pitaya.smart_rest.exceptions.ParamsException;

/**
 * @ClassName AssertUtil
 * @author: lucine
 * @Description TODO
 * @date 2021/12/9 0:19
 * @Version 1.0版本
 */
public class AssertUtil {


    /**
     * 判断条件是否满足
     *  如果条件满足，则抛出参数异常
     * @param flag
     * @param msg
     * @return void
     */
    public  static void isTrue(Boolean flag, String msg){
        if(flag){
            throw  new ParamsException(msg);
        }
    }



}

