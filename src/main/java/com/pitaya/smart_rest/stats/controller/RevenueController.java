package com.pitaya.smart_rest.stats.controller;

import com.pitaya.smart_rest.base.BaseController;
import com.pitaya.smart_rest.stats.query.RevenueQuery;
import com.pitaya.smart_rest.stats.service.impl.RevenueServiceImpl;
import com.pitaya.smart_rest.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName ftl
 * @author: lucine
 * @Description 营收统计页面
 * @date 2022/3/6 13:22
 * @Version 1.0版本
 */
@Controller
@RequestMapping("/stats/revenue")
public class RevenueController extends BaseController {

    @Autowired
    private RevenueServiceImpl revenueService;

    @RequestMapping("index")
    public String index(){
        return "stats/revenue";
    }

    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, RevenueQuery query){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        return revenueService.queryList(userId,query);
    }

}
