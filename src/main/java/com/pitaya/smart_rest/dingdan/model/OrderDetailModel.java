package com.pitaya.smart_rest.dingdan.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName OrderDetailModel
 * @author: lucine
 * @Description TODO
 * @date 2022/1/29 9:58
 * @Version 1.0版本
 */
@Data
public class OrderDetailModel {
    @TableId(value="id" , type = IdType.AUTO)
    private Integer id;
    private String orderId;
    private String foodName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
     * 0=正常 1=申请退款 2=退款成功
     */
    private Integer orderDetailState;


    private Float virtualAcc;

    /**
     * 赠送账号
     */
    private Float giftAcc;

    private Float allowanceAcc;

    private Float cashAcc;

    private Float chargeAcc;

    /**
     * 重量
     */
    private Double weight;
}
