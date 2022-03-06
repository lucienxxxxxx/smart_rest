package com.pitaya.smart_rest.guke.query;

import com.pitaya.smart_rest.base.BaseQuery;
import lombok.Data;

/**
 * @ClassName OnlineMemberQuery
 * @author: lucine
 * @Description TODO
 * @date 2022/3/3 17:14
 * @Version 1.0版本
 */
@Data
public class MemberQuery extends BaseQuery {
    private Integer id;
    private String trueName;
    private String mobile;
    private Integer resId;
    /**
     * 0=启用 1=禁用  2=挂失 3=删除
     */
    private Integer state;

    private String note;
    private Integer orgId;
    /**
     * 逻辑号
     */
    private String logicName;
}
