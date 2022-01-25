package com.pitaya.smart_rest.system.service.impl;

import com.pitaya.smart_rest.system.entity.Permission;
import com.pitaya.smart_rest.system.mapper.PermissionMapper;
import com.pitaya.smart_rest.system.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色模块表 服务实现类
 * </p>
 *
 * @author lucien
 * @since 2022-01-22
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {
    @Resource
    private PermissionMapper permissionMapper;
    /***
     * 通过查询用户拥有的角色，角色拥有的资源，得到用户拥有的资源列表 （资源权限码）
     * @return java.util.List<java.lang.String>
     */
    public List<String> queryUserHasRoleHasPermissionByUserId(Integer userId) {
        return permissionMapper.queryUserHasRoleHasPermissionByUserId(userId);
    }
}
