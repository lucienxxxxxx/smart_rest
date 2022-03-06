package com.pitaya.smart_rest.dingdan.controller;


import com.pitaya.smart_rest.base.ResultInfo;
import com.pitaya.smart_rest.dingdan.entity.Order;
import com.pitaya.smart_rest.dingdan.query.OrderQuery;
import com.pitaya.smart_rest.dingdan.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.pitaya.smart_rest.base.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author lucien
 * @since 2022-01-28
 */
@Controller
@RequestMapping("/dingdan/order")
public class OrderController extends BaseController {

    @Autowired
    private OrderServiceImpl orderService;

    /**
     * 主页
     *
     * @return
     */
    @GetMapping("index")
    public String index() {
        return "dingdan/order";
    }

    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> list(OrderQuery orderQuery) {
        return orderService.queryAllOrderByParams(orderQuery);
    }

    /**
     * 进入子订单页面
     *
     * @param orderId
     * @return
     */
    @GetMapping("toOrderDetailPage/{orderId}")
    public String toOrderDetailPage(@PathVariable String orderId, Model model) {
        model.addAttribute("orderId",orderId);
        return "dingdan/orderDetail";
    }


}
