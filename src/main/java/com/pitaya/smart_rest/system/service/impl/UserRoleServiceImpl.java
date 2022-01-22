package com.pitaya.smart_rest.system.service.impl;

import com.pitaya.smart_rest.system.entity.UserRole;
import com.pitaya.smart_rest.system.mapper.UserRoleMapper;
import com.pitaya.smart_rest.system.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author lucien
 * @since 2022-01-22
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
