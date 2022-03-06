package com.pitaya.smart_rest.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pitaya.smart_rest.system.entity.Module;
import com.pitaya.smart_rest.system.model.ModuleTree;
import com.pitaya.smart_rest.system.model.TreeModel;
import org.apache.ibatis.annotations.Select;

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
    List<TreeModel> queryAllModules();

    @Select("select DISTINCT m.id as id,m.module_style as icon,m.module_name as name,m.url,m.parent_id as pid,m.orders,m.grade  from t_user u \n" +
            "join t_user_role ur on u.id=ur.user_id\n" +
            "join t_role r on ur.role_id=r.id\n" +
            "join t_permission p on p.role_id=r.id\n" +
            "join t_module m on m.id=p.module_id\n" +
            "where u.id=#{userId}")
    List<ModuleTree> queryModules(Integer userId);
}
