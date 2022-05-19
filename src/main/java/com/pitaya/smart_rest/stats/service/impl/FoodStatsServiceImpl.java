package com.pitaya.smart_rest.stats.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pitaya.smart_rest.stats.entity.FoodStats;
import com.pitaya.smart_rest.stats.entity.MealTime;
import com.pitaya.smart_rest.stats.entity.MemberStats;
import com.pitaya.smart_rest.stats.mapper.FoodStatsMapper;
import com.pitaya.smart_rest.stats.mapper.MealTimeMapper;
import com.pitaya.smart_rest.stats.query.FoodStatsQuery;
import com.pitaya.smart_rest.stats.query.MemberStatsQuery;
import com.pitaya.smart_rest.stats.service.IFoodStatsService;
import com.pitaya.smart_rest.stats.service.IMealTimeService;
import com.pitaya.smart_rest.system.entity.User;
import com.pitaya.smart_rest.system.mapper.UserMapper;
import com.pitaya.smart_rest.utils.AssertUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName FoodStatsServiceImpl
 * @author: lucine
 * @Description TODO
 * @date 2022/4/14 21:07
 * @Version 1.0版本
 */
@Service
public class FoodStatsServiceImpl extends ServiceImpl<FoodStatsMapper, FoodStats> implements IFoodStatsService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private FoodStatsMapper foodStatsMapper;
    @Override
    public Map<String, Object> queryList(Integer userId, FoodStatsQuery query) {
        //处理组织部分
        if (query.getOrgId() != null) {
            String[] org = query.getOrgId().split(",");
            //只要最后的一位
            query.setOrgId(org[org.length - 1]);
        }
        //处理餐厅
        User user = userMapper.selectById(userId);
        AssertUtil.isTrue(userId == null || user == null, "该用户不存在");
        query.setResId(user.getResId());

        Page<FoodStats> page = new Page<FoodStats>(query.getPage(), query.getLimit());
        IPage<FoodStats> iPage = foodStatsMapper.selectModelPage(page, query);
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
        linkedHashMap.put("code", 0);
        linkedHashMap.put("msg", "");
        linkedHashMap.put("count", iPage.getTotal());
        linkedHashMap.put("data", iPage.getRecords());
        return linkedHashMap;
    }
}
