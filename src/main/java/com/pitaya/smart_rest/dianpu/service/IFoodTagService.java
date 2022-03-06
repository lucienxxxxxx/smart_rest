package com.pitaya.smart_rest.dianpu.service;

import com.pitaya.smart_rest.base.BaseQuery;
import com.pitaya.smart_rest.dianpu.entity.FoodTag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lucien
 * @since 2022-02-14
 */
public interface IFoodTagService extends IService<FoodTag> {

    Map<String, Object> queryList(Integer userId, BaseQuery baseQuery);

    void addOrUpdate(FoodTag foodTag, Integer flag, Integer userId);

    void del(Integer tagId);
}
