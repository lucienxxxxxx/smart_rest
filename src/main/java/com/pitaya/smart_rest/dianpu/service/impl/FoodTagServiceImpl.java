package com.pitaya.smart_rest.dianpu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.base.BaseQuery;
import com.pitaya.smart_rest.dianpu.entity.Food;
import com.pitaya.smart_rest.dianpu.entity.FoodTag;
import com.pitaya.smart_rest.dianpu.mapper.FoodTagMapper;
import com.pitaya.smart_rest.dianpu.service.IFoodTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pitaya.smart_rest.system.entity.User;
import com.pitaya.smart_rest.system.mapper.UserMapper;
import com.pitaya.smart_rest.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lucien
 * @since 2022-02-14
 */
@Service
public class FoodTagServiceImpl extends ServiceImpl<FoodTagMapper, FoodTag> implements IFoodTagService {
    @Resource
    private FoodTagMapper foodTagMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public Map<String, Object> queryList(Integer userId, BaseQuery baseQuery) {
        User user = userMapper.selectById(userId);
        QueryWrapper<FoodTag> foodTagQueryWrapper = new QueryWrapper<FoodTag>();
        foodTagQueryWrapper.eq("res_id", user.getResId());
        Page<FoodTag> page = new Page<>(baseQuery.getPage(), baseQuery.getLimit());
        IPage<FoodTag> foodTagPage = foodTagMapper.selectPage(page, foodTagQueryWrapper);
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", foodTagPage.getTotal());
        map.put("data", foodTagPage.getRecords());
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addOrUpdate(FoodTag foodTag, Integer flag, Integer userId) {
        User user = userMapper.selectById(userId);
        AssertUtil.isTrue(userId==null||user==null,"该用户不存在");
        //参数校验
        AssertUtil.isTrue(StringUtils.isBlank(foodTag.getTagName()), "标签名称不能为空");
        if (flag == 0) {
            //添加
            foodTag.setResId(user.getResId());
            AssertUtil.isTrue(foodTagMapper.insert(foodTag)!=1,"添加失败");
        }else {
            AssertUtil.isTrue(foodTagMapper.updateById(foodTag)!=1,"修改失败");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void del(Integer tagId) {
        FoodTag foodTag = foodTagMapper.selectById(tagId);
        AssertUtil.isTrue(foodTag == null, "该记录不存在");
        AssertUtil.isTrue(foodTagMapper.deleteById(foodTag) != 1, "删除失败");
    }
}
