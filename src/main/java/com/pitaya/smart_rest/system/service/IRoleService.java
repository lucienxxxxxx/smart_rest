package com.pitaya.smart_rest.system.service;

import com.pitaya.smart_rest.system.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pitaya.smart_rest.system.query.RoleQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author lucien
 * @since 2022-01-22
 */
public interface IRoleService extends IService<Role> {

    Map<String, Object> selectRolesByParams(RoleQuery roleQuery);

    void addOrUpdate(Role role,Integer flag);


    void del(Integer roleId);

    List<Map<String,Object>> queryAllRolesByUserId(Integer userId);

    void addGrant(Integer roleId, Integer[] mIds);
}
