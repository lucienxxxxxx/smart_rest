package com.pitaya.smart_rest.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.activity.entity.ChargeActivity;
import com.pitaya.smart_rest.activity.mapper.ChargeActivityMapper;
import com.pitaya.smart_rest.activity.model.ChargeActivityModel;
import com.pitaya.smart_rest.activity.query.ChargeActivityQuery;
import com.pitaya.smart_rest.activity.service.IChargeActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pitaya.smart_rest.dianpu.entity.RfidUser;
import com.pitaya.smart_rest.dingdan.model.OrderModel;
import com.pitaya.smart_rest.system.entity.User;
import com.pitaya.smart_rest.system.mapper.UserMapper;
import com.pitaya.smart_rest.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * 充值活动 服务实现类
 * </p>
 *
 * @author lucien
 * @since 2022-03-13
 */
@Service
public class ChargeActivityServiceImpl extends ServiceImpl<ChargeActivityMapper, ChargeActivity> implements IChargeActivityService {
    @Resource
    private ChargeActivityMapper chargeActivityMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public Map<String, Object> queryList(Integer userId, ChargeActivityQuery query) {
        User user = userMapper.selectById(userId);
        query.setResId(user.getResId());
        Page<ChargeActivityModel> page = new Page<>(query.getPage(), query.getLimit());
        IPage<ChargeActivityModel> iPage = chargeActivityMapper.selectOrderModelPage(page,query);
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
        linkedHashMap.put("code", 0);
        linkedHashMap.put("msg", "");
        linkedHashMap.put("count", iPage.getTotal());
        linkedHashMap.put("data", iPage.getRecords());
        return linkedHashMap;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addOrUpdate(ChargeActivity chargeActivity, Integer flag, Integer userId) {
        //对时间范围进行切割
        String dateRange = chargeActivity.getDateRange();
        String[] dateSplit = dateRange.split("~");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = simpleDateFormat.parse(dateSplit[0]);
            Date endDate = simpleDateFormat.parse(dateSplit[1]);
            chargeActivity.setStartDate(startDate);
            chargeActivity.setEndDate(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        User user = userMapper.selectById(userId);
        AssertUtil.isTrue(userId==null||user==null,"该用户不存在");

        //参数校验
        AssertUtil.isTrue(chargeActivity.getActivityType()==null, "活动类型不能为空");

        if (flag == 0) {
            //添加
            chargeActivity.setState(0);
            chargeActivity.setResId(user.getResId());//设置所属餐厅
            chargeActivity.setCreateDate(new Date());
            AssertUtil.isTrue(chargeActivityMapper.insert(chargeActivity)!=1,"添加失败");
        }else {
            AssertUtil.isTrue(chargeActivityMapper.updateById(chargeActivity)!=1,"修改失败");
        }

    }

    /**
     * 删除
     * @param id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void del(Integer id) {
        ChargeActivity chargeActivity = chargeActivityMapper.selectById(id);
        AssertUtil.isTrue(chargeActivity==null,"该记录不存在");
        AssertUtil.isTrue(chargeActivityMapper.deleteById(id)!=1,"删除失败");
    }

    /**
     * 切换状态
     * @param id
     * @param state
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void switchStatus(Integer id, Integer state) {
        ChargeActivity chargeActivity = chargeActivityMapper.selectById(id);
        AssertUtil.isTrue(chargeActivity==null,"该记录不存在");
        chargeActivity.setState(state);
        AssertUtil.isTrue(chargeActivityMapper.updateById(chargeActivity)!=1,"切换状态失败");
    }
}
