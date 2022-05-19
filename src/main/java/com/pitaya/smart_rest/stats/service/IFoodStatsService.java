package com.pitaya.smart_rest.stats.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pitaya.smart_rest.stats.entity.FoodStats;
import com.pitaya.smart_rest.stats.entity.MealTime;
import com.pitaya.smart_rest.stats.query.FoodStatsQuery;
import com.pitaya.smart_rest.stats.query.MemberStatsQuery;

import java.util.Map;

/**
 * @ClassName IFoodStatsService
 * @author: lucine
 * @Description TODO
 * @date 2022/4/14 21:06
 * @Version 1.0版本
 */
public interface IFoodStatsService extends IService<FoodStats> {
    Map<String, Object> queryList(Integer userId, FoodStatsQuery query);
}
