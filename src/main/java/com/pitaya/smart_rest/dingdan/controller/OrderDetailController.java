package com.pitaya.smart_rest.dingdan.controller;


import com.pitaya.smart_rest.base.ResultInfo;
import com.pitaya.smart_rest.dingdan.query.OrderDetailQuery;
import com.pitaya.smart_rest.dingdan.query.OrderQuery;
import com.pitaya.smart_rest.dingdan.service.impl.OrderDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pitaya.smart_rest.base.BaseController;

import java.util.Map;

/**
 * <p>
 * 订单明细 前端控制器
 * </p>
 *
 * @author lucien
 * @since 2022-01-28
 */
@RestController
@RequestMapping("/dingdan/orderDetail")
public class OrderDetailController extends BaseController {
    @Autowired
    private OrderDetailServiceImpl orderDetailService;

    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> list(OrderDetailQuery orderDetailQuery, String orderId) {
        return orderDetailService.queryAllOrderDetailByParams(orderDetailQuery, orderId);
    }

    @PostMapping("refund")
    @ResponseBody
    public ResultInfo refundOrder(@RequestParam Integer id,
                                  @RequestParam Float refundMoney) {
        orderDetailService.refund(id,refundMoney);
        return success("退款成功");
    }

    @PostMapping("allRefund")
    @ResponseBody
    public ResultInfo allRefund(@RequestParam String orderId){
        orderDetailService.allRefund(orderId);
        return success("整单退款成功");
    }
}
