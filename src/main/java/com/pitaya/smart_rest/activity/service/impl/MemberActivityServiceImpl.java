package com.pitaya.smart_rest.activity.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.activity.entity.MemberActivity;
import com.pitaya.smart_rest.activity.mapper.ChargeActivityMapper;
import com.pitaya.smart_rest.activity.mapper.MemberActivityMapper;
import com.pitaya.smart_rest.activity.model.MemberActivityModel;
import com.pitaya.smart_rest.activity.query.MemberActivityQuery;
import com.pitaya.smart_rest.activity.service.IMemberActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pitaya.smart_rest.dingdan.model.OrderModel;
import com.pitaya.smart_rest.utils.AssertUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * 会员活动表 服务实现类
 * </p>
 *
 * @author lucien
 * @since 2022-03-13
 */
@Service
public class MemberActivityServiceImpl extends ServiceImpl<MemberActivityMapper, MemberActivity> implements IMemberActivityService {

    @Resource
    private MemberActivityMapper memberActivityMapper;
    @Resource
    private ChargeActivityMapper chargeActivityMapper;

    @Override
    public void verify(Integer id) {
        MemberActivity memberActivity = memberActivityMapper.selectById(id);
        AssertUtil.isTrue(memberActivity.getIsUse()==1,"已经核销");
        memberActivity.setIsUse(1);
        memberActivity.setUseDate(new Date());
        AssertUtil.isTrue(memberActivityMapper.updateById(memberActivity)!=1,"核销失败");
    }

    @Override
    public Map<String, Object> queryList(Integer userId, MemberActivityQuery query) {
        Page<MemberActivityModel> page = new Page<>(query.getPage(), query.getLimit());
        IPage<MemberActivityModel> iPage = memberActivityMapper.selectModelPage(page,query);
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
        linkedHashMap.put("code", 0);
        linkedHashMap.put("msg", "");
        linkedHashMap.put("count", iPage.getTotal());
        linkedHashMap.put("data", iPage.getRecords());
        return linkedHashMap;
    }
}
