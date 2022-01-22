package com.pitaya.smart_rest.system.service.impl;

import com.pitaya.smart_rest.system.entity.Role;
import com.pitaya.smart_rest.system.mapper.RoleMapper;
import com.pitaya.smart_rest.system.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
