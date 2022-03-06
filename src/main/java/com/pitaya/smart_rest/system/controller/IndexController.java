package com.pitaya.smart_rest.system.controller;

import com.pitaya.smart_rest.base.BaseController;
import com.pitaya.smart_rest.base.ResultInfo;
import com.pitaya.smart_rest.system.entity.User;
import com.pitaya.smart_rest.system.model.ModuleTree;
import com.pitaya.smart_rest.system.model.UserModel;
import com.pitaya.smart_rest.system.service.impl.ModuleServiceImpl;
import com.pitaya.smart_rest.system.service.impl.PermissionServiceImpl;
import com.pitaya.smart_rest.system.service.impl.UserServiceImpl;
import com.pitaya.smart_rest.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @ClassName IndexController
 * @author: lucine
 * @Description TODO
 * @date 2022/1/22 0:00
 * @Version 1.0版本
 */
@Controller
public class IndexController extends BaseController {
    @Autowired
    private UserServiceImpl userService;
    @Resource
    private PermissionServiceImpl permissionService;
    @Resource
    private ModuleServiceImpl moduleService;

    @GetMapping("welcome")
    public String text() {
        return "welcome";
    }

    /**
     * 登录页面
     *
     * @return
     */
    @GetMapping("login")
    public String login() {
        return "login";
    }

    /**
     * 后台系统主页
     *
     * @return
     */
    @GetMapping({"/", "main"})
    public String main(HttpServletRequest request) {
        // 获取cookie中的用户Id
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        // 查询用户对象，设置session作用域
        User user = userService.getById(userId);
        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("userId", user.getId());
        List<ModuleTree> maps = permissionService.queryModelPermissions(userId);
        System.out.println(maps);
        request.getSession().setAttribute("menus", maps);
        // 通过当前登录用户ID查询当前登录用户拥有的资源列表 （查询对应资源的授权码）
        List<String> permissions = permissionService.queryUserHasRoleHasPermissionByUserId(userId);
        // 将集合设置到session作用域中
        request.getSession().setAttribute("permissions", permissions);

        return "main2";
    }

    /**
     * 登录API
     *
     * @param userName 用户名
     * @param userPwd  密码
     * @return
     */
    @PostMapping("toLogin")
    @ResponseBody
    public ResultInfo userLogin(@RequestParam String userName,
                                @RequestParam String userPwd) {
        UserModel userModel = userService.userLogin(userName, userPwd);
        return success("登录成功", userModel);
    }
}
