package com.pitaya.smart_rest.dianpu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.dianpu.entity.Food;
import com.pitaya.smart_rest.dianpu.entity.RfidTuopan;
import com.pitaya.smart_rest.dianpu.mapper.FoodMapper;
import com.pitaya.smart_rest.dianpu.model.FoodModel;
import com.pitaya.smart_rest.dianpu.query.FoodQuery;
import com.pitaya.smart_rest.dianpu.service.IFoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pitaya.smart_rest.dingdan.model.OrderModel;
import com.pitaya.smart_rest.system.entity.User;
import com.pitaya.smart_rest.system.mapper.UserMapper;
import com.pitaya.smart_rest.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * 食物或菜品 服务实现类
 * </p>
 *
 * @author lucien
 * @since 2022-02-09
 */
@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements IFoodService {
    @Resource
    private FoodMapper foodMapper;
    @Resource
    private UserMapper userMapper;

    /**
     * 表格
     * @param userId
     * @param foodQuery
     * @return
     */
    @Override
    public Map<String, Object> queryList(Integer userId, FoodQuery foodQuery) {
        User user = userMapper.selectById(userId);
        foodQuery.setRestId(user.getResId());
        Page<FoodModel> page = new Page<FoodModel>(foodQuery.getPage(), foodQuery.getLimit());
        IPage<FoodModel> iPage = foodMapper.selectModelPage(page,foodQuery);
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
        linkedHashMap.put("code", 0);
        linkedHashMap.put("msg", "");
        linkedHashMap.put("count", iPage.getTotal());
        linkedHashMap.put("data", iPage.getRecords());
        return linkedHashMap;
    }

    /**
     * 添加与修改
     * @param food
     * @param flag
     * @param userId
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addOrUpdate(Food food, Integer flag, Integer userId) {
        User user = userMapper.selectById(userId);
        AssertUtil.isTrue(userId==null||user==null,"该用户不存在");
        //参数校验
        AssertUtil.isTrue(StringUtils.isBlank(food.getName()), "名称不能为空");
        AssertUtil.isTrue(food.getPrice()==null, "价格不能为空");
        AssertUtil.isTrue(food.getDangkouId() == null, "档口ID不能为空");
        AssertUtil.isTrue(food.getPriceMethod() == null, "计价方式不能为空");
        if (flag == 0) {
            //添加
            food.setRestId(user.getResId());
            AssertUtil.isTrue(foodMapper.insert(food)!=1,"添加失败");
        }else {
            AssertUtil.isTrue(foodMapper.updateById(food)!=1,"修改失败");
        }
    }

    /**
     * 删除
     * @param foodId
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void del(Integer foodId) {
        Food food = foodMapper.selectById(foodId);
        AssertUtil.isTrue(food==null,"该记录不存在");
        AssertUtil.isTrue(foodMapper.deleteById(foodId)!=1,"删除失败");
    }
}
