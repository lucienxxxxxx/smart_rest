package com.pitaya.smart_rest.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pitaya.smart_rest.system.entity.User;
import com.pitaya.smart_rest.system.mapper.UserMapper;
import com.pitaya.smart_rest.system.model.UserModel;
import com.pitaya.smart_rest.system.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pitaya.smart_rest.utils.AssertUtil;
import com.pitaya.smart_rest.utils.Md5Util;
import com.pitaya.smart_rest.utils.UserIDBase64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 工作人员信息表 服务实现类
 * </p>
 *
 * @author lucien
 * @since 2022-01-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserModel userLogin(String userName, String userPwd) {
        //参数校验
        checkLoginParams(userName, userPwd);
        //查询用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        User user = userMapper.selectOne(wrapper);
        //判断用户是否存在
        AssertUtil.isTrue(user == null, "该用户不存在");
        //进行密码判断
        AssertUtil.isTrue(!Md5Util.encode(userPwd).equals(user.getUserPwd()), "密码不正确");
        //新建模型
        UserModel userModel = new UserModel();
        userModel.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        userModel.setUserName(user.getUserName());
        userModel.setTrueName(user.getTrueName());

        return userModel;

    }

    /**
     * 登录的参数校验
     * @param userName 登录名
     * @param userPwd   登录密码
     */
    private void checkLoginParams(String userName, String userPwd) {
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd), "密码不能为空");
    }
}
