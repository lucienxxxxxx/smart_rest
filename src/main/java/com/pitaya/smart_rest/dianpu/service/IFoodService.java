package com.pitaya.smart_rest.dianpu.service;

import com.pitaya.smart_rest.dianpu.entity.Food;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pitaya.smart_rest.dianpu.query.FoodQuery;

import java.util.Map;

/**
 * <p>
 * 食物或菜品 服务类
 * </p>
 *
 * @author lucien
 * @since 2022-02-09
 */
public interface IFoodService extends IService<Food> {

    Map<String, Object> queryList(Integer userId, FoodQuery foodQuery);

    void addOrUpdate(Food food, Integer flag, Integer userId);

    void del(Integer foodId);
}
