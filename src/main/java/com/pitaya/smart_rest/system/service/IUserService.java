package com.pitaya.smart_rest.system.service;

import com.pitaya.smart_rest.system.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pitaya.smart_rest.system.model.UserModel;
import com.pitaya.smart_rest.system.query.UserQuery;

import java.util.Map;

/**
 * <p>
 * 工作人员信息表 服务类
 * </p>
 *
 * @author lucien
 * @since 2022-01-22
 */
public interface IUserService extends IService<User> {
    /**
     * 登录
     * @param userName
     * @param userPwd
     * @return
     */
    UserModel userLogin(String userName, String userPwd);

    /**
     * 分页多条件查询
     * @param userQuery
     * @return
     */
    Map<String, Object> selectUsersByParams(UserQuery userQuery);

    void addOrUpdate(User user,Integer flag);

    void del(Integer userId);

    void resetPwd(Integer userId);

    void updatePassWord(Integer userId, String oldPassword, String newPassword, String repeatPassword);
}
