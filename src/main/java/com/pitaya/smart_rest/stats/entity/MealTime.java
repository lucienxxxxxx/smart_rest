package com.pitaya.smart_rest.stats.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pitaya.smart_rest.annotation.Double2Serializer;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName MealTime
 * @author: lucine
 * @Description TODO
 * @date 2022/3/26 13:36
 * @Version 1.0版本
 */
@Data
public class MealTime {
    @TableId
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    /**
     * 早餐时段
     */
    @JsonSerialize(using = Double2Serializer.class)
    private Double breakfastTotal=0d;//总额
    private Integer breakfastNum;//订单总数
    @JsonSerialize(using = Double2Serializer.class)
    private Double breakfastAvg=0d;//均价

    /**
     * 午餐时段
     */
    @JsonSerialize(using = Double2Serializer.class)
    private Double lunchTotal=0d;
    private Integer lunchNum;
    @JsonSerialize(using = Double2Serializer.class)
    private Double lunchAvg=0d;

    /**
     * 晚餐时段
     */
    @JsonSerialize(using = Double2Serializer.class)
    private Double dinnerTotal=0d;
    private Integer dinnerNum;
    @JsonSerialize(using = Double2Serializer.class)
    private Double dinnerAvg=0d;

    @JsonSerialize(using = Double2Serializer.class)
    private Double total=0d;
}
