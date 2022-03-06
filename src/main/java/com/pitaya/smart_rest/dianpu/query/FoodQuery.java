package com.pitaya.smart_rest.dianpu.query;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.pitaya.smart_rest.base.BaseQuery;
import lombok.Data;

/**
 * @ClassName FoodQuery
 * @author: lucine
 * @Description TODO
 * @date 2022/2/13 17:09
 * @Version 1.0版本
 */
@Data
public class FoodQuery extends BaseQuery {

    private String name;

    private Float price;

    private Integer dangkouId;

    private Integer tagId;

    private Integer restId;

    private Float discount;
}
