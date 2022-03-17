package com.pitaya.smart_rest.activity.query;

import com.pitaya.smart_rest.base.BaseQuery;
import lombok.Data;

/**
 * @ClassName ChargeActivityQuery
 * @author: lucine
 * @Description TODO
 * @date 2022/3/13 18:06
 * @Version 1.0版本
 */
@Data
public class ChargeActivityQuery extends BaseQuery {

    /**
     * 活动类型：0=送金额 1=送礼品
     */
    private Integer activityType;

    private Integer state;

    private String note;

    private String keyword;

    private Integer resId;
}
