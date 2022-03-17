package com.pitaya.smart_rest.activity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pitaya.smart_rest.base.BaseQuery;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName MemberActivityQuery
 * @author: lucine
 * @Description TODO
 * @date 2022/3/14 14:47
 * @Version 1.0版本
 */
@Data
public class MemberActivityQuery extends BaseQuery {

    private Integer memberId;

    /**
     * 是否已经核销：0=未核销 1=核销
     */
    private Integer isUse;

    private String mobile;

    private String note;

    private String trueName;
}
