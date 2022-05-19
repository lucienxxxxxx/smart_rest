package com.pitaya.smart_rest.stats.controller;

import com.pitaya.smart_rest.base.BaseController;
import com.pitaya.smart_rest.stats.query.MealTimeQuery;
import com.pitaya.smart_rest.stats.query.MoneyQuery;
import com.pitaya.smart_rest.stats.service.impl.MoneyServiceImpl;
import com.pitaya.smart_rest.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName MoneyController
 * @author: lucine
 * @Description TODO
 * @date 2022/3/27 21:25
 * @Version 1.0版本
 */
@Controller
@RequestMapping("/stats/money")
public class MoneyController extends BaseController {

    @Autowired
    private MoneyServiceImpl moneyService;

    /**
     * 主页
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "stats/money";
    }

    /**
     * 列表
     * @param request
     * @param query
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, MoneyQuery query){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        return moneyService.queryList(userId,query);
    }

}
