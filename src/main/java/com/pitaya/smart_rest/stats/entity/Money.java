package com.pitaya.smart_rest.stats.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pitaya.smart_rest.annotation.Double2Serializer;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName Money
 * @author: lucine
 * @Description TODO
 * @date 2022/3/27 21:25
 * @Version 1.0版本
 */
@Data
public class Money {

    @TableId
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;
    @JsonSerialize(using = Double2Serializer.class)
    private Double chargeVirtualAcc;
    @JsonSerialize(using = Double2Serializer.class)
    private Double chargeGiftAcc;
    @JsonSerialize(using = Double2Serializer.class)
    private Double chargeAllowanceAcc;
    @JsonSerialize(using = Double2Serializer.class)
    private Double chargeCashAcc;
    @JsonSerialize(using = Double2Serializer.class)
    private Double chargeChargeAcc;
    @JsonSerialize(using = Double2Serializer.class)
    private Double refundVirtualAcc;
    @JsonSerialize(using = Double2Serializer.class)
    private Double refundGiftAcc;
    @JsonSerialize(using = Double2Serializer.class)
    private Double refundAllowanceAcc;
    @JsonSerialize(using = Double2Serializer.class)
    private Double refundCashAcc;
    @JsonSerialize(using = Double2Serializer.class)
    private Double refundChargeAcc;
    @JsonSerialize(using = Double2Serializer.class)
    private Double chargeTotal;
    @JsonSerialize(using = Double2Serializer.class)
    private Double withdrawalTotal;
    @JsonSerialize(using = Double2Serializer.class)
    private Double refundTotal;
    @JsonSerialize(using = Double2Serializer.class)
    private Double orderTotal;
}
