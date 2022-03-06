package com.pitaya.smart_rest.dingdan.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pitaya.smart_rest.base.BaseQuery;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName OrderQuery
 * @author: lucine
 * @Description TODO
 * @date 2022/1/29 8:38
 * @Version 1.0版本
 */
@Data
public class OrderQuery extends BaseQuery {
    private String id;
    private Integer memberId;
    private String orderDate;
    private Integer state;
    private String tuopanId;
    private String mobile;
    private String endDate;
}
