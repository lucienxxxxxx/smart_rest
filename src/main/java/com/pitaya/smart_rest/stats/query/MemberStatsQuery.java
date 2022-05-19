package com.pitaya.smart_rest.stats.query;

import com.pitaya.smart_rest.base.BaseQuery;
import lombok.Data;

/**
 * @ClassName MemberStatsQuery
 * @author: lucine
 * @Description TODO
 * @date 2022/4/14 0:15
 * @Version 1.0版本
 */
@Data
public class MemberStatsQuery extends BaseQuery {
    private String startDateTime;
    private String endDateTime;
    private String orgId;
}
