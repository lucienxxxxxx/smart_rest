package com.pitaya.smart_rest.system.service;

import com.pitaya.smart_rest.system.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pitaya.smart_rest.system.model.UserModel;

/**
 * <p>
 * 工作人员信息表 服务类
 * </p>
 *
 * @author lucien
 * @since 2022-01-22
 */
public interface IUserService extends IService<User> {
    UserModel userLogin(String userName, String userPwd);
}
