package com.pitaya.smart_rest.dingdan.service;

import com.pitaya.smart_rest.dingdan.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pitaya.smart_rest.dingdan.query.OrderQuery;

import java.util.Map;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author lucien
 * @since 2022-01-28
 */
public interface IOrderService extends IService<Order> {

    Map<String, Object> queryAllOrderByParams(OrderQuery orderQuery);
}
