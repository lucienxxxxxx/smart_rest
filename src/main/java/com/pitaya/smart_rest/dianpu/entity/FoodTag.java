package com.pitaya.smart_rest.dianpu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author lucien
 * @since 2022-02-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_food_tag")
public class FoodTag implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value="id" , type = IdType.AUTO)
    private Integer id;
    /**
     * 菜品标签名
     */
    private String tagName;

    private Integer resId;
}
