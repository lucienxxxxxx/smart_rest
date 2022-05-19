package com.pitaya.smart_rest.stats.controller;

import com.pitaya.smart_rest.base.BaseController;
import com.pitaya.smart_rest.stats.query.MealTimeQuery;
import com.pitaya.smart_rest.stats.query.RevenueQuery;
import com.pitaya.smart_rest.stats.service.impl.MealTimeServiceImpl;
import com.pitaya.smart_rest.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName MealTimeController
 * @author: lucine
 * @Description TODO
 * @date 2022/3/26 13:36
 * @Version 1.0版本
 */
@Controller
@RequestMapping("/stats/mealTime")
public class MealTimeController extends BaseController {

    @Autowired
    private MealTimeServiceImpl mealTimeService;

    /**
     * 主页
     * @return
     */
    @GetMapping("index")
    public String index(){
        return "stats/mealTime";
    }

    /**
     * 表格查询
     * @param request
     * @param query
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, MealTimeQuery query){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        return mealTimeService.queryList(userId,query);
    }

}
