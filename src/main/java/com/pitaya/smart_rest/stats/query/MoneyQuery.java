package com.pitaya.smart_rest.stats.query;

import com.pitaya.smart_rest.base.BaseQuery;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName MoneyQuery
 * @author: lucine
 * @Description TODO
 * @date 2022/3/27 21:28
 * @Version 1.0版本
 */
@Data
public class MoneyQuery extends BaseQuery {
    private String startDateTime;
    private String endDateTime;
}
