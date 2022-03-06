package com.pitaya.smart_rest.dianpu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @ClassName FoodModel
 * @author: lucine
 * @Description TODO
 * @date 2022/2/13 17:08
 * @Version 1.0版本
 */
@Data
public class FoodModel {
    @TableId(value="id" , type = IdType.AUTO)
    private Integer id;
    private String name;
    private Float price;
    /**
     * 0=克（显示50克）  1=份
     */
    private Integer priceMethod;

    private Integer dangkouId;

    private Integer tagId;

    private String tagName;

    private Integer restId;
    private Float heat;
    private Float protein;
    private Float fat;
    private Float cellulose;
    private Float carbohydrate;
    private String descriptions;
    private Float discount;
}
