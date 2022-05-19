package com.pitaya.smart_rest.dingdan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.dingdan.entity.Order;
import com.pitaya.smart_rest.dingdan.mapper.OrderMapper;
import com.pitaya.smart_rest.dingdan.model.OrderModel;
import com.pitaya.smart_rest.dingdan.query.OrderQuery;
import com.pitaya.smart_rest.dingdan.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pitaya.smart_rest.system.entity.Role;
import com.pitaya.smart_rest.system.entity.User;
import com.pitaya.smart_rest.system.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author lucien
 * @since 2022-01-28
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public Map<String, Object> queryAllOrderByParams(Integer userId,OrderQuery orderQuery) {
        User user = userMapper.selectById(userId);
        orderQuery.setResId(user.getResId());
        Page<OrderModel> page = new Page<OrderModel>(orderQuery.getPage(), orderQuery.getLimit());
        IPage<OrderModel> iPage = orderMapper.selectOrderModelPage(page,orderQuery);
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
        linkedHashMap.put("code", 0);
        linkedHashMap.put("msg", "");
        linkedHashMap.put("count", iPage.getTotal());
        linkedHashMap.put("data", iPage.getRecords());
        return linkedHashMap;
    }
}
