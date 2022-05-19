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

    private Integer memberId;//会员号码

    private String mobile;//手机号码

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderDate;//订单下订时间

    private Integer state;//0=订单完成 1=请求退款 2=退款成功

    private String descriptions;//描述

    private String comments;//评论

    private Integer tuopanId;//托盘卡卡号

    /**
     * 五类账号消费情况
     */
    private Float virtualAcc;

    private Float giftAcc;

    private Float allowanceAcc;

    private Float cashAcc;

    private Float chargeAcc;

    private Float total;//订单总额

    private Float refundTotal;//退款总额
}
