package com.pitaya.smart_rest.system.controller;

import com.pitaya.smart_rest.base.BaseController;
import com.pitaya.smart_rest.base.ResultInfo;
import com.pitaya.smart_rest.system.model.UserModel;
import com.pitaya.smart_rest.system.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    /**
     * 登录页面
     * @return
     */
    @GetMapping("login")
    public String login(){
        return "login";
    }

    /**
     * 后台系统主页
     * @return
     */
    @GetMapping("main")
    public String main(){
        return "main";
    }

    /**
     * 登录API
     * @param userName  用户名
     * @param userPwd   密码
     * @return
     */
    @PostMapping("toLogin")
    @ResponseBody
    public ResultInfo userLogin(String userName, String userPwd){
        UserModel userModel = userService.userLogin(userName, userPwd);
        return success("登录成功",userModel);
    }
}
