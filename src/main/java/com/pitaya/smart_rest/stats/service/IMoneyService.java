package com.pitaya.smart_rest.stats.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pitaya.smart_rest.stats.entity.MealTime;
import com.pitaya.smart_rest.stats.entity.Money;
import com.pitaya.smart_rest.stats.query.MoneyQuery;

import java.util.Map;

/**
 * @ClassName IMoneyService
 * @author: lucine
 * @Description TODO
 * @date 2022/3/27 21:28
 * @Version 1.0版本
 */
public interface IMoneyService extends IService<Money> {
    Map<String, Object> queryList(Integer userId, MoneyQuery query);
}
