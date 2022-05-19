package com.pitaya.smart_rest.stats.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pitaya.smart_rest.annotation.Double2Serializer;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName Revenue
 * @author: lucine
 * @Description TODO
 * @date 2022/3/20 21:48
 * @Version 1.0版本
 */
@Data
public class Revenue {
    @TableId
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;
    private Integer orderSum;//订单总数
    @JsonSerialize(using = Double2Serializer.class)
    private Double total=0d;
    @JsonSerialize(using = Double2Serializer.class)
    private Double refundTotal=0d;
    @JsonSerialize(using = Double2Serializer.class)
    private Double avg=0d;//订单均价
    @JsonSerialize(using = Double2Serializer.class)
    private Double income=0d;//实际收入
    @JsonSerialize(using = Double2Serializer.class)
    private Double virtualAcc=0d;
    @JsonSerialize(using = Double2Serializer.class)
    private Double giftAcc=0d;
    @JsonSerialize(using = Double2Serializer.class)
    private Double allowanceAcc=0d;
    @JsonSerialize(using = Double2Serializer.class)
    private Double cashAcc=0d;
    @JsonSerialize(using = Double2Serializer.class)
    private Double chargeAcc=0d;

}
