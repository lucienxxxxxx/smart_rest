package com.pitaya.smart_rest.system.query;

import com.pitaya.smart_rest.base.BaseQuery;
import lombok.Data;

/**
 * 用户条件查询
 */

@Data
public class UserQuery extends BaseQuery {

    private String userName; // 用户名
    private String trueName;
    private String phone; // 手机号
    private Integer state;
    private Integer resId;
    private Integer roleId;

}
