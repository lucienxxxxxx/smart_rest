package com.pitaya.smart_rest.system.controller;


import com.pitaya.smart_rest.base.ResultInfo;
import com.pitaya.smart_rest.system.entity.User;
import com.pitaya.smart_rest.system.query.UserQuery;
import com.pitaya.smart_rest.system.service.impl.UserServiceImpl;
import com.pitaya.smart_rest.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.pitaya.smart_rest.base.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 工作人员信息表 前端控制器
 * </p>
 *
 * @author lucien
 * @since 2022-01-22
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * 主页
     *
     * @return
     */
    @GetMapping("index")
    public String index() {
        return "system/user";
    }


    /**
     * 分页多条件查询
     *
     * @param userQuery
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> list(UserQuery userQuery) {
        return userService.selectUsersByParams(userQuery);
    }

    /**
     * 更新添加用户
     *
     * @param user 用户实体
     * @param flag 1=修改 0=添加
     * @return
     */
    @PostMapping("addOrUpdate")
    @ResponseBody
    public ResultInfo addOrUpdate(User user, Integer flag) {
        userService.addOrUpdate(user, flag);
        String msg = "用户添加成功";
        if (flag == 1) {
            msg = "用户修改成功";
        }
        return success(msg);
    }

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    @PostMapping("del")
    @ResponseBody
    public ResultInfo del(Integer userId) {
        userService.del(userId);
        return success("用户删除成功");
    }

    /**
     * 重置密码
     *
     * @param userId
     * @return
     */
    @PostMapping("resetPwd")
    @ResponseBody
    public ResultInfo resetPwd(Integer userId) {
        userService.resetPwd(userId);
        return success("密码修改成功");
    }

    /**
     * 进入修改密码的页面
     *
     * @param
     * @return java.lang.String
     */
    @RequestMapping("toPasswordPage")
    public String toPasswordPage() {
        return "system/password";
    }

    @PostMapping("updatePwd")
    @ResponseBody
    public ResultInfo updateUserPassword(HttpServletRequest request,
                                         String oldPassword, String newPassword, String repeatPassword) {
        ResultInfo resultInfo = new ResultInfo();
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        userService.updatePassWord(userId, oldPassword, newPassword, repeatPassword);
        return resultInfo;
    }

    /**
     * 修改状态
     *
     * @param
     * @return
     */
    @PostMapping("switchStatus")
    @ResponseBody
    public ResultInfo switchStatus(Integer id,Integer state) {
        userService.switchStatus(id,state);
        return success("修改状态成功");
    }
}
