package com.pitaya.smart_rest.activity.service;

import com.pitaya.smart_rest.activity.entity.ChargeActivity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pitaya.smart_rest.activity.query.ChargeActivityQuery;

import java.util.Map;

/**
 * <p>
 * 充值活动 服务类
 * </p>
 *
 * @author lucien
 * @since 2022-03-13
 */
public interface IChargeActivityService extends IService<ChargeActivity> {

    Map<String, Object> queryList(Integer userId, ChargeActivityQuery query);

    void addOrUpdate(ChargeActivity chargeActivity, Integer flag, Integer userId);

    void del(Integer id);

    void switchStatus(Integer id, Integer state);
}
