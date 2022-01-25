package com.pitaya.smart_rest.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pitaya.smart_rest.system.entity.Module;
import com.pitaya.smart_rest.system.model.TreeModel;

import java.util.List;

/**
 * <p>
 * 权限模块 Mapper 接口
 * </p>
 *
 * @author lucien
 * @since 2022-01-22
 */
public interface ModuleMapper extends BaseMapper<Module> {
    // 查询所有的资源列表
    public List<TreeModel> queryAllModules();
}
