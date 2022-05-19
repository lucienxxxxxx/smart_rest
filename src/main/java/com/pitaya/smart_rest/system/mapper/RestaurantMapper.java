package com.pitaya.smart_rest.system.mapper;

import com.pitaya.smart_rest.system.entity.Restaurant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pitaya.smart_rest.system.model.ResModel;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 餐厅 Mapper 接口
 * </p>
 *
 * @author lucien
 * @since 2022-02-20
 */
public interface RestaurantMapper extends BaseMapper<Restaurant> {

    @Select("select restaurant_name as name,id as value from t_restaurant")
    List<ResModel> selectAllRes();
}
