package com.pitaya.smart_rest.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pitaya.smart_rest.system.entity.Module;
import com.pitaya.smart_rest.system.model.TreeModel;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限模块 服务类
 * </p>
 *
 * @author lucien
 * @since 2022-01-22
 */
public interface IModuleService extends IService<Module> {

    List<TreeModel> queryAllModules(Integer roleId);

    Map<String, Object> queryModuleList();

    void addModule(Module module);

    void updateModule(Module module);

    void deleteModule(Integer id);
}
