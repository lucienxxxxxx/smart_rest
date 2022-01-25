package com.pitaya.smart_rest.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.system.entity.Permission;
import com.pitaya.smart_rest.system.entity.Role;
import com.pitaya.smart_rest.system.entity.User;
import com.pitaya.smart_rest.system.mapper.*;
import com.pitaya.smart_rest.system.query.RoleQuery;
import com.pitaya.smart_rest.system.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pitaya.smart_rest.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author lucien
 * @since 2022-01-22
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private ModuleMapper moduleMapper;

    @Override
    public Map<String, Object> selectRolesByParams(RoleQuery roleQuery) {
        //查询器
        QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>();
        //查询条件设置

        //逻辑删除
        queryWrapper.eq("is_valid", 1);

        if (roleQuery.getRoleName() != null) {
            queryWrapper.like("role_name", roleQuery.getRoleName());
        }

        //分页
        Page<Role> pages = new Page<Role>(roleQuery.getPage(), roleQuery.getLimit());
        IPage<Role> iPage = roleMapper.selectPage(pages, queryWrapper);
        List<Role> list = iPage.getRecords();
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
        linkedHashMap.put("code", 0);
        linkedHashMap.put("msg", "");
        linkedHashMap.put("count", iPage.getTotal());
        linkedHashMap.put("data", list);
        return linkedHashMap;
    }

    @Override
    public void addOrUpdate(Role role, Integer flag) {
        if (flag == 0) {
            //参数校验
            checkRoleParams(role.getRoleName(), null);
            //默认参数设置
            role.setUpdateDate(new Date());
            role.setCreateDate(new Date());
            role.setIsValid(1);
            AssertUtil.isTrue(roleMapper.insert(role) != 1, "角色添加失败");
        } else if (flag == 1) {
            //参数校验
            checkRoleParams(role.getRoleName(), role.getId());
            //默认参数设置
            role.setUpdateDate(new Date());
            role.setIsValid(1);
            AssertUtil.isTrue(roleMapper.updateById(role) != 1, "角色修改失败");
        }


    }


    private void checkRoleParams(String roleName, Integer roleId) {
        AssertUtil.isTrue(StringUtils.isBlank(roleName), "角色名不能为空");
        QueryWrapper<Role> queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_name", roleName);
        Role role = roleMapper.selectOne(queryWrapper);
        AssertUtil.isTrue(role != null && !(role.getId().equals(roleId)), "该角色名已经存在");
    }

    @Override
    public void del(Integer roleId) {
        Role role = roleMapper.selectById(roleId);
        AssertUtil.isTrue(roleId == null || role == null, "该角色不存在");
        role.setIsValid(0);
        AssertUtil.isTrue(roleMapper.updateById(role) != 1, "角色删除失败");
    }

    @Override
    public List<Map<String, Object>> queryAllRolesByUserId(Integer userId) {
//        User user = userMapper.selectById(userId);
//        AssertUtil.isTrue(userId==null||user==null,"该用户不存在");
        return roleMapper.queryAllRoles(userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addGrant(Integer roleId, Integer[] mIds) {
        // 1. 通过角色ID查询对应的权限记录
        QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
        permissionQueryWrapper.eq("role_id",roleId);
        Integer count = permissionMapper.selectCount(permissionQueryWrapper);
        // 2. 如果权限记录存在，则删除对应的角色拥有的权限记录
        if (count > 0) {
            // 删除权限记录
            permissionMapper.delete(permissionQueryWrapper);
        }
        // 3. 如果有权限记录，则添加权限记录
        if (mIds != null &&  mIds.length > 0) {


            // 遍历资源ID数组
            for(Integer mId: mIds) {
                Permission permission = new Permission();
                permission.setModuleId(mId);
                permission.setRoleId(roleId);

                permission.setAclValue(moduleMapper.selectById(mId).getOptValue());
                permission.setCreateDate(new Date());
                permission.setUpdateDate(new Date());
                // 将对象设置到集合中
                AssertUtil.isTrue(permissionMapper.insert(permission) != 1, "角色授权失败！");
            }


        }
    }
}
