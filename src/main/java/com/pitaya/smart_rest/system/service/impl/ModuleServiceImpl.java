package com.pitaya.smart_rest.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pitaya.smart_rest.system.entity.Permission;
import com.pitaya.smart_rest.system.mapper.ModuleMapper;
import com.pitaya.smart_rest.system.mapper.PermissionMapper;
import com.pitaya.smart_rest.system.model.TreeModel;
import com.pitaya.smart_rest.system.service.IModuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pitaya.smart_rest.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.pitaya.smart_rest.system.entity.Module;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限模块 服务实现类
 * </p>
 *
 * @author lucien
 * @since 2022-01-22
 */
@Service
public class ModuleServiceImpl extends ServiceImpl<ModuleMapper, Module> implements IModuleService {


    @Resource
    private ModuleMapper moduleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteModule(Integer id) {
        // 判断id是否为空
        AssertUtil.isTrue(null == id, "待删除记录不存在！");
        // 通过id查询资源对象
        Module temp = moduleMapper.selectById(id);
        // 判断资源对象是否为空
        AssertUtil.isTrue(null == temp, "待删除记录不存在！");

        // 如果当前资源存在子记录(将id当做父Id查询资源记录)
        QueryWrapper<Module> moduleQueryWrapper = new QueryWrapper<>();
        moduleQueryWrapper.eq("parent_id","id");
        Integer count = moduleMapper.selectCount(moduleQueryWrapper);
        // 如果存在子记录，则不可删除
        AssertUtil.isTrue(count > 0, "该资源存在子记录，不可删除！");

        // 通过资源id查询权限表中是否存在数据
        QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
        permissionQueryWrapper.eq("module_id",id);
        count = permissionMapper.selectCount(permissionQueryWrapper);
        // 判断是否存在，存在则删除
        if (count > 0) {
            // 删除指定资源ID的权限记录
            permissionMapper.delete(permissionQueryWrapper);
        }

        // 设置记录无效
        temp.setIsValid(0);
        temp.setUpdateDate(new Date());

        // 执行更新
        AssertUtil.isTrue(moduleMapper.updateById(temp) < 1, "删除资源失败！");
    }

    /**
     * 修改资源
     * @param module
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateModule(Module module) {
        /* 1. 参数校验 */
        // id 非空，数据存在
        // 非空判断
        AssertUtil.isTrue(null == module.getId(), "待更新记录不存在！");
        // 通过id查询资源对象
        Module temp = moduleMapper.selectById(module.getId());
        // 判断记录是否存在
        AssertUtil.isTrue(null == temp, "待更新记录不存在！");

        // 层级 grade  非空 0|1|2
        Integer grade = module.getGrade();
        AssertUtil.isTrue(null == grade || !(grade == 0 || grade == 1 || grade == 2), "菜单层级不合法！");

