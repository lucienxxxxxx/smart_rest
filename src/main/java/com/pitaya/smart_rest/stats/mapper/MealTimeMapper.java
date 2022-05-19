package com.pitaya.smart_rest.stats.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.stats.entity.MealTime;
import com.pitaya.smart_rest.stats.query.MealTimeQuery;

/**
 * @ClassName MealTimeMapper
 * @author: lucine
 * @Description TODO
 * @date 2022/3/26 13:38
 * @Version 1.0版本
 */
public interface MealTimeMapper extends BaseMapper<MealTime> {
    IPage<MealTime> selectModelPage(Page<MealTime> page, MealTimeQuery query);
}
