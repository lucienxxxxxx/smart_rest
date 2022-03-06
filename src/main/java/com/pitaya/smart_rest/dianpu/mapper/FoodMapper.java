package com.pitaya.smart_rest.dianpu.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.dianpu.entity.Food;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pitaya.smart_rest.dianpu.model.FoodModel;
import com.pitaya.smart_rest.dianpu.query.FoodQuery;
import com.pitaya.smart_rest.dingdan.model.OrderModel;

/**
 * <p>
 * 食物或菜品 Mapper 接口
 * </p>
 *
 * @author lucien
 * @since 2022-02-09
 */
public interface FoodMapper extends BaseMapper<Food> {

    IPage<FoodModel> selectModelPage(Page<FoodModel> page, FoodQuery foodQuery);
}
