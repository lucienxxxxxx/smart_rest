package com.pitaya.smart_rest.system.mapper;

import com.pitaya.smart_rest.system.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色模块表 Mapper 接口
 * </p>
 *
 * @author lucien
 * @since 2022-01-22
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    // 查询角色拥有的所有的资源ID的集合
    List<Integer> queryRoleHasModuleIdsByRoleId(Integer roleId);
    // 通过用户ID查询对应的资源列表（资源权限码）
    List<String> queryUserHasRoleHasPermissionByUserId(Integer userId);
}
