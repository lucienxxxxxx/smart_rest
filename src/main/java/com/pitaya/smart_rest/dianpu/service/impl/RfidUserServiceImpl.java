package com.pitaya.smart_rest.dianpu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.dianpu.entity.RfidTuopan;
import com.pitaya.smart_rest.dianpu.entity.RfidUser;
import com.pitaya.smart_rest.dianpu.mapper.RfidUserMapper;
import com.pitaya.smart_rest.dianpu.query.CardQuery;
import com.pitaya.smart_rest.dianpu.service.IRfidUserService;
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
 * RFID卡(用户和管理卡) 服务实现类
 * </p>
 *
 * @author lucien
 * @since 2022-03-01
 */
@Service
public class RfidUserServiceImpl extends ServiceImpl<RfidUserMapper, RfidUser> implements IRfidUserService {

    @Resource
    private RfidUserMapper rfidUserMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Map<String, Object> queryList(Integer userId, CardQuery cardQuery) {
        User user = userMapper.selectById(userId);
        QueryWrapper<RfidUser> rfidUserQueryWrapper = new QueryWrapper<RfidUser>();
        AssertUtil.isTrue(userId==null||user==null,"该用户不存在");
        rfidUserQueryWrapper.eq("res_id", user.getResId());//查询该餐厅
        rfidUserQueryWrapper.eq("type_id", 0);//查询管理卡
        if (cardQuery.getStateId() != null) {
            rfidUserQueryWrapper.like("state_id", cardQuery.getStateId());
        }
        if (cardQuery.getLogicName() != null && !StringUtils.isBlank(cardQuery.getLogicName())) {
            rfidUserQueryWrapper.like("logic_name", cardQuery.getLogicName());
        }
        if (cardQuery.getDescriptions() != null && !StringUtils.isBlank(cardQuery.getDescriptions())) {
            rfidUserQueryWrapper.like("descriptions", cardQuery.getDescriptions());
        }
        Page<RfidUser> page = new Page<>(cardQuery.getPage(), cardQuery.getLimit());
        IPage<RfidUser> iPage = rfidUserMapper.selectPage(page, rfidUserQueryWrapper);
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", iPage.getTotal());
        map.put("data", iPage.getRecords());
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addOrUpdate(RfidUser rfidUser, Integer flag, Integer userId) {
        User user = userMapper.selectById(userId);
        AssertUtil.isTrue(userId==null||user==null,"该用户不存在");
        //参数校验
        AssertUtil.isTrue(StringUtils.isBlank(rfidUser.getLogicName()), "逻辑号不能为空");
        if (flag == 0) {
            //添加
            rfidUser.setStateId(1);
            rfidUser.setTypeId(0);//设置成管理卡
            rfidUser.setResId(user.getResId());//设置所属餐厅
            AssertUtil.isTrue(rfidUserMapper.insert(rfidUser)!=1,"添加失败");
        }else {
            AssertUtil.isTrue(rfidUserMapper.updateById(rfidUser)!=1,"修改失败");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void del(Integer cardId) {
        RfidUser rfidUser = rfidUserMapper.selectById(cardId);
        AssertUtil.isTrue(rfidUser==null,"该记录不存在");
        AssertUtil.isTrue(rfidUserMapper.deleteById(cardId)!=1,"删除失败");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void switchStatus(Integer cardId, Integer state) {
        RfidUser rfidUser = rfidUserMapper.selectById(cardId);
        AssertUtil.isTrue(rfidUser==null,"该记录不存在");
        rfidUser.setStateId(state);
        AssertUtil.isTrue(rfidUserMapper.updateById(rfidUser)!=1,"切换状态失败");
    }
}
