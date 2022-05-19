package com.pitaya.smart_rest.dingdan.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.pitaya.smart_rest.dingdan.entity.Order;
import com.pitaya.smart_rest.dingdan.entity.OrderDetail;
import com.pitaya.smart_rest.dingdan.entity.Refund;
import com.pitaya.smart_rest.dingdan.mapper.OrderDetailMapper;
import com.pitaya.smart_rest.dingdan.mapper.OrderMapper;
import com.pitaya.smart_rest.dingdan.mapper.RefundMapper;
import com.pitaya.smart_rest.dingdan.model.OrderDetailModel;
import com.pitaya.smart_rest.dingdan.query.OrderDetailQuery;
import com.pitaya.smart_rest.dingdan.service.IOrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pitaya.smart_rest.guke.entity.Member;
import com.pitaya.smart_rest.guke.mapper.MemberMapper;
import com.pitaya.smart_rest.utils.AssertUtil;

import com.pitaya.smart_rest.utils.CountUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 订单明细 服务实现类
 * </p>
 *
 * @author lucien
 * @since 2022-01-28
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {
    @Resource
    private OrderDetailMapper orderDetailMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private RefundMapper refundMapper;
    @Resource
    private MemberMapper memberMapper;

    /**
     * 多条件查询-分页
     * @param orderDetailQuery
     * @param orderId
     * @return
     */
    @Override
    public Map<String, Object> queryAllOrderDetailByParams(OrderDetailQuery orderDetailQuery,String orderId) {
        List<OrderDetailModel> list = orderDetailMapper.queryOrderDetailById(orderId);
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
        linkedHashMap.put("code", 0);
        linkedHashMap.put("msg", "");
        linkedHashMap.put("count", list.size());
        linkedHashMap.put("data", list);
        return linkedHashMap;
    }

    /**
     * 子订单退款
     * @param order_detail_id
     * @param refund_money
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void refund(Integer order_detail_id, Float refund_money) {

        //查询子订单
        OrderDetail order_detail = orderDetailMapper.selectById(order_detail_id);
        //参数校验
        AssertUtil.isTrue(order_detail==null,"该子订单id不存在");
        AssertUtil.isTrue(refund_money==null,"退款金额为空");
//        BigDecimal orderMoney = NumberUtil.add(order_detail.getGiftAcc(),order_detail.getAllowanceAcc(),order_detail.getChargeAcc(),order_detail.getVirtualAcc(),order_detail.getCashAcc());
//        AssertUtil.isTrue(Float.compare(refund_money,orderMoney.floatValue())==-1,"退款金额不能大于订单总金额");
       //退钱
        Float refund_charge_acc= Float.valueOf(0),
                refund_cash_acc= Float.valueOf(0),
                refund_allowance_acc= Float.valueOf(0),
                refund_gift_acc= Float.valueOf(0),
                refund_virtual_acc = Float.valueOf(0),
                refund_cashout_limit= Float.valueOf(0);
        Float charge_acc = order_detail.getChargeAcc();
        Float cash_acc = order_detail.getCashAcc();
        Float allowance_acc = order_detail.getAllowanceAcc();
        Float gift_acc = order_detail.getGiftAcc();
        Float virtual_acc = order_detail.getVirtualAcc();
        Float cashout_plus = order_detail.getCashoutPlus();

        if (refund_money>0 && charge_acc>0){
            if (charge_acc >= refund_money){
                refund_charge_acc = refund_money;
                refund_money = Float.valueOf(0);
                //先退款非cashoutlimit部分,再退款非cashoutlimit部分
                if ((charge_acc - refund_charge_acc) > cashout_plus){
                    refund_cashout_limit = Float.valueOf(0);//全部是非cashoutlimit部分
                }else {
                    refund_cashout_limit = (refund_charge_acc + cashout_plus) - charge_acc;
                }
            }else {
                refund_charge_acc = charge_acc;
                refund_money = refund_money - charge_acc;
                refund_cashout_limit = cashout_plus;
            }
        }

        if (refund_money>0 && cash_acc>0){
            if (cash_acc >= refund_money){
                refund_cash_acc = refund_money;
                refund_money = Float.valueOf(0);
            }else {
                refund_cash_acc = cash_acc;
                refund_money = refund_money - cash_acc;
            }
        }

        if (refund_money>0 && allowance_acc>0){
            if (allowance_acc >= refund_money){
                refund_allowance_acc = refund_money;
                refund_money = Float.valueOf(0);
            }else {
                refund_allowance_acc = allowance_acc;
                refund_money = refund_money - allowance_acc;
            }
        }

        if (refund_money>0 && gift_acc>0){
            if (gift_acc >= refund_money){
                refund_gift_acc = refund_money;
                refund_money = Float.valueOf(0);
            }else {
                refund_gift_acc = gift_acc;
                refund_money = refund_money - gift_acc;
            }
        }

        if (refund_money>0 && virtual_acc>0){
            if (virtual_acc >= refund_money){
                refund_virtual_acc = refund_money;
                refund_money = Float.valueOf(0);
            }else {
                refund_virtual_acc = virtual_acc;
                refund_money = refund_money - virtual_acc;
            }
        }

        //order_detail_state设置为相关状态
        order_detail.setOrderDetailState(2);
        AssertUtil.isTrue(orderDetailMapper.updateById(order_detail)!=1,"退款失败");


        //将相关信息写到退款表上去
        Refund refund = new Refund();
        refund.setOrderDetailId(order_detail.getId());

        refund.setCashAcc(order_detail.getCashAcc());
        refund.setAllowanceAcc(order_detail.getAllowanceAcc());
        refund.setChargeAcc(order_detail.getChargeAcc());
        refund.setGiftAcc(order_detail.getGiftAcc());
        refund.setVirtualAcc(order_detail.getVirtualAcc());

        refund.setCreateDate(new Date());
        refund.setIsValid(1);
        refund.setUpdateDate(new Date());
        refund.setState(2);
        //插入退款表
        AssertUtil.isTrue(refundMapper.insert(refund)!=1,"退款表记录失败");


        //退款加到member上去
        String order_id =  order_detail.getOrderId();
        Order order = orderMapper.selectById(order_id);
        Member member = memberMapper.selectById(order.getMemberId());
        UpdateWrapper<Member> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",member.getId())
                .set("virtual_acc", member.getVirtualAcc()+refund_virtual_acc)
                .set("gift_acc", member.getGiftAcc()+refund_gift_acc)
                .set("allowance_acc",member.getAllowanceAcc()+refund_allowance_acc)
                .set("cash_acc",member.getAllowanceAcc()+refund_cash_acc)
                .set("charge_acc",member.getChargeAcc()+refund_charge_acc)
                .set("cashout_limit",member.getCashoutLimit()+refund_cashout_limit);
        AssertUtil.isTrue(memberMapper.update(null, updateWrapper)!=1,"退款失败（用户表修改失败）");

        //修改订单状态
        order.setState(2);
        AssertUtil.isTrue(orderMapper.updateById(order)!=1,"订单状态修改失败");

    }

    /**
     * 整单退款
     * @param orderId
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void allRefund(String orderId) {
        //查询该订单号是否存在
        AssertUtil.isTrue(orderMapper.selectById(orderId)==null,"该订单号不存在");
        //查询该订单所有的子订单
        QueryWrapper<OrderDetail> queryWrapper = new QueryWrapper<OrderDetail>();
        queryWrapper.eq("order_id",orderId);
        List<OrderDetail> orderDetails = orderDetailMapper.selectList(queryWrapper);

        //遍历调用子订单退款
        orderDetails.forEach(od -> {
            Float total = CountUtil.floatAdd(new Float[]{od.getCashAcc(),od.getChargeAcc(),od.getVirtualAcc(),od.getAllowanceAcc(),od.getGiftAcc()});
            //状态为2的就不能退款
            if (od.getOrderDetailState()!=2){
                //退款操作
                this.refund(od.getId(),total);
            }
        });
    }
}
