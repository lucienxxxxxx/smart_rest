package com.pitaya.smart_rest.stats.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pitaya.smart_rest.guke.entity.Member;
import com.pitaya.smart_rest.stats.entity.Revenue;
import com.pitaya.smart_rest.stats.query.RevenueQuery;

import java.util.Map;

/**
 * @ClassName RevenueService
 * @author: lucine
 * @Description TODO
 * @date 2022/3/20 21:46
 * @Version 1.0版本
 */
public interface IRevenueService extends IService<Revenue> {
    Map<String, Object> queryList(Integer userId, RevenueQuery query);
}
