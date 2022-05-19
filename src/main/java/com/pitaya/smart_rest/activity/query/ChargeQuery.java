package com.pitaya.smart_rest.activity.query;

import com.pitaya.smart_rest.base.BaseQuery;
import lombok.Data;

/**
 * @ClassName ChargeQuery
 * @author: lucine
 * @Description TODO
 * @date 2022/4/13 11:25
 * @Version 1.0版本
 */
@Data
public class ChargeQuery extends BaseQuery {
    private String orgId;
    private Integer memberType;
}
