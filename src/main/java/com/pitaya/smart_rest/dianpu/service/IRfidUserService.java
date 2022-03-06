package com.pitaya.smart_rest.dianpu.service;

import com.pitaya.smart_rest.dianpu.entity.RfidUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pitaya.smart_rest.dianpu.query.CardQuery;

import java.util.Map;

/**
 * <p>
 * RFID卡(用户和管理卡) 服务类
 * </p>
 *
 * @author lucien
 * @since 2022-03-01
 */
public interface IRfidUserService extends IService<RfidUser> {

    Map<String, Object> queryList(Integer userId, CardQuery cardQuery);

    void addOrUpdate(RfidUser rfidUser, Integer flag, Integer userId);

    void del(Integer cardId);

    void switchStatus(Integer cardId, Integer state);
}
