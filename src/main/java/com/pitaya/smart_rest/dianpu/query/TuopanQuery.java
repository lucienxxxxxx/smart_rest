package com.pitaya.smart_rest.dianpu.query;

import com.pitaya.smart_rest.base.BaseQuery;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName TuopanQuery
 * @author: lucine
 * @Description TODO
 * @date 2022/2/12 21:49
 * @Version 1.0版本
 */
@Data
public class TuopanQuery extends BaseQuery {
    private Integer id;
    /**
     * 字符串卡号
     */
    private String logicName;

    /**
     * 0=停用 1=启用
     */
    private Integer stateId;


    private String descriptions;

    /**
     * 餐厅id
     */
    private Integer resId;
}
