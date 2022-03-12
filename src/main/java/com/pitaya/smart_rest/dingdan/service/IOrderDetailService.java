package com.pitaya.smart_rest.dingdan.service;

import com.pitaya.smart_rest.dingdan.entity.OrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pitaya.smart_rest.dingdan.query.OrderDetailQuery;

import java.util.Map;

/**
 * <p>
 * 订单明细 服务类
 * </p>
 *
 * @author lucien
 * @since 2022-01-28
 */
public interface IOrderDetailService extends IService<OrderDetail> {

    Map<String, Object> queryAllOrderDetailByParams(OrderDetailQuery orderDetailQuery,String orderId);

    void refund(Integer id, Float refundMoney);

    void allRefund(String orderId);
}
