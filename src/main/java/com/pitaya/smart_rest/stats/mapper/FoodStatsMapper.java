package com.pitaya.smart_rest.stats.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.stats.entity.FoodStats;
import com.pitaya.smart_rest.stats.entity.MemberStats;
import com.pitaya.smart_rest.stats.query.FoodStatsQuery;

/**
 * @ClassName FoodStatsMapper
 * @author: lucine
 * @Description TODO
 * @date 2022/4/14 21:05
 * @Version 1.0版本
 */
public interface FoodStatsMapper extends BaseMapper<FoodStats> {
    IPage<FoodStats> selectModelPage(Page<FoodStats> page, FoodStatsQuery query);
}
