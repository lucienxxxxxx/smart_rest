package com.pitaya.smart_rest.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.dianpu.entity.Food;
import com.pitaya.smart_rest.dianpu.entity.RfidTuopan;
import com.pitaya.smart_rest.dianpu.entity.RfidUser;
import com.pitaya.smart_rest.dianpu.mapper.FoodMapper;
import com.pitaya.smart_rest.dianpu.query.TuopanQuery;
import com.pitaya.smart_rest.system.entity.Restaurant;
import com.pitaya.smart_rest.system.entity.User;
import com.pitaya.smart_rest.system.mapper.RestaurantMapper;
import com.pitaya.smart_rest.system.model.ResModel;
import com.pitaya.smart_rest.system.query.RestaurantQuery;
import com.pitaya.smart_rest.system.service.IRestaurantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pitaya.smart_rest.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    private RestaurantMapper restaurantMapper;
    @Resource
    private FoodMapper foodMapper;

    @Override
    public void foodMenuMove(Integer resId, Integer toResId, Integer isCover) {
        //是否选择覆盖
        if (isCover==0){
            //确定覆盖,删除被迁移餐厅所有菜品
            QueryWrapper<Food> fqw = new QueryWrapper<>();
            fqw.eq("rest_id",toResId);
            foodMapper.delete(fqw);
        }
        //迁移
        QueryWrapper<Food> fqwr = new QueryWrapper<>();
        fqwr.eq("rest_id",resId);
        List<Food> foods = foodMapper.selectList(fqwr);
        foods.forEach(food -> {
            food.setId(null);
            food.setRestId(toResId);
            foodMapper.insert(food);
        });
    }

    @Override
    public List<ResModel> getAllRestaurant() {
        return restaurantMapper.selectAllRes();
    }

    @Override
    public Map<String, Object> queryList(RestaurantQuery query) {
        QueryWrapper<Restaurant> rfidTuopanQueryWrapper = new QueryWrapper<Restaurant>();
        if (query.getState() != null) {
            rfidTuopanQueryWrapper.like("state", query.getState());
        }
        if (query.getRestaurantName() != null && !StringUtils.isBlank(query.getRestaurantName())) {
            rfidTuopanQueryWrapper.like("restaurant_name", query.getRestaurantName());
        }
        Page<Restaurant> page = new Page<Restaurant>(query.getPage(), query.getLimit());
        IPage<Restaurant> iPage = restaurantMapper.selectPage(page, rfidTuopanQueryWrapper);
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", iPage.getTotal());
        map.put("data", iPage.getRecords());
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addOrUpdate(Restaurant restaurant, Integer flag) {
        //参数校验
        AssertUtil.isTrue(StringUtils.isBlank(restaurant.getRestaurantName()), "餐厅名称不能为空");
        if (flag == 0) {
            //添加
            restaurant.setState(0);
            restaurant.setTpUpdate(0);
            restaurant.setCreateDate(new Date());
            restaurant.setUpdateDate(new Date());
            AssertUtil.isTrue(restaurantMapper.insert(restaurant)!=1,"添加失败");
        }else {
            AssertUtil.isTrue(restaurantMapper.updateById(restaurant)!=1,"修改失败");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void del(Integer id,Integer userId) {
        Restaurant restaurant = restaurantMapper.selectById(id);
        AssertUtil.isTrue(restaurant==null,"该记录不存在");
        AssertUtil.isTrue(restaurantMapper.deleteById(id)!=1,"删除失败");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void switchStatus(Integer id, Integer state,Integer userId) {
        Restaurant restaurant = restaurantMapper.selectById(id);
        AssertUtil.isTrue(restaurant==null,"该记录不存在");
        restaurant.setState(state);
        AssertUtil.isTrue(restaurantMapper.updateById(restaurant)!=1,"切换状态失败");
    }
}
