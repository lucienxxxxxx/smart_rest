package com.pitaya.smart_rest.dianpu.service;

import com.pitaya.smart_rest.dianpu.entity.RfidTuopan;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pitaya.smart_rest.dianpu.query.TuopanQuery;

import java.util.Map;

/**
 * <p>
 * 托盘RFID卡 服务类
 * </p>
 *
 * @author lucien
 * @since 2022-02-12
 */
public interface IRfidTuopanService extends IService<RfidTuopan> {

    Map<String, Object> queryList(Integer userId, TuopanQuery tuopanQuery);

    void addOrUpdate(RfidTuopan rfidTuopan, Integer flag, Integer userId);

    void del(Integer tuopanId);

    void switchStatus(Integer tuopanId,Integer state);
}
