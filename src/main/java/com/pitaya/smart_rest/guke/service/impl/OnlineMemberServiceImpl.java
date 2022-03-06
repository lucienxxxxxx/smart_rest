package com.pitaya.smart_rest.guke.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pitaya.smart_rest.guke.entity.Member;
import com.pitaya.smart_rest.guke.mapper.OnlineMemberMapper;
import com.pitaya.smart_rest.guke.query.MemberQuery;
import com.pitaya.smart_rest.guke.service.IOnlineMemberService;
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
 * @ClassName IOnlineMemberServiceImp
 * @author: lucine
 * @Description TODO
 * @date 2022/3/3 17:17
 * @Version 1.0版本
 */
@Service
public class OnlineMemberServiceImpl extends ServiceImpl<OnlineMemberMapper, Member> implements IOnlineMemberService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private OnlineMemberMapper onlineMemberMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addOrUpdate(Member member, Integer flag, Integer userId) {
        User user = userMapper.selectById(userId);
        AssertUtil.isTrue(userId==null||user==null,"该用户不存在");
        //参数校验
        AssertUtil.isTrue(StringUtils.isBlank(member.getTrueName()), "真实姓名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(member.getMobile()), "手机号不能为空");

        if (flag == 0) {
            //添加
            member.setState(1);
            member.setMemberType(0);//设置成线上
            member.setResId(user.getResId());//设置所属餐厅
            AssertUtil.isTrue(onlineMemberMapper.insert(member)!=1,"添加失败");
        }else {
            AssertUtil.isTrue(onlineMemberMapper.updateById(member)!=1,"修改失败");
        }
    }

    /**
     * 条件查询-分页列表
     * @param userId
     * @param memberQuery
     * @return
     */
    @Override
    public Map<String, Object> queryList(Integer userId, MemberQuery memberQuery) {
        User user = userMapper.selectById(userId);
        AssertUtil.isTrue(userId==null||user==null,"该用户不存在");
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("res_id", user.getResId());//查询该餐厅
        queryWrapper.eq("member_type", 0);//线上会员
        if (memberQuery.getState() != null) {
            queryWrapper.like("state", memberQuery.getState());
        }
        if (memberQuery.getId() != null) {
            queryWrapper.like("id", memberQuery.getId());
        }
        if (memberQuery.getTrueName() != null && !StringUtils.isBlank(memberQuery.getTrueName())) {
            queryWrapper.like("true_name", memberQuery.getTrueName());
        }
        if (memberQuery.getMobile() != null && !StringUtils.isBlank(memberQuery.getMobile())) {
            queryWrapper.like("mobile", memberQuery.getMobile());
        }
        if (memberQuery.getNote() != null && !StringUtils.isBlank(memberQuery.getNote())) {
            queryWrapper.like("note", memberQuery.getNote());
        }

        Page<Member> page = new Page<>(memberQuery.getPage(), memberQuery.getLimit());
        IPage<Member> iPage = onlineMemberMapper.selectPage(page, queryWrapper);
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", iPage.getTotal());
        map.put("data", iPage.getRecords());
        return map;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void del(Integer id) {
        Member member = onlineMemberMapper.selectById(id);
        AssertUtil.isTrue(member==null,"该记录不存在");
        AssertUtil.isTrue(onlineMemberMapper.deleteById(id)!=1,"删除失败");
    }

    /**
     * 切换状态
     * @param id
     * @param state
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void switchStatus(Integer id, Integer state) {
        Member member = onlineMemberMapper.selectById(id);
        AssertUtil.isTrue(member==null,"该记录不存在");
        member.setState(state);
        AssertUtil.isTrue(onlineMemberMapper.updateById(member)!=1,"切换状态失败");
    }
}
