package com.pitaya.smart_rest.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.dianpu.entity.RfidTuopan;
import com.pitaya.smart_rest.dingdan.model.OrderDetailModel;
import com.pitaya.smart_rest.dingdan.model.OrderModel;
import com.pitaya.smart_rest.system.entity.User;
import com.pitaya.smart_rest.system.entity.UserRole;
import com.pitaya.smart_rest.system.mapper.UserMapper;
import com.pitaya.smart_rest.system.mapper.UserRoleMapper;
import com.pitaya.smart_rest.system.model.UserModel;
import com.pitaya.smart_rest.system.query.UserQuery;
import com.pitaya.smart_rest.system.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pitaya.smart_rest.utils.AssertUtil;
import com.pitaya.smart_rest.utils.Md5Util;
import com.pitaya.smart_rest.utils.PhoneUtil;
import com.pitaya.smart_rest.utils.UserIDBase64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

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
    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 分页多条件查询
     *
     * @param userQuery 查询实体
     * @return
     */
    @Override
    public Map<String, Object> selectUsersByParams(UserQuery userQuery) {
        Page<User> page = new Page<User>(userQuery.getPage(), userQuery.getLimit());
        IPage<User> iPage = userMapper.selectUserModelPage(page,userQuery);
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
        linkedHashMap.put("code", 0);
        linkedHashMap.put("msg", "");
        linkedHashMap.put("count", iPage.getTotal());
        linkedHashMap.put("data", iPage.getRecords());
        return linkedHashMap;
    }


    /**
     * 添加或修改用户
     * @param user
     * @param flag
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addOrUpdate(User user, Integer flag) {

        if (flag == 0) {   //添加用户

            //参数校验
            checkUserParams(user.getUserName(), user.getEmail(), user.getTrueName(), user.getPhone(), null);

            //设置默认值
            user.setCreateDate(new Date());
            user.setUpdateDate((new Date()));
            user.setUserPwd(Md5Util.encode("123456"));
            user.setIsValid(1);

            //添加
            AssertUtil.isTrue(userMapper.insert(user) != 1, "用户添加失败");

        } else if (flag == 1) {   //编辑用户
            //参数校验
            checkUserParams(user.getUserName(), user.getEmail(), user.getTrueName(), user.getPhone(), user.getId());

            //设置默认值
            user.setUpdateDate((new Date()));

            //修改
            AssertUtil.isTrue(userMapper.updateById(user) != 1, "用户修改失败");

        }

        //用户角色关联
        relationUserRole(user.getId(),user.getRoleIds());
    }

    private void relationUserRole(Integer userId, String roleIds) {

        // 通过用户ID查询角色记录
        QueryWrapper<UserRole> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_id",userId);
        Integer count = userRoleMapper.selectCount(userQueryWrapper);
        // 判断角色记录是否存在
        if (count > 0) {
            // 如果角色记录存在，则删除该用户对应的角色记录
            AssertUtil.isTrue(userRoleMapper.delete(userQueryWrapper) != count, "用户角色分配失败！");
        }

        // 判断角色ID是否存在，如果存在，则添加该用户对应的角色记录
        if (StringUtils.isNotBlank(roleIds)) {
            // 将角色ID字符串转换成数组
            String[] roleIdsArray = roleIds.split(",");
            // 遍历数组，得到对应的用户角色对象，并设置到集合中
            for (String roleId : roleIdsArray) {
                UserRole userRole = new UserRole();
                userRole.setRoleId(Integer.parseInt(roleId));
                userRole.setUserId(userId);
                userRole.setCreateDate(new Date());
                userRole.setUpdateDate(new Date());
                AssertUtil.isTrue(userRoleMapper.insert(userRole) != 1, "用户角色分配失败！");
            }


        }
    }

    /**
     * 添加与修改的参数校验
     *
     * @param userName
     * @param email
     * @param trueName
     * @param phone
     * @param userId
     */
    private void checkUserParams(String userName, String email, String trueName, String phone, Integer userId) {
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(trueName), "真实姓名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(phone), "手机号码不能为空");
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone), "手机号码格式不正确");
        //根据名字查询
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("user_name", userName);
        User user = userMapper.selectOne(wrapper);
        AssertUtil.isTrue(user != null && !(user.getId().equals(userId)), "该用户名已经存在");

    }


    /**
     * 删除用户
     * @param userId
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void del(Integer userId) {
        User user = userMapper.selectById(userId);
        AssertUtil.isTrue(userId==null||user==null,"该用户不存在");
        AssertUtil.isTrue(userMapper.deleteById(userId)!=1,"用户删除失败");
    }

    /**
     * 密码重置
     * @param userId
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void resetPwd(Integer userId) {

        User user = userMapper.selectById(userId);
        AssertUtil.isTrue(userId==null||user==null,"该用户不存在");

        user.setUserPwd(Md5Util.encode("123456"));

        AssertUtil.isTrue(userMapper.updateById(user)!=1,"密码重置失败");
    }

    /**
     * 用户登录
     *
     * @param userName
     * @param userPwd
     * @return
     */
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
        //判断用户状态
        AssertUtil.isTrue(user.getState()==1,"该账号已被停用");
        //进行密码判断
        AssertUtil.isTrue(!Md5Util.encode(userPwd).equals(user.getUserPwd()), "密码不正确");
        //更新最新登录时间
        user.setLoginDate(new Date());
        userMapper.updateById(user);
        //新建模型
        UserModel userModel = new UserModel();
        userModel.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        userModel.setUserName(user.getUserName());
        userModel.setTrueName(user.getTrueName());
        return userModel;

    }

    /**
     * 登录参数校验
     *
     * @param userName
     * @param userPwd
     */
    private void checkLoginParams(String userName, String userPwd) {
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd), "密码不能为空");
    }
    /**
     * 修改密码
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePassWord(Integer userId, String oldPwd, String newPwd, String repeatPwd) {
        // 通过用户ID查询用户记录，返回用户对象
        User user = userMapper.selectById(userId);
        // 判断用户记录是否存在
        AssertUtil.isTrue(null == user, "待更新记录不存在！");

        // 参数校验
        checkPasswordParams(user, oldPwd, newPwd, repeatPwd);

        // 设置用户的新密码
        user.setUserPwd(Md5Util.encode(newPwd));

        // 执行更新，判断受影响的行数
        AssertUtil.isTrue(userMapper.updateById(user) < 1, "修改密码失败！");

    }
    /**
     * 修改密码的参数校验
     */
    private void checkPasswordParams(User user, String oldPwd, String newPwd, String repeatPwd) {
        //  判断原始密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(oldPwd), "原始密码不能为空！");
        // 判断原始密码是否正确（查询的用户对象中的用户密码是否原始密码一致）
        AssertUtil.isTrue(!user.getUserPwd().equals(Md5Util.encode(oldPwd)), "原始密码不正确！");

        // 判断新密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(newPwd), "新密码不能为空！");
        // 判断新密码是否与原始密码一致 （不允许新密码与原始密码）
        AssertUtil.isTrue(oldPwd.equals(newPwd),"新密码不能与原始密码相同！");

        // 判断确认密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(repeatPwd),"确认密码不能为空！");
        // 判断确认密码是否与新密码一致
        AssertUtil.isTrue(!newPwd.equals(repeatPwd), "确认密码与新密码不一致！");

    }


    /**
     * 修改状态
     * @param id
     * @param state
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void switchStatus(Integer id, Integer state) {
        User user = userMapper.selectById(id);
        AssertUtil.isTrue(user==null,"该记录不存在");
        user.setState(state);
        AssertUtil.isTrue(userMapper.updateById(user)!=1,"切换状态失败");
    }
}
