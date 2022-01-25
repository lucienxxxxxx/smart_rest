package com.pitaya.smart_rest.system.service;

import com.pitaya.smart_rest.system.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色模块表 服务类
 * </p>
 *
 * @author lucien
 * @since 2022-01-22
 */
public interface IPermissionService extends IService<Permission> {
    public List<String> queryUserHasRoleHasPermissionByUserId(Integer userId);
}
