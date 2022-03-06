package com.pitaya.smart_rest.dianpu.query;

import com.pitaya.smart_rest.base.BaseQuery;
import lombok.Data;

/**
 * @ClassName StaffQuery
 * @author: lucine
 * @Description TODO
 * @date 2022/3/1 17:20
 * @Version 1.0版本
 */
@Data
public class StaffQuery extends BaseQuery {

    private String trueName;
    private String mobile;
    /**
     * 0=启用 1=禁用  2=挂失 3=删除
     */
    private Integer state;
    private String note;

}
