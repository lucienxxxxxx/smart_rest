package com.pitaya.smart_rest.stats.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.guke.entity.Member;
import com.pitaya.smart_rest.stats.entity.Revenue;
import com.pitaya.smart_rest.stats.query.RevenueQuery;

/**
 * @ClassName RevenueMapper
 * @author: lucine
 * @Description TODO
 * @date 2022/3/20 21:42
 * @Version 1.0版本
 */
public interface RevenueMapper extends BaseMapper<Revenue> {
    IPage<Revenue> selectModelPage(Page<Revenue> page, RevenueQuery query);
}
