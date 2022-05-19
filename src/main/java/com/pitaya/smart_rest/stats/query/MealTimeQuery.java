package com.pitaya.smart_rest.stats.query;

import com.pitaya.smart_rest.base.BaseQuery;
import lombok.Data;

/**
 * @ClassName MealTimeQuery
 * @author: lucine
 * @Description TODO
 * @date 2022/3/26 13:36
 * @Version 1.0版本
 */
@Data
public class MealTimeQuery extends BaseQuery {
    private String orgId;
    private Integer memberType;
    private String startDateTime;
    private String endDateTime;
}
