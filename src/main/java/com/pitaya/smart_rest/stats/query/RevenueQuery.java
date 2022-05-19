package com.pitaya.smart_rest.stats.query;

import com.pitaya.smart_rest.base.BaseQuery;
import lombok.Data;

/**
 * @ClassName RevenueQuery
 * @author: lucine
 * @Description TODO
 * @date 2022/3/20 21:44
 * @Version 1.0版本
 */
@Data
public class RevenueQuery extends BaseQuery {
    private String orgId;
    private Integer memberType;
    private String startDateTime;
    private String endDateTime;
}
