package com.pitaya.smart_rest.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.system.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pitaya.smart_rest.system.query.UserQuery;

/**
 * <p>
 * 工作人员信息表 Mapper 接口
 * </p>
 *
 * @author lucien
 * @since 2022-01-22
 */
public interface UserMapper extends BaseMapper<User> {

    IPage<User> selectUserModelPage(Page<User> page, UserQuery userQuery);
}
