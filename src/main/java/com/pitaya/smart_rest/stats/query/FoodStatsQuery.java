package com.pitaya.smart_rest.stats.query;

import com.pitaya.smart_rest.base.BaseQuery;
import lombok.Data;

/**
 * @ClassName FoodStatsQuery
 * @author: lucine
 * @Description TODO
 * @date 2022/4/14 21:06
 * @Version 1.0版本
 */
@Data
public class FoodStatsQuery extends BaseQuery {
    private String orgId;
    private Integer memberType;
    private String startDateTime="";
    private String endDateTime="";
}
