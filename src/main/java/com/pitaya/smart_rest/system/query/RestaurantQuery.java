package com.pitaya.smart_rest.system.query;

import com.pitaya.smart_rest.base.BaseQuery;
import lombok.Data;

/**
 * @ClassName RestaurantQuery
 * @author: lucine
 * @Description TODO
 * @date 2022/3/29 19:55
 * @Version 1.0版本
 */
@Data
public class RestaurantQuery extends BaseQuery {
    private String restaurantName;
    private Integer state;
}