        // 模块名称 moduleName      非空，同一层级下模块名称唯一 （不包含当前修改记录本身）
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()), "模块名称不能为空！");
        // 通过层级与模块名称查询资源对象
        QueryWrapper<Module> moduleQueryWrapper = new QueryWrapper<Module>();
        moduleQueryWrapper.eq("grade",grade);
        moduleQueryWrapper.eq("module_name",module.getModuleName());
        moduleQueryWrapper.eq("is_valid",1);
        temp = moduleMapper.selectOne(moduleQueryWrapper);
        if (temp != null) {
            AssertUtil.isTrue(!(temp.getId()).equals(module.getId()), "该层级下菜单名已存在！");
        }

        // 地址 url   二级菜单（grade=1），非空且同一层级下不可重复（不包含当前修改记录本身）
        if (grade == 1) {
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()), "菜单URL不能为空！");
            // 通过层级与菜单URl查询资源对象
            QueryWrapper<Module> moduleQueryWrapper1 = new QueryWrapper<Module>();
            moduleQueryWrapper1.eq("grade",grade);
            moduleQueryWrapper1.eq("url",module.getUrl());
            moduleQueryWrapper1.eq("is_valid",1);
            temp = moduleMapper.selectOne(moduleQueryWrapper1);
            // 判断是否存在
            if (temp != null) {
                AssertUtil.isTrue(!(temp.getId()).equals(module.getId()), "该层级下菜单URL已存在！");
            }
        }

        // 权限码 optValue     非空，不可重复（不包含当前修改记录本身）
        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()), "权限码不能为空！");
        // 通过权限码查询资源对象
        QueryWrapper<Module> moduleQueryWrapper2 = new QueryWrapper<Module>();
        moduleQueryWrapper2.eq("opt_value",module.getOptValue());
        moduleQueryWrapper2.eq("is_valid",1);
        temp = moduleMapper.selectOne(moduleQueryWrapper2);
        // 判断是否为空
        if (temp != null) {
            AssertUtil.isTrue(!(temp.getId()).equals(module.getId()),"权限码已存在！");
        }

        /* 2. 设置参数的默认值  */
        // 修改时间 系统当前时间
        module.setUpdateDate(new Date());

        /* 3. 执行更新操作，判断受影响的行数 */
        AssertUtil.isTrue(moduleMapper.updateById(module) < 1, "修改资源失败！");
    }

    /**
     * 添加资源
     * @param module
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addModule(Module module) {
        /* 1. 参数校验  */
        // 层级 grade 非空，0|1|2
        Integer grade = module.getGrade();
        AssertUtil.isTrue(null == grade || !(grade == 0 || grade == 1 || grade == 2),"菜单层级不合法！");

        // 模块名称 moduleName  非空
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()), "模块名称不能为空！");
        // 模块名称 moduleName  同一层级下模块名称唯一
        QueryWrapper<Module> moduleQueryWrapper = new QueryWrapper<Module>();
        moduleQueryWrapper.eq("grade",grade);
        moduleQueryWrapper.eq("module_name",module.getModuleName());
        moduleQueryWrapper.eq("is_valid",1);
        AssertUtil.isTrue(null != moduleMapper.selectOne(moduleQueryWrapper), "该层级下模块名称已存在！");

        // 如果是二级菜单 （grade=1)
        if (grade == 1) {
            // 地址 url   二级菜单（grade=1），且同一层级下不可重复
            QueryWrapper<Module> moduleQueryWrapper1 = new QueryWrapper<Module>();
            moduleQueryWrapper1.eq("grade",grade);
            moduleQueryWrapper1.eq("url",module.getUrl());
            moduleQueryWrapper1.eq("is_valid",1);
            AssertUtil.isTrue(null != moduleMapper.selectOne(moduleQueryWrapper1),"URL不可重复！");
        }

        // 父级菜单 parentId    一级菜单（目录 grade=0）    -1
        if (grade == 0) {
            module.setParentId(-1);
        }
        // 父级菜单 parentId    二级|三级菜单（菜单|按钮 grade=1或2）    非空，父级菜单必须存在
        if (grade != 0) {
            // 非空
            AssertUtil.isTrue(null == module.getParentId(),"父级菜单不能为空！");
            // 父级菜单必须存在 (将父级菜单的ID作为主键，查询资源记录)
            AssertUtil.isTrue(null == moduleMapper.selectById(module.getParentId()), "请指定正确的父级菜单！");
        }

        // 权限码 optValue     非空
        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()),"权限码不能为空！");
        // 权限码 optValue     不可重复
        QueryWrapper<Module> moduleQueryWrapper2 = new QueryWrapper<Module>();
        moduleQueryWrapper2.eq("opt_value",module.getOptValue());
        moduleQueryWrapper2.eq("is_valid",1);
        AssertUtil.isTrue(null != moduleMapper.selectOne(moduleQueryWrapper2),"权限码已存在！");


        /* 2. 设置参数的默认值  */
        // 是否有效 isValid    1
        module.setIsValid(1);
        // 创建时间createDate  系统当前时间
        module.setCreateDate(new Date());
        // 修改时间updateDate  系统当前时间
        module.setUpdateDate(new Date());

        /* 3. 执行添加操作，判断受影响的行数 */
        AssertUtil.isTrue(moduleMapper.insert(module) < 1, "添加资源失败！");
    }


    /**
     * 查询所有的资源列表
     * @param roleId
     * @return
     */
    public List<TreeModel> queryAllModules(Integer roleId) {
        // 查询所有的资源列表
        List<TreeModel> treeModelList = moduleMapper.queryAllModules();
        // 查询指定角色已经授权过的资源列表 (查询角色拥有的资源ID)
        List<Integer> permissionIds = permissionMapper.queryRoleHasModuleIdsByRoleId(roleId);
        // 判断角色是否拥有资源ID
        if (permissionIds != null && permissionIds.size() > 0) {
            // 循环所有的资源列表，判断用户拥有的资源ID中是否有匹配的，如果有，则设置checked属性为true
            treeModelList.forEach(treeModel -> {
                // 判断角色拥有的资源ID中是否有当前遍历的资源ID
                if (permissionIds.contains(treeModel.getId())) {
                    // 如果包含你，则说明角色授权过，设置checked为true
                    treeModel.setChecked(true);
                }
            });
        }
        return treeModelList;
    }

    /**
     * 表格数据
     * @return
     */
    public Map<String, Object> queryModuleList() {
        Map<String, Object> map = new HashMap<>();
        // 查询资源列表
        QueryWrapper<Module> moduleQueryWrapper = new QueryWrapper<>();
        moduleQueryWrapper.eq("is_valid",1);
        List<Module> moduleList = moduleMapper.selectList(moduleQueryWrapper);
        map.put("code",0);
        map.put("msg","");
        map.put("count", moduleList.size());
        map.put("data",moduleList);
        return map;

    }

}
