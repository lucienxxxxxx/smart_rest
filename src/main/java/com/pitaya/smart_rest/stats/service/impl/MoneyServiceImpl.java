package com.pitaya.smart_rest.stats.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pitaya.smart_rest.stats.entity.Money;
import com.pitaya.smart_rest.stats.entity.Money;
import com.pitaya.smart_rest.stats.mapper.MoneyMapper;
import com.pitaya.smart_rest.stats.mapper.MoneyMapper;
import com.pitaya.smart_rest.stats.query.MoneyQuery;
import com.pitaya.smart_rest.stats.service.IMoneyService;
import com.pitaya.smart_rest.stats.service.IMoneyService;
import com.pitaya.smart_rest.system.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MoneyServiceImpl
 * @author: lucine
 * @Description TODO
 * @date 2022/3/27 21:29
 * @Version 1.0版本
 */
@Service
public class MoneyServiceImpl extends ServiceImpl<MoneyMapper, Money> implements IMoneyService {

    @Resource
    private MoneyMapper moneyMapper;
    @Resource
    private UserMapper userMapper;
    
    @Override
    public Map<String, Object> queryList(Integer userId, MoneyQuery query) {
        query.setResId(userMapper.selectById(userId).getResId());
        Page<Money> page = new Page<Money>(query.getPage(), query.getLimit());
        IPage<Money> iPage = moneyMapper.selectModelPage(page, query);
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
        linkedHashMap.put("code", 0);
        linkedHashMap.put("msg", "");
        linkedHashMap.put("count", iPage.getTotal());
        linkedHashMap.put("data", iPage.getRecords());
        return linkedHashMap;
    }
}
