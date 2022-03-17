package com.pitaya.smart_rest.activity.controller;


import com.pitaya.smart_rest.activity.query.ChargeActivityQuery;
import com.pitaya.smart_rest.activity.query.MemberActivityQuery;
import com.pitaya.smart_rest.activity.service.impl.MemberActivityServiceImpl;
import com.pitaya.smart_rest.base.ResultInfo;
import com.pitaya.smart_rest.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.pitaya.smart_rest.base.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 会员活动表 前端控制器
 * </p>
 *
 * @author lucien
 * @since 2022-03-13
 */
@Controller
@RequestMapping("/activity/memberActivity")
public class MemberActivityController extends BaseController {
    @Autowired
    private MemberActivityServiceImpl memberActivityService;

    @RequestMapping("index")
    public String index(){
        return "activity/memberActivity";
    }

    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, MemberActivityQuery query){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        return memberActivityService.queryList(userId,query);
    }

    @PostMapping("verify")
    @ResponseBody
    public ResultInfo verify(Integer id){
        System.out.println(id);
        memberActivityService.verify(id);
        return success();
    }
}
