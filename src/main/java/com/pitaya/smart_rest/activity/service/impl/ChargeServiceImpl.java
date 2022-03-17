package com.pitaya.smart_rest.activity.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pitaya.smart_rest.activity.model.ChargeModel;
import com.pitaya.smart_rest.activity.model.MemberActivityModel;
import com.pitaya.smart_rest.base.BaseQuery;
import com.pitaya.smart_rest.guke.entity.Member;
import com.pitaya.smart_rest.guke.mapper.MemberMapper;
import com.pitaya.smart_rest.activity.service.IChargeService;
import com.pitaya.smart_rest.system.mapper.UserMapper;
import com.pitaya.smart_rest.utils.AssertUtil;
import com.pitaya.smart_rest.utils.CountUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName ChargeServiceImpl
 * @author: lucine
 * @Description TODO
 * @date 2022/3/15 15:35
 * @Version 1.0版本
 */
@Service
public class ChargeServiceImpl extends ServiceImpl<MemberMapper, Member> implements IChargeService {
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public Map<String, Object> queryList(Integer userId, BaseQuery query) {
        Page<ChargeModel> page = new Page<>(query.getPage(), query.getLimit());
        IPage<ChargeModel> iPage = memberMapper.selectModelPage(page, query);
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
        linkedHashMap.put("code", 0);
        linkedHashMap.put("msg", "");
        linkedHashMap.put("count", iPage.getTotal());
        linkedHashMap.put("data", iPage.getRecords());
        return linkedHashMap;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void charge(String ids, Float virtualAcc, Float giftAcc, Float allowanceAcc, Float cashAcc, Float chargeAcc) {
        //获取用户id数组
        String[] members = ids.split(",");
        //对每个用户进行充值
        for (int i = 0; i < members.length; i++) {
            chargeMember(members[i], virtualAcc, giftAcc, allowanceAcc, cashAcc, chargeAcc);
        }

    }


    private void chargeMember(String id, Float virtualAcc, Float giftAcc, Float allowanceAcc, Float cashAcc, Float chargeAcc) {
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("id", id);
        Member member = memberMapper.selectOne(memberQueryWrapper);
        AssertUtil.isTrue(member == null, "该用户不存在");

        virtualAcc = CountUtil.floatAdd(new Float[]{virtualAcc, member.getVirtualAcc()});
        giftAcc = CountUtil.floatAdd(new Float[]{giftAcc, member.getGiftAcc()});
        allowanceAcc = CountUtil.floatAdd(new Float[]{allowanceAcc, member.getAllowanceAcc()});
        cashAcc = CountUtil.floatAdd(new Float[]{cashAcc, member.getCashAcc()});
        chargeAcc = CountUtil.floatAdd(new Float[]{chargeAcc, member.getChargeAcc()});

        member.setVirtualAcc(virtualAcc);
        member.setGiftAcc(giftAcc);
        member.setAllowanceAcc(allowanceAcc);
        member.setCashAcc(cashAcc);
        member.setChargeAcc(chargeAcc);
        memberMapper.updateById(member);

//        System.out.println("充值之后：" + id + "," + virtualAcc + "," + giftAcc + "," + allowanceAcc + "," + cashAcc + "," + chargeAcc);
    }
}
