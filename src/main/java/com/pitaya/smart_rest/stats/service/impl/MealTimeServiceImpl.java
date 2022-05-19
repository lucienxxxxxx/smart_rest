package com.pitaya.smart_rest.stats.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pitaya.smart_rest.stats.entity.MealTime;
import com.pitaya.smart_rest.stats.mapper.MealTimeMapper;
import com.pitaya.smart_rest.stats.query.MealTimeQuery;
import com.pitaya.smart_rest.stats.service.IMealTimeService;
import com.pitaya.smart_rest.system.entity.User;
import com.pitaya.smart_rest.system.mapper.UserMapper;
import com.pitaya.smart_rest.utils.ArithmeticUtils;
import com.pitaya.smart_rest.utils.AssertUtil;
import com.pitaya.smart_rest.utils.CountUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MealTimeServiceImpl
 * @author: lucine
 * @Description TODO
 * @date 2022/3/26 13:37
 * @Version 1.0版本
 */
@Service
public class MealTimeServiceImpl extends ServiceImpl<MealTimeMapper, MealTime> implements IMealTimeService {
    
    @Resource
    private MealTimeMapper mealTimeMapper;
    @Resource
    private UserMapper userMapper;

    /**
     * 表格查询
     * @param userId
     * @param query
     * @return
     */
    @Override
    public Map<String, Object> queryList(Integer userId, MealTimeQuery query) {
        //处理组织部分
        if (query.getOrgId() != null) {
            String[] org = query.getOrgId().split(",");
            //只要最后的一位
            query.setOrgId(org[org.length - 1]);
        }

        //处理餐厅
        User user = userMapper.selectById(userId);
        AssertUtil.isTrue(userId == null || user == null, "该用户不存在");
        query.setResId(user.getResId());
        Page<MealTime> page = new Page<MealTime>(query.getPage(), query.getLimit());
        IPage<MealTime> iPage = mealTimeMapper.selectModelPage(page, query);
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
        linkedHashMap.put("code", 0);
        linkedHashMap.put("msg", "");
        linkedHashMap.put("count", iPage.getTotal());
        List<MealTime> list = addParam(iPage.getRecords());
        linkedHashMap.put("data", list);
        return linkedHashMap;
    }

    /**
     * 计算均价
     * @param records
     * @return
     */
    private List addParam(List<MealTime> records) {
        records.forEach(r -> {
            //计算早餐均价
            double breakfastTotal = r.getBreakfastTotal();
            double breakfastNum = r.getBreakfastNum();
            if (breakfastNum!=0){
                double breakfastAvg = ArithmeticUtils.divide(breakfastTotal, breakfastNum,2);
                r.setBreakfastAvg(breakfastAvg);
            }


            //计算午餐均价
            double lunchTotal = r.getLunchTotal();
            double lunchNum = r.getLunchNum();
            if (lunchNum!=0){
                double lunchAvg = ArithmeticUtils.divide(lunchTotal, lunchNum,2);
                r.setLunchAvg(lunchAvg);
            }


            //计算晚餐均价
            double dinnerTotal = r.getDinnerTotal();
            double dinnerNum = r.getDinnerNum();
            if (dinnerNum!=0){
                double dinnerAvg = ArithmeticUtils.divide(dinnerTotal, dinnerNum,2);
                r.setDinnerAvg(dinnerAvg);
            }

            //计算总价
            Float total = CountUtil.floatAdd(new Float[]{(float) breakfastTotal, (float) lunchTotal, (float) dinnerTotal});
            r.setTotal(Double.valueOf(total));

        });
        return records;
    }
}
