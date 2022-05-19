package com.pitaya.smart_rest.dianpu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.dianpu.entity.Dangkou;
import com.pitaya.smart_rest.dianpu.entity.RfidTuopan;
import com.pitaya.smart_rest.dianpu.mapper.DangkouMapper;
import com.pitaya.smart_rest.dianpu.query.DangkuoQuery;
import com.pitaya.smart_rest.dianpu.service.IDangkouService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * <p>
 * 档口 服务实现类
 * </p>
 *
 * @author lucien
 * @since 2022-03-29
 */
@Service
public class DangkouServiceImpl extends ServiceImpl<DangkouMapper, Dangkou> implements IDangkouService {

    @Resource
    private DangkouMapper dangkouMapper;
    @Resource
    private UserMapper userMapper;


    /**
     * 表格渲染
     * @param userId
     * @param query
     * @return
     */
    @Override
    public Map<String, Object> queryList(Integer userId, DangkuoQuery query) {
        User user = userMapper.selectById(userId);
        QueryWrapper<Dangkou> dangkouQueryWrapper = new QueryWrapper<>();
        AssertUtil.isTrue(userId==null||user==null,"该用户不存在");
        dangkouQueryWrapper.eq("res_id", user.getResId());
        if (query.getKeyword() != null && !StringUtils.isBlank(query.getKeyword())) {
            dangkouQueryWrapper.like("dangkou_name",query.getKeyword());
        }
        if (query.getState() != null) {
            dangkouQueryWrapper.like("state",query.getState());
        }
        Page<Dangkou> page = new Page<>(query.getPage(), query.getLimit());
        IPage<Dangkou> iPage = dangkouMapper.selectPage(page, dangkouQueryWrapper);
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", iPage.getTotal());
        map.put("data", iPage.getRecords());
        return map;
    }

    /**
     * 修改状态
     * @param id
     * @param state
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void switchStatus(Integer id, Integer state) {
        Dangkou dangkou = dangkouMapper.selectById(id);
        AssertUtil.isTrue(dangkou==null,"该记录不存在");
        dangkou.setState(state);
        AssertUtil.isTrue(dangkouMapper.updateById(dangkou)!=1,"切换状态失败");
    }

    /**
     * 删除
     * @param id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void del(Integer id) {
        Dangkou dangkou = dangkouMapper.selectById(id);
        AssertUtil.isTrue(dangkou==null,"该记录不存在");
        AssertUtil.isTrue(dangkouMapper.deleteById(dangkou)!=1,"删除失败");
    }

    /**
     * 添加与修改
     * @param dangkou
     * @param flag
     * @param userId
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addOrUpdate(Dangkou dangkou, Integer flag, Integer userId) {
        User user = userMapper.selectById(userId);
        AssertUtil.isTrue(userId==null||user==null,"该用户不存在");
        //参数校验
        AssertUtil.isTrue(StringUtils.isBlank(dangkou.getDangkouName()), "名称不能为空");
        if (flag == 0) {
            //添加
            dangkou.setState(0);
            dangkou.setResId(user.getResId());
            dangkou.setCreateDate(new Date());
            dangkou.setUpdateDate(new Date());
            AssertUtil.isTrue(dangkouMapper.insert(dangkou)!=1,"添加失败");
        }else {
            AssertUtil.isTrue(dangkouMapper.updateById(dangkou)!=1,"修改失败");
        }

    }
}
