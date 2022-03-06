package com.pitaya.smart_rest.dianpu.query;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.pitaya.smart_rest.base.BaseQuery;
import lombok.Data;

/**
 * @ClassName CardQuery
 * @author: lucine
 * @Description TODO
 * @date 2022/3/1 15:06
 * @Version 1.0版本
 */
@Data
public class CardQuery extends BaseQuery {
    private String logicName;
    private Integer stateId;
    private String descriptions;

}
