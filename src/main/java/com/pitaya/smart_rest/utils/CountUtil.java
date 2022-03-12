package com.pitaya.smart_rest.utils;

import java.math.BigDecimal;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CountUtil
 * @author: lucine
 * @Description 运算工具类
 * @date 2022/3/12 11:00
 * @Version 1.0版本
 */
public class CountUtil {

    public static Float  floatAdd(Float[] floats) {
        BigDecimal result=new BigDecimal(Float.toString(0F));
        List<BigDecimal> bigDecimals = new ArrayList<BigDecimal>();
        for (int i = 0; i < floats.length; i++) {
            BigDecimal b = new BigDecimal(Float.toString(floats[i]));
            result=result.add(b);
        }
        return result.floatValue();
    }
}
