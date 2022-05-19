package com.pitaya.smart_rest.activity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 各类充值
 * </p>
 *
 * @author lucien
 * @since 2022-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_charge")
public class Charge implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value="id" , type = IdType.AUTO)
    private Integer id;
    /**
     * 谁被充值
     */
    private Integer memberId;

    /**
     * 谁做充值
     */
    private String chargerId;

    private Date createDate;

    /**
     * 充值金额
     */
    private Float virtualAcc;

    /**
     * 重置方式
     */
    private Float giftAcc;

    private Float allowanceAcc;

    private Float cashAcc;

    private Float chargeAcc;

    private String descriptions;


}
