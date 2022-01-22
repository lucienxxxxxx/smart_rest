package com.pitaya.smart_rest.base;

import lombok.Data;

/**
 * @ClassName ResultInfo
 * @author: lucine
 * @Description TODO
 * @date 2021/12/8 23:31
 * @Version 1.0版本
 */
@Data
public class ResultInfo {
    private Integer code=200;
    private String msg="success";
    private Object result;
}
