package com.pitaya.smart_rest.dingdan.mapper;

import com.pitaya.smart_rest.dingdan.entity.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pitaya.smart_rest.dingdan.model.OrderDetailModel;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 订单明细 Mapper 接口
 * </p>
 *
 * @author lucien
 * @since 2022-01-28
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
    @Select("SELECT od.id,f.NAME as food_name,od.order_id,od.create_date,od.order_detail_state,od.virtual_acc,od.gift_acc,od.allowance_acc,od.cash_acc,od.charge_acc,od.weight,(od.virtual_acc+od.gift_acc+od.allowance_acc+od.cash_acc+od.charge_acc) as total,( r.virtual_acc + r.gift_acc + r.allowance_acc + r.cash_acc + r.charge_acc ) AS refund_total FROM t_order_detail as od left JOIN t_food as f ON od.food_id = f.id left join t_refund as r on r.order_detail_id=od.id where od.order_id=#{orderId}")
    List<OrderDetailModel> queryOrderDetailById(String orderId);
}
