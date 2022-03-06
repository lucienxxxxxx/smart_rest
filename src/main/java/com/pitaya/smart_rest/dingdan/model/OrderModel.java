package com.pitaya.smart_rest.dingdan.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName orderModel
 * @author: lucine
 * @Description TODO
 * @date 2022/1/29 12:26
 * @Version 1.0版本
 */
@Data
public class OrderModel {

    private String id;

    private Integer memberId;

    private String mobile;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderDate;

    /**
     * 0=订单完成 1=请求退款 2=退款成功
     */
    private Integer state;

    /**
     * 描述
     */
    private String descriptions;

    /**
     * 评论
     */
    private String comments;

    /**
     * 托盘卡卡号
     */
    private String tuopanId;
}
