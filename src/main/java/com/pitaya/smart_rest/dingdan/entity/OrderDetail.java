package com.pitaya.smart_rest.dingdan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单明细
 * </p>
 *
 * @author lucien
 * @since 2022-01-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_order_detail")
public class OrderDetail implements Serializable {

    @TableId(value="id" , type = IdType.AUTO)
    private Integer id;

    private Integer foodId;

    private String orderId;

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

    /**
     * member表中cashout_limit字段减少值
     */
    private Float cashoutPlus;


}
