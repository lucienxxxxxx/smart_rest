package com.pitaya.smart_rest.activity.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pitaya.smart_rest.base.BaseQuery;
import com.pitaya.smart_rest.guke.entity.Member;

import java.util.Map;

/**
 * @ClassName ChargeService
 * @author: lucine
 * @Description TODO
 * @date 2022/3/15 15:35
 * @Version 1.0版本
 */
public interface IChargeService extends IService<Member> {
    Map<String, Object> queryList(Integer userId, BaseQuery query);


    void charge(String ids, Float virtualAcc, Float giftAcc, Float allowanceAcc, Float cashAcc, Float chargeAcc);
}
