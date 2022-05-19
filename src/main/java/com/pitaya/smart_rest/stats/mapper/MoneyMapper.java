package com.pitaya.smart_rest.stats.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.stats.entity.Money;
import com.pitaya.smart_rest.stats.query.MoneyQuery;

/**
 * @ClassName MoneyMapper
 * @author: lucine
 * @Description TODO
 * @date 2022/3/27 21:27
 * @Version 1.0版本
 */
public interface MoneyMapper extends BaseMapper<Money> {

    IPage<Money> selectModelPage(Page<Money> page, MoneyQuery query);
}
