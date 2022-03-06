package com.pitaya.smart_rest.system.service.impl;

import com.pitaya.smart_rest.system.entity.Restaurant;
import com.pitaya.smart_rest.system.mapper.RestaurantMapper;
import com.pitaya.smart_rest.system.service.IRestaurantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 餐厅 服务实现类
 * </p>
 *
 * @author lucien
 * @since 2022-02-20
 */
@Service
public class RestaurantServiceImpl extends ServiceImpl<RestaurantMapper, Restaurant> implements IRestaurantService {

}
