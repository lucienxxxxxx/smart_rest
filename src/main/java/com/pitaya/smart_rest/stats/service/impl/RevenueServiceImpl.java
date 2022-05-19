package com.pitaya.smart_rest.stats.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pitaya.smart_rest.dingdan.model.OrderModel;
import com.pitaya.smart_rest.dingdan.query.OrderQuery;
import com.pitaya.smart_rest.stats.entity.Revenue;
import com.pitaya.smart_rest.stats.mapper.RevenueMapper;
import com.pitaya.smart_rest.stats.query.RevenueQuery;
import com.pitaya.smart_rest.stats.service.IRevenueService;
import com.pitaya.smart_rest.system.entity.User;
import com.pitaya.smart_rest.system.mapper.UserMapper;
import com.pitaya.smart_rest.utils.ArithmeticUtils;
import com.pitaya.smart_rest.utils.AssertUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RevenueServiceImpl
 * @author: lucine
 * @Description TODO
 * @date 2022/3/20 21:46
 * @Version 1.0版本
 */
@Service
public class RevenueServiceImpl extends ServiceImpl<RevenueMapper, Revenue> implements IRevenueService {

    @Resource
    private RevenueMapper revenueMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public Map<String, Object> queryList(Integer userId, RevenueQuery query) {
        //处理组织部分
        if (query.getOrgId() != null) {
            //System.out.println("处理之前："+memberQuery.getOrgId());
            String[] org = query.getOrgId().split(",");
            //只要最后的一位
            query.setOrgId(org[org.length - 1]);
            //System.out.println("处理之后："+memberQuery.getOrgId());
        }
        //处理餐厅
        User user = userMapper.selectById(userId);
        AssertUtil.isTrue(userId == null || user == null, "该用户不存在");
        query.setResId(user.getResId());

        Page<Revenue> page = new Page<Revenue>(query.getPage(), query.getLimit());
        IPage<Revenue> iPage = revenueMapper.selectModelPage(page, query);
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
        linkedHashMap.put("code", 0);
        linkedHashMap.put("msg", "");
        linkedHashMap.put("count", iPage.getTotal());
        List<Revenue> list = addParam(iPage.getRecords());
        linkedHashMap.put("data", list);
        return linkedHashMap;
    }

    private List addParam(List<Revenue> records) {
        records.forEach(r -> {
            double orderSum = r.getOrderSum();
            double total = r.getTotal();
            double refundTotal = r.getRefundTotal();

            double income = ArithmeticUtils.subtract(total, refundTotal);
            double avg = ArithmeticUtils.divide(income, orderSum, 2);

            r.setIncome(income);
            r.setAvg(avg);
        });
        return records;
    }
}
