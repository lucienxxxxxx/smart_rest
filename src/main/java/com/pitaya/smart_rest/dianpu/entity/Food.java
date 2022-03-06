package com.pitaya.smart_rest.dianpu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 食物或菜品
 * </p>
 *
 * @author lucien
 * @since 2022-02-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_food")
public class Food implements Serializable {

    private static final long serialVersionUID = 1L;
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

    private Integer restId;
    private Float heat;
    private Float protein;
    private Float fat;
    private Float cellulose;
    private Float carbohydrate;
    private String descriptions;
    private Float discount;
}
