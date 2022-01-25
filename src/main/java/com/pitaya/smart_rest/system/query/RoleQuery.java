package com.pitaya.smart_rest.system.query;


import com.pitaya.smart_rest.base.BaseQuery;
import lombok.Data;

/**
 * @ClassName RoleQuery
 * @author: lucine
 * @Description TODO
 * @date 2021/12/10 14:17
 * @Version 1.0版本
 */
@Data
public class RoleQuery extends BaseQuery {
    private String roleName;
}
