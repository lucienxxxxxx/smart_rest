package com.pitaya.smart_rest.stats.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pitaya.smart_rest.annotation.Double2Serializer;
import lombok.Data;

/**
 * @ClassName foodStats
 * @author: lucine
 * @Description TODO
 * @date 2022/4/14 21:00
 * @Version 1.0版本
 */
@Data
public class FoodStats {
    @TableId
    private Integer id;
    private String date;
    private String foodName;
    @JsonSerialize(using = Double2Serializer.class)
    private Double price;
    @JsonSerialize(using = Double2Serializer.class)
    private Double priceAmount;
    private Integer goodsAmount;
}
