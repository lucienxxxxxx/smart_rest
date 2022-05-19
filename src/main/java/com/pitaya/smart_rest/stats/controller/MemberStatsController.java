package com.pitaya.smart_rest.stats.controller;

import com.pitaya.smart_rest.base.BaseController;
import com.pitaya.smart_rest.stats.query.MemberStatsQuery;
import com.pitaya.smart_rest.stats.query.MoneyQuery;
import com.pitaya.smart_rest.stats.service.impl.MemberStatsServiceImpl;
import com.pitaya.smart_rest.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName MemberStatsController
 * @author: lucine
 * @Description TODO
 * @date 2022/4/14 0:10
 * @Version 1.0版本
 */
@Controller
@RequestMapping("/stats/memberStats")
public class MemberStatsController extends BaseController {
    @Autowired
    private MemberStatsServiceImpl memberStatsService;

    /**
     * 主页
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "stats/memberStats";
    }

    /**
     * 列表
     * @param request
     * @param query
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, MemberStatsQuery query){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        return memberStatsService.queryList(userId,query);
    }


}
