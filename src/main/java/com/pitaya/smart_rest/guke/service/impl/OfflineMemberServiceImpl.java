package com.pitaya.smart_rest.guke.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pitaya.smart_rest.activity.service.impl.ChargeServiceImpl;
import com.pitaya.smart_rest.dianpu.entity.RfidUser;
import com.pitaya.smart_rest.dianpu.mapper.RfidUserMapper;
import com.pitaya.smart_rest.guke.entity.Member;
import com.pitaya.smart_rest.guke.mapper.OfflineMemberMapper;
import com.pitaya.smart_rest.guke.query.MemberQuery;
import com.pitaya.smart_rest.guke.service.IOfflineMemberService;
import com.pitaya.smart_rest.system.entity.User;
import com.pitaya.smart_rest.system.mapper.OrgMapper;
import com.pitaya.smart_rest.system.mapper.UserMapper;
import com.pitaya.smart_rest.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName IOfflineMemberServiceImpl
 * @author: lucine
 * @Description TODO
 * @date 2022/3/3 17:20
 * @Version 1.0版本
 */
@Service
public class OfflineMemberServiceImpl extends ServiceImpl<OfflineMemberMapper, Member> implements IOfflineMemberService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private OfflineMemberMapper offlineMemberMapper;
    @Resource
    private RfidUserMapper rfidUserMapper;
    @Resource
    private OrgMapper orgMapper;
    @Autowired
    private ChargeServiceImpl chargeService;

    @Override
    public List<Map<String, Object>> addBatch(JSONArray list,Integer userId) {
        List errorList = new ArrayList<>();
        //参数校验
        for (int i = 0; i < list.size(); i++) {
            JSONObject object = list.getJSONObject(i);
            String logicName = object.getString("logicName");
            String mobile = object.getString("mobile");
            String trueName = object.getString("trueName");
            String orgId = object.getString("orgId");

            if (StringUtils.isBlank(logicName)){
                object.put("errorReason","逻辑卡号不能为空");
                errorList.add(object);
                list.remove(object);
                continue;
            }
            if (StringUtils.isBlank(trueName)){
                object.put("errorReason","真实姓名不能为空");
                errorList.add(object);
                list.remove(object);
                continue;
            }
            QueryWrapper<RfidUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("logic_name",logicName);
            if (rfidUserMapper.selectCount(queryWrapper)>0){
                object.put("errorReason","该逻辑号已经存在");
                errorList.add(object);
                list.remove(object);
                continue;
            }
            if (orgMapper.selectById(Integer.valueOf(orgId))==null){
                object.put("errorReason","该组织不存在");
                errorList.add(object);
                list.remove(object);
                continue;
            }
        }

        //如果有错误对象就返回
        if (!errorList.isEmpty()){
            return errorList;
        }
        //没有错误，开始写入
        for (int i = 0; i < list.size(); i++) {
            JSONObject object = list.getJSONObject(i);
            String logicName = object.getString("logicName");
            String mobile = object.getString("mobile");
            String trueName = object.getString("trueName");
            String orgId = object.getString("orgId");
            //添加
            Member member = new Member();
            member.setTrueName(trueName);
            member.setOrgId(Integer.valueOf(orgId));
            member.setMobile(mobile);
            member.setCreateDate(new Date());
            member.setUpdateDate(new Date());
            member.setIsValid(1);
            member.setState(0);
            member.setMemberType(1);//设置成线下
            User user = userMapper.selectById(userId);
            member.setResId(user.getResId());//设置所属餐厅
            AssertUtil.isTrue(offlineMemberMapper.insert(member) != 1, "添加失败");
            RfidUser rfidUser = new RfidUser();
            rfidUser.setLogicName(logicName);
            rfidUser.setTypeId(1);
            rfidUser.setStateId(1);
            rfidUser.setResId(user.getResId());
            rfidUser.setMemberId(member.getId());
            AssertUtil.isTrue(rfidUserMapper.insert(rfidUser) != 1, "添加失败");
        }

        return null;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void del(Integer id) {
        Member member = offlineMemberMapper.selectById(id);
        AssertUtil.isTrue(member == null, "该记录不存在");
        AssertUtil.isTrue(offlineMemberMapper.deleteById(id) != 1, "删除失败");
    }

    /**
     * 切换状态
     *
     * @param id
     * @param state
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void switchStatus(Integer id, Integer state) {
        Member member = offlineMemberMapper.selectById(id);
        AssertUtil.isTrue(member == null, "该记录不存在");
        member.setState(state);
        AssertUtil.isTrue(offlineMemberMapper.updateById(member) != 1, "切换状态失败");
    }

    /**
     * 分页-多条件查询
     *
     * @param userId
     * @param memberQuery
     * @return
     */
    @Override
    public Map<String, Object> queryList(Integer userId, MemberQuery memberQuery) {
        //处理组织部分
        if (memberQuery.getOrgId()!=null){
            //System.out.println("处理之前："+memberQuery.getOrgId());
            String[] org = memberQuery.getOrgId().split(",");
            //只要最后的一位
            memberQuery.setOrgId(org[org.length-1]);
            //System.out.println("处理之后："+memberQuery.getOrgId());
        }
        //处理餐厅
        User user = userMapper.selectById(userId);
        AssertUtil.isTrue(userId == null || user == null, "该用户不存在");
        memberQuery.setResId(user.getResId());
        Page<Member> page = new Page<>(memberQuery.getPage(), memberQuery.getLimit());
        IPage<Member> iPage = offlineMemberMapper.selectMemberPage(page, memberQuery);
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
        linkedHashMap.put("code", 0);
        linkedHashMap.put("msg", "");
        linkedHashMap.put("count", iPage.getTotal());
        linkedHashMap.put("data", iPage.getRecords());
        return linkedHashMap;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addOrUpdate(Member member, RfidUser rfidUer, Integer flag, Integer userId) {
        User user = userMapper.selectById(userId);
        AssertUtil.isTrue(userId == null || user == null, "该用户不存在");
        //参数校验
        AssertUtil.isTrue(StringUtils.isBlank(member.getTrueName()), "真实姓名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(member.getMobile()), "手机号不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(rfidUer.getLogicName()), "逻辑卡号不能为空");
        AssertUtil.isTrue(member.getOrgId()!=null&&orgMapper.selectById(member.getOrgId())==null, "该组织不存在");

        if (flag == 0) {
            //添加
            member.setCreateDate(new Date());
            member.setUpdateDate(new Date());
            member.setIsValid(1);
            member.setState(0);
            member.setMemberType(1);//设置成线下
            member.setResId(user.getResId());//设置所属餐厅
            AssertUtil.isTrue(offlineMemberMapper.insert(member) != 1, "添加失败");
            rfidUer.setTypeId(1);
            rfidUer.setStateId(1);
            rfidUer.setResId(user.getResId());
            rfidUer.setMemberId(member.getId());
            AssertUtil.isTrue(rfidUserMapper.insert(rfidUer) != 1, "添加失败");
        } else {
            AssertUtil.isTrue(offlineMemberMapper.updateById(member) != 1, "修改失败");
            rfidUer.setId(null);
            UpdateWrapper<RfidUser> rfidUserUpdateWrapper = new UpdateWrapper<RfidUser>();
            rfidUserUpdateWrapper.eq("logic_name",rfidUer.getLogicName());
            AssertUtil.isTrue(rfidUserMapper.update(rfidUer,rfidUserUpdateWrapper) != 1, "修改失败");
        }
    }

    /**
     * 通过excel批量充值
     * @param list
     * @return
     */
    @Override
    public List<Map<String, Object>> chargeByExcel(JSONArray list,Integer userId) {
        List errorList = new ArrayList<>();

        //参数校验
        for (int i = 0; i < list.size(); i++) {
            JSONObject chargeObject = list.getJSONObject(i);
            String trueName = chargeObject.getString("trueName");
            String logicName = chargeObject.getString("logicName");
            String virtualAcc = chargeObject.getString("virtualAcc");
            String giftAcc = chargeObject.getString("giftAcc");
            String allowanceAcc = chargeObject.getString("allowanceAcc");
            String cashAcc = chargeObject.getString("cashAcc");
            String chargeAcc = chargeObject.getString("chargeAcc");

            //手机号码为空
            if (StringUtils.isBlank(logicName)){
                chargeObject.put("errorReason","逻辑卡号不能为空");
                errorList.add(chargeObject);
                list.remove(chargeObject);
                continue;
            }else {

                if (!StringUtils.isNumeric(virtualAcc)){
                    chargeObject.put("errorReason","金额必须为数字");
                    errorList.add(chargeObject);
                    list.remove(chargeObject);
                    continue;
                }
                if (!StringUtils.isNumeric(giftAcc)){
                    chargeObject.put("errorReason","金额必须为数字");
                    errorList.add(chargeObject);
                    list.remove(chargeObject);
                    continue;
                }
                if (!StringUtils.isNumeric(allowanceAcc)){
                    chargeObject.put("errorReason","金额必须为数字");
                    errorList.add(chargeObject);
                    list.remove(chargeObject);
                    continue;
                }
                if (!StringUtils.isNumeric(cashAcc)){
                    chargeObject.put("errorReason","金额必须为数字");
                    errorList.add(chargeObject);
                    list.remove(chargeObject);
                    continue;
                }
                if (!StringUtils.isNumeric(chargeAcc)){
                    chargeObject.put("errorReason","金额必须为数字");
                    errorList.add(chargeObject);
                    list.remove(chargeObject);
                    continue;
                }

            }

            QueryWrapper<RfidUser> qw = new QueryWrapper<>();
            qw.eq("logic_name",logicName);
            RfidUser rfidUser = rfidUserMapper.selectOne(qw);
            if (rfidUser == null) {
                chargeObject.put("errorReason","该用户不存在");
                errorList.add(chargeObject);
                list.remove(chargeObject);
                continue;
            }

        }

       if (!errorList.isEmpty()){
           return errorList;
       }

       //充值
        for (int i = 0; i < list.size(); i++) {
            JSONObject chargeObject = list.getJSONObject(i);
            String logicName = chargeObject.getString("logicName");
            Float virtualAcc = Float.valueOf(chargeObject.getString("virtualAcc"));
            Float giftAcc = Float.valueOf(chargeObject.getString("giftAcc"));
            Float allowanceAcc = Float.valueOf(chargeObject.getString("allowanceAcc"));
            Float cashAcc = Float.valueOf(chargeObject.getString("cashAcc"));
            Float chargeAcc = Float.valueOf(chargeObject.getString("chargeAcc"));
            QueryWrapper<RfidUser> qw = new QueryWrapper<>();
            qw.eq("logic_name",logicName);
            RfidUser rfidUser = rfidUserMapper.selectOne(qw);
            Member member = offlineMemberMapper.selectById(rfidUser.getMemberId());
            AssertUtil.isTrue(member==null,"出现错误，出现不存在用户");
            String chargeId= String.valueOf(member.getId());
            chargeService.chargeSingle(userId,chargeId, virtualAcc, giftAcc, allowanceAcc, cashAcc, chargeAcc);
        }

       return null;
    }
}
