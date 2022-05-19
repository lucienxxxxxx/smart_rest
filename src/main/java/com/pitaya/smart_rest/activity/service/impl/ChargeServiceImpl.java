package com.pitaya.smart_rest.activity.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pitaya.smart_rest.activity.entity.Charge;
import com.pitaya.smart_rest.activity.mapper.ChargeMapper;
import com.pitaya.smart_rest.activity.model.ChargeModel;
import com.pitaya.smart_rest.activity.model.MemberActivityModel;
import com.pitaya.smart_rest.activity.query.ChargeQuery;
import com.pitaya.smart_rest.base.BaseQuery;
import com.pitaya.smart_rest.guke.entity.Member;
import com.pitaya.smart_rest.guke.mapper.MemberMapper;
import com.pitaya.smart_rest.activity.service.IChargeService;
import com.pitaya.smart_rest.system.entity.User;
import com.pitaya.smart_rest.system.mapper.UserMapper;
import com.pitaya.smart_rest.utils.AssertUtil;
import com.pitaya.smart_rest.utils.CountUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
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
    @Resource
    private ChargeMapper chargeMapper;
    @Override
    public Map<String, Object> queryList(Integer userId, ChargeQuery query) {
        //处理组织部分
        if (query.getOrgId()!=null){
            //System.out.println("处理之前："+memberQuery.getOrgId());
            String[] org = query.getOrgId().split(",");
            //只要最后的一位
            query.setOrgId(org[org.length-1]);
            //System.out.println("处理之后："+memberQuery.getOrgId());
        }


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
    public void charge(Integer userId,String ids, Float virtualAcc, Float giftAcc, Float allowanceAcc, Float cashAcc, Float chargeAcc) {
        //获取用户id数组
        String[] members = ids.split(",");
        //对每个用户进行充值
        for (int i = 0; i < members.length; i++) {
            chargeMember(userId,members[i], virtualAcc, giftAcc, allowanceAcc, cashAcc, chargeAcc);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void chargeSingle(Integer userId,String id, Float virtualAcc, Float giftAcc, Float allowanceAcc, Float cashAcc, Float chargeAcc) {
        chargeMember(userId,id, virtualAcc, giftAcc, allowanceAcc, cashAcc, chargeAcc);
    }

    private void chargeMember(Integer userId,String id, Float virtualAcc, Float giftAcc, Float allowanceAcc, Float cashAcc, Float chargeAcc) {
        User user = userMapper.selectById(userId);
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("id", id);
        Member member = memberMapper.selectOne(memberQueryWrapper);
        AssertUtil.isTrue(member == null, "该用户不存在");

        Float new_virtualAcc = CountUtil.floatAdd(new Float[]{virtualAcc, member.getVirtualAcc()});
        Float new_giftAcc = CountUtil.floatAdd(new Float[]{giftAcc, member.getGiftAcc()});
        Float new_allowanceAcc = CountUtil.floatAdd(new Float[]{allowanceAcc, member.getAllowanceAcc()});
        Float new_cashAcc = CountUtil.floatAdd(new Float[]{cashAcc, member.getCashAcc()});
        Float new_chargeAcc = CountUtil.floatAdd(new Float[]{chargeAcc, member.getChargeAcc()});

        member.setVirtualAcc(new_virtualAcc);
        member.setGiftAcc(new_giftAcc);
        member.setAllowanceAcc(new_allowanceAcc);
        member.setCashAcc(new_cashAcc);
        member.setChargeAcc(new_chargeAcc);
        AssertUtil.isTrue(memberMapper.updateById(member)!=1,"充值失败");

        //记录操作
        Charge charge = new Charge();
        charge.setChargerId(user.getUserName());
        charge.setMemberId(Integer.parseInt(id));
        charge.setVirtualAcc(virtualAcc);
        charge.setGiftAcc(giftAcc);
        charge.setAllowanceAcc(allowanceAcc);
        charge.setCashAcc(cashAcc);
        charge.setChargeAcc(chargeAcc);
        AssertUtil.isTrue(chargeMapper.insert(charge)!=1,"充值失败");

    }
}
