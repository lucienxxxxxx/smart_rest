package com.pitaya.smart_rest.exceptions;


import lombok.Data;

/**
 * 自定义权限异常类
 *
 * 乐字节：专注线上IT培训
 * 答疑老师微信：lezijie
 */
@Data
public class AuthException extends RuntimeException {
    private Integer code=400;
    private String msg="暂无权限!";


    public AuthException() {
        super("暂无权限!");
    }

    public AuthException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public AuthException(Integer code) {
        super("暂无权限!");
        this.code = code;
    }

    public AuthException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

}
