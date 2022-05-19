package com.pitaya.smart_rest.dianpu.service;

import com.pitaya.smart_rest.dianpu.entity.Dangkou;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pitaya.smart_rest.dianpu.query.DangkuoQuery;

import java.util.Map;

/**
 * <p>
 * 档口 服务类
 * </p>
 *
 * @author lucien
 * @since 2022-03-29
 */
public interface IDangkouService extends IService<Dangkou> {

    Map<String, Object> queryList(Integer userId, DangkuoQuery query);

    void switchStatus(Integer id, Integer state);

    void del(Integer id);

    void addOrUpdate(Dangkou dangkou, Integer flag, Integer userId);
}
