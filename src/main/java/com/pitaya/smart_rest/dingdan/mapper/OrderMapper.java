package com.pitaya.smart_rest.dingdan.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.dingdan.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pitaya.smart_rest.dingdan.model.OrderDetailModel;
import com.pitaya.smart_rest.dingdan.model.OrderModel;
import com.pitaya.smart_rest.dingdan.query.OrderQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author lucien
 * @since 2022-01-28
 */
public interface OrderMapper extends BaseMapper<Order> {
//     List<OrderModel> selectOrderModelPage(OrderQuery orderQuery);

     IPage<OrderModel> selectOrderModelPage(Page<OrderModel> page,@Param("orderQuery")OrderQuery orderQuery);
}
