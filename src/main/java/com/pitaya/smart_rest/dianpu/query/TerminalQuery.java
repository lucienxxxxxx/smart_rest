package com.pitaya.smart_rest.dianpu.query;

import com.pitaya.smart_rest.base.BaseQuery;
import lombok.Data;

/**
 * @ClassName TerminalQuery
 * @author: lucine
 * @Description TODO
 * @date 2022/2/11 9:24
 * @Version 1.0版本
 */
@Data
public class TerminalQuery extends BaseQuery {
    private Integer restaurantId;
    private Integer state;
    private Integer type;
    private String terminalName;
}
