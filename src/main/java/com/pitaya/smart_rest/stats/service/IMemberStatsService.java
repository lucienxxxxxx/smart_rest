package com.pitaya.smart_rest.stats.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pitaya.smart_rest.stats.entity.MealTime;
import com.pitaya.smart_rest.stats.entity.MemberStats;
import com.pitaya.smart_rest.stats.query.MemberStatsQuery;

import java.util.Map;

/**
 * @ClassName IMemberStatsService
 * @author: lucine
 * @Description TODO
 * @date 2022/4/14 0:11
 * @Version 1.0版本
 */
public interface IMemberStatsService extends IService<MemberStats> {
    Map<String, Object> queryList(Integer userId, MemberStatsQuery query);
}
