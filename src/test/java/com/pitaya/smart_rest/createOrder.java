package com.pitaya.smart_rest;


import com.pitaya.smart_rest.dingdan.entity.Order;
import com.pitaya.smart_rest.dingdan.entity.OrderDetail;
import com.pitaya.smart_rest.dingdan.mapper.OrderDetailMapper;
import com.pitaya.smart_rest.dingdan.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @ClassName createOrder
 * @author: lucine
 * @Description TODO
 * @date 2022/5/3 11:07
 * @Version 1.0版本
 */
@SpringBootTest
public class createOrder {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;


    @Test
    public void cs() {

    }


    @Test
    public void createOrder() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = df.parse("2022-05-10 20:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //新建订单
        Order order = new Order();
        order.setState(0);
        order.setOrderDate(date);

        //可修改
        order.setMemberId(12375738);
        order.setTuopanId(123);
        order.setDescriptions("晚餐");

        String orderId = String.valueOf(System.currentTimeMillis());
        order.setId(orderId);


        for (int i = 0; i < 8; i++) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setOrderDetailState(0);

            //每隔1分钟取一次菜
            long time = date.getTime();
            time += 60000 * (i + 2);
            orderDetail.setCreateDate(new Date(time));


            //可修改
            orderDetail.setChargeAcc(10f);
            orderDetail.setFoodId(11);//单价3块
            orderDetail.setWeight(5d);

            orderDetailMapper.insert(orderDetail);

        }

        orderMapper.insert(order);
    }

}
