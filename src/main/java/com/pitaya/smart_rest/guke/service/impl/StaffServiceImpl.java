package com.pitaya.smart_rest.guke.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pitaya.smart_rest.dianpu.query.StaffQuery;
import com.pitaya.smart_rest.guke.entity.Member;
import com.pitaya.smart_rest.guke.mapper.StaffMapper;
import com.pitaya.smart_rest.guke.service.IStaffService;
import com.pitaya.smart_rest.system.entity.User;
import com.pitaya.smart_rest.system.mapper.UserMapper;
import com.pitaya.smart_rest.utils.AssertUtil;
import com.pitaya.smart_rest.utils.PhoneUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName StaffServiceImpl
 * @author: lucine
 * @Description TODO
 * @date 2022/3/5 10:36
 * @Version 1.0版本
 */
@Service
public class StaffServiceImpl  extends ServiceImpl<StaffMapper, Member> implements IStaffService {
    @Resource
    private StaffMapper staffMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void switchStatus(Integer id, Integer state) {
        System.out.println(id+"/"+state);
        Member member = staffMapper.selectById(id);
        AssertUtil.isTrue(member==null,"该记录不存在");
        member.setState(state);
        AssertUtil.isTrue(staffMapper.updateById(member)!=1,"切换状态失败");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void del(Integer id) {
        Member member = staffMapper.selectById(id);
        AssertUtil.isTrue(member==null,"该记录不存在");
        AssertUtil.isTrue(staffMapper.deleteById(id)!=1,"删除失败");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addOrUpdate(Member member, Integer flag, Integer userId) {
        User user = userMapper.selectById(userId);
        AssertUtil.isTrue(userId==null||user==null,"该用户不存在");
        //参数校验
        AssertUtil.isTrue(StringUtils.isBlank(member.getTrueName()), "真实姓名不能为空");
        AssertUtil.isTrue( !PhoneUtil.isMobile(member.getMobile()), "手机号码格式不正确");
        if (flag == 0) {
            //添加
            member.setState(0);
            member.setMemberType(2);
            member.setResId(user.getResId());//设置所属餐厅
            member.setIsValid(1);
            member.setCreateDate(new Date());
            member.setUpdateDate(new Date());
            AssertUtil.isTrue(staffMapper.insert(member)!=1,"添加失败");
        }else {
            AssertUtil.isTrue(staffMapper.updateById(member)!=1,"修改失败");
        }
    }

    @Override
    public Map<String, Object> queryList(Integer userId, StaffQuery staffQuery) {
        User user = userMapper.selectById(userId);
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        AssertUtil.isTrue(userId==null||user==null,"该用户不存在");
        queryWrapper.eq("res_id", user.getResId());//查询该餐厅
        queryWrapper.eq("is_valid", 1);//
        queryWrapper.eq("member_type", 2);
        if (staffQuery.getState() != null) {
            queryWrapper.like("state", staffQuery.getState());
        }
        if (staffQuery.getTrueName() != null && !StringUtils.isBlank(staffQuery.getTrueName())) {
            queryWrapper.like("true_name", staffQuery.getTrueName());
        }
        if (staffQuery.getTrueName() != null && !StringUtils.isBlank(staffQuery.getTrueName())) {
            queryWrapper.like("true_name", staffQuery.getTrueName());
        }
        if (staffQuery.getMobile() != null && !StringUtils.isBlank(staffQuery.getMobile())) {
            queryWrapper.like("mobile", staffQuery.getMobile());
        }
        if (staffQuery.getNote() != null && !StringUtils.isBlank(staffQuery.getNote())) {
            queryWrapper.like("note", staffQuery.getNote());
        }
        Page<Member> page = new Page<>(staffQuery.getPage(), staffQuery.getLimit());
        IPage<Member> iPage = staffMapper.selectPage(page, queryWrapper);
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", iPage.getTotal());
        map.put("data", iPage.getRecords());
        return map;
    }
}
