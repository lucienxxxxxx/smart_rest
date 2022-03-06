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
 * 订单退钱明细
 * </p>
 *
 * @author lucien
 * @since 2022-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_refund")
public class Refund implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value="id" , type = IdType.AUTO)
    private Integer id;
    private Integer orderDetailId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    private Integer isValid;

    /**
     * 0=订单完成 1=请求退款 2=退款成功
     */
    private Integer state;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    private Float virtualAcc;

    private Float giftAcc;

    private Float allowanceAcc;

    private Float cashAcc;

    private Float chargeAcc;

    private String descriptions;


}
