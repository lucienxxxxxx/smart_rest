package com.pitaya.smart_rest.system.service;

import com.pitaya.smart_rest.dianpu.query.TuopanQuery;
import com.pitaya.smart_rest.system.entity.Restaurant;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pitaya.smart_rest.system.model.ResModel;
import com.pitaya.smart_rest.system.query.RestaurantQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 餐厅 服务类
 * </p>
 *
 * @author lucien
 * @since 2022-02-20
 */
public interface IRestaurantService extends IService<Restaurant> {

    Map<String, Object> queryList(RestaurantQuery query);

    void addOrUpdate(Restaurant restaurant, Integer flag);

    void del(Integer id,Integer userId);

    void switchStatus(Integer id, Integer state,Integer userId);

    List<ResModel> getAllRestaurant();

    void foodMenuMove(Integer resId, Integer toResId, Integer isCover);
}
