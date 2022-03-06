package com.pitaya.smart_rest.guke.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pitaya.smart_rest.dianpu.entity.RfidUser;
import com.pitaya.smart_rest.dianpu.mapper.RfidUserMapper;
import com.pitaya.smart_rest.guke.entity.Member;
import com.pitaya.smart_rest.guke.mapper.OfflineMemberMapper;
import com.pitaya.smart_rest.guke.query.MemberQuery;
import com.pitaya.smart_rest.guke.service.IOfflineMemberService;
import com.pitaya.smart_rest.system.entity.User;
import com.pitaya.smart_rest.system.mapper.UserMapper;
import com.pitaya.smart_rest.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

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
            AssertUtil.isTrue(rfidUserMapper.updateById(rfidUer) != 1, "修改失败");
        }
    }
}
