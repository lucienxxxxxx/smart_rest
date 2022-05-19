package com.pitaya.smart_rest.stats.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pitaya.smart_rest.stats.entity.MealTime;
import com.pitaya.smart_rest.stats.entity.Revenue;
import com.pitaya.smart_rest.stats.query.MealTimeQuery;

import java.util.Map;

/**
 * @ClassName IMealTimeService
 * @author: lucine
 * @Description TODO
 * @date 2022/3/26 13:37
 * @Version 1.0版本
 */
public interface IMealTimeService extends IService<MealTime> {
    Map<String, Object> queryList(Integer userId, MealTimeQuery query);
}
