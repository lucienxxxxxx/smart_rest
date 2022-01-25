package com.pitaya.smart_rest.system.mapper;

import com.pitaya.smart_rest.system.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author lucien
 * @since 2022-01-22
 */
public interface RoleMapper extends BaseMapper<Role> {
    List<Map<String, Object>> queryAllRoles(Integer userId);
}
