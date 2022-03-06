package com.pitaya.smart_rest.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pitaya.smart_rest.system.entity.Org;
import com.pitaya.smart_rest.system.mapper.OrgMapper;
import com.pitaya.smart_rest.system.query.OrgQuery;
import com.pitaya.smart_rest.system.service.IOrgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pitaya.smart_rest.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 机构会员 可以有上级机构 服务实现类
 * </p>
 *
 * @author lucien
 * @since 2022-01-26
 */
@Service
public class OrgServiceImpl extends ServiceImpl<OrgMapper, Org> implements IOrgService {
    @Resource
    private OrgMapper orgMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void del(Integer orgId) {
        orgMapper.deleteById(orgId);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id", orgId);
        List<Org> orgList = orgMapper.selectList(queryWrapper);

    }




    /**
     * 表格渲染
     *
     * @param orgQuery
     * @return
     */
    @Override
    public Map<String, Object> queryAllOrgByParams(OrgQuery orgQuery) {
        QueryWrapper<Org> orgQueryWrapper = new QueryWrapper<>();
        List<Org> orgs = orgMapper.selectList(orgQueryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", orgs.size());
        map.put("data", orgs);
        return map;
    }

    /**
     * 添加组织
     *
     * @param org
     */
    @Override
    public void add(Org org) {
        //参数校验
        AssertUtil.isTrue(StringUtils.isBlank(org.getOrgName()), "机构名称不能为空");

        //设置默认值
        org.setCreateDate(new Date());
        org.setUpdateDate(new Date());

        //插入记录
        AssertUtil.isTrue(orgMapper.insert(org) != 1, "添加失败");

    }


    /**
     * 更新组织
     *
     * @param org
     */
    @Override
    public void update(Org org) {
        //参数校验
        AssertUtil.isTrue(StringUtils.isBlank(org.getOrgName()), "机构名称不能为空");

        //设置默认值
        org.setUpdateDate(new Date());

        //插入记录
        AssertUtil.isTrue(orgMapper.updateById(org) != 1, "修改失败");
    }
}
