package com.pitaya.smart_rest.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pitaya.smart_rest.system.entity.Module;
import com.pitaya.smart_rest.system.entity.Permission;
import com.pitaya.smart_rest.system.entity.User;
import com.pitaya.smart_rest.system.entity.UserRole;
import com.pitaya.smart_rest.system.mapper.*;
import com.pitaya.smart_rest.system.model.ModuleTree;
import com.pitaya.smart_rest.system.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private ModuleMapper moduleMapper;

    /***
     * 通过查询用户拥有的角色，角色拥有的资源，得到用户拥有的资源列表 （资源权限码）
     * @return java.util.List<java.lang.String>
     */
    public List<String> queryUserHasRoleHasPermissionByUserId(Integer userId) {
        return permissionMapper.queryUserHasRoleHasPermissionByUserId(userId);
    }


    @Override
    public List<ModuleTree> queryModelPermissions(Integer userId) {
        List<ModuleTree> moduleList = moduleMapper.queryModules(userId);
        List<ModuleTree> modules = new ArrayList<>();
//        先找到所有父级菜单
        moduleList.forEach(module -> {
            //如果是父级
            if (module.getPid() == -1) {
                modules.add(module);
            }
        });
        modules.forEach(module -> {
            module.setSubMenus(getChild(module.getId(), moduleList));
        });
        return modules;
    }

    private List<ModuleTree> getChild(Integer pid, List<ModuleTree> moduleList) {
        List<ModuleTree> childList = new ArrayList<>();
        moduleList.forEach(module -> {
            if (module.getPid() == pid) {
                childList.add(module);
            }
        });

        childList.forEach(child->{
            child.setSubMenus(getChild(child.getId(), moduleList));
        });

        if (childList.size()==0){
            return new ArrayList<>();
        }
        return childList;
    }


}
