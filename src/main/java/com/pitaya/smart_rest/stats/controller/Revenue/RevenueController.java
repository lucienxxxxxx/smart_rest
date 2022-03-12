package com.pitaya.smart_rest.stats.controller.Revenue;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName ftl
 * @author: lucine
 * @Description 营收统计页面
 * @date 2022/3/6 13:22
 * @Version 1.0版本
 */
@Controller
@RequestMapping("/stats/revenue")
public class RevenueController {

    @RequestMapping("index")
    public String index(){
        return "stats/revenue";
    }

}
