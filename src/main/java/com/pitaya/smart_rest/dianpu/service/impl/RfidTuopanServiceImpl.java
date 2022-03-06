package com.pitaya.smart_rest.dianpu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.dianpu.entity.RfidTuopan;
import com.pitaya.smart_rest.dianpu.entity.Terminal;
import com.pitaya.smart_rest.dianpu.mapper.RfidTuopanMapper;
import com.pitaya.smart_rest.dianpu.query.TuopanQuery;
import com.pitaya.smart_rest.dianpu.service.IRfidTuopanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * <p>
 * 托盘RFID卡 服务实现类
 * </p>
 *
 * @author lucien
 * @since 2022-02-12
 */
@Service
public class RfidTuopanServiceImpl extends ServiceImpl<RfidTuopanMapper, RfidTuopan> implements IRfidTuopanService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RfidTuopanMapper rfidTuopanMapper;

    /**
     * 表格
     * @param userId
     * @param tuopanQuery
     * @return
     */
    @Override
    public Map<String, Object> queryList(Integer userId, TuopanQuery tuopanQuery) {
        User user = userMapper.selectById(userId);
        QueryWrapper<RfidTuopan> rfidTuopanQueryWrapper = new QueryWrapper<RfidTuopan>();
        AssertUtil.isTrue(userId==null||user==null,"该用户不存在");
        rfidTuopanQueryWrapper.eq("res_id", user.getResId());
        if (tuopanQuery.getId() != null) {
            rfidTuopanQueryWrapper.eq("id", tuopanQuery.getId());
        }
        if (tuopanQuery.getStateId() != null) {
            rfidTuopanQueryWrapper.like("state_id", tuopanQuery.getStateId());
        }
        if (tuopanQuery.getLogicName() != null && !StringUtils.isBlank(tuopanQuery.getLogicName())) {
            rfidTuopanQueryWrapper.like("logic_name", tuopanQuery.getLogicName());
        }
        if (tuopanQuery.getDescriptions() != null && !StringUtils.isBlank(tuopanQuery.getDescriptions())) {
            rfidTuopanQueryWrapper.like("descriptions", tuopanQuery.getDescriptions());
        }
        Page<RfidTuopan> page = new Page<>(tuopanQuery.getPage(), tuopanQuery.getLimit());
        IPage<RfidTuopan> iPage = rfidTuopanMapper.selectPage(page, rfidTuopanQueryWrapper);
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", iPage.getTotal());
        map.put("data", iPage.getRecords());
        return map;
    }

    /**
     * 添加和修改
     * @param rfidTuopan
     * @param flag 0=添加 1=修改
     * @param userId
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addOrUpdate(RfidTuopan rfidTuopan, Integer flag, Integer userId) {
        User user = userMapper.selectById(userId);
        AssertUtil.isTrue(userId==null||user==null,"该用户不存在");
        //参数校验
        AssertUtil.isTrue(StringUtils.isBlank(rfidTuopan.getLogicName()), "逻辑号不能为空");
        AssertUtil.isTrue(rfidTuopan.getId() == null, "ID不能为空");
        if (flag == 0) {
            //添加
            rfidTuopan.setStateId(1);
            rfidTuopan.setResId(user.getResId());
            AssertUtil.isTrue(rfidTuopanMapper.insert(rfidTuopan)!=1,"添加失败");
        }else {
            AssertUtil.isTrue(rfidTuopanMapper.updateById(rfidTuopan)!=1,"修改失败");
        }

    }

    /**
     * 删除
     * @param tuopanId
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void del(Integer tuopanId) {
        RfidTuopan rfidTuopan = rfidTuopanMapper.selectById(tuopanId);
        AssertUtil.isTrue(rfidTuopan==null,"该记录不存在");
        AssertUtil.isTrue(rfidTuopanMapper.deleteById(tuopanId)!=1,"删除失败");
    }

    /**
     * 切换状态
     * @param tuopanId
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void switchStatus(Integer tuopanId,Integer state) {
        RfidTuopan rfidTuopan = rfidTuopanMapper.selectById(tuopanId);
        AssertUtil.isTrue(rfidTuopan==null,"该记录不存在");
        rfidTuopan.setStateId(state);
        AssertUtil.isTrue(rfidTuopanMapper.updateById(rfidTuopan)!=1,"切换状态失败");
    }
}
