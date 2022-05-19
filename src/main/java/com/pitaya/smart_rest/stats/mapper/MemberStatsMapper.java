package com.pitaya.smart_rest.stats.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.stats.entity.MemberStats;
import com.pitaya.smart_rest.stats.entity.Money;
import com.pitaya.smart_rest.stats.query.MemberStatsQuery;

/**
 * @ClassName MemberStatsMapper
 * @author: lucine
 * @Description TODO
 * @date 2022/4/14 0:09
 * @Version 1.0版本
 */
public interface MemberStatsMapper extends BaseMapper<MemberStats> {
    IPage<MemberStats> selectModelPage(Page<MemberStats> page, MemberStatsQuery query);
}
