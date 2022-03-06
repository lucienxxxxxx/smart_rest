package com.pitaya.smart_rest.dianpu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.dianpu.entity.Terminal;
import com.pitaya.smart_rest.dianpu.mapper.TerminalMapper;
import com.pitaya.smart_rest.dianpu.query.TerminalQuery;
import com.pitaya.smart_rest.dianpu.service.ITerminalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pitaya.smart_rest.system.entity.User;
import com.pitaya.smart_rest.system.mapper.UserMapper;
import com.pitaya.smart_rest.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 终端或者取餐台 服务实现类
 * </p>
 *
 * @author lucien
 * @since 2022-02-09
 */
@Service
public class TerminalServiceImpl extends ServiceImpl<TerminalMapper, Terminal> implements ITerminalService {
    @Resource
    private TerminalMapper terminalMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public Map<String, Object> queryList(Integer userId, TerminalQuery terminalQuery) {
        //餐厅id处理
        User user = userMapper.selectById(userId);
        terminalQuery.setRestaurantId(user.getResId());
        Page<Terminal> pages = new Page<Terminal>(terminalQuery.getPage(), terminalQuery.getLimit());
        IPage<Terminal> iPage = terminalMapper.selectModelPage(pages,terminalQuery);
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
        linkedHashMap.put("code", 0);
        linkedHashMap.put("msg", "");
        linkedHashMap.put("count", iPage.getTotal());
        linkedHashMap.put("data", iPage.getRecords());
        return linkedHashMap;
    }


    /**
     * 修改状态
     *
     * @param terminalId
     */
    @Override
    public void switchStatus(Integer terminalId,Integer state) {
        //查询该id的记录是否存在
        Terminal terminal = terminalMapper.selectById(terminalId);
        AssertUtil.isTrue(terminal == null, "该id不存在");
        terminal.setState(state);
        AssertUtil.isTrue(terminalMapper.updateById(terminal) != 1, "修改状态失败");
    }

    /**
     * 删除
     *
     * @param terminalId
     */
    @Override
    public void del(Integer terminalId) {
        //查询该id的记录是否存在
        Terminal terminal = terminalMapper.selectById(terminalId);
        AssertUtil.isTrue(terminal == null, "该id不存在");
        //删除
        AssertUtil.isTrue(terminalMapper.deleteById(terminal) != 1, "删除失败");
    }

    /**
     * 添加和修改
     *
     * @param terminal
     * @param flag
     */
    @Override
    public void addOrUpdate(Terminal terminal, Integer flag, Integer userId) {
        //参数校验
        AssertUtil.isTrue(StringUtils.isBlank(terminal.getTerminalName()), "终端名不能为空");
        AssertUtil.isTrue(terminal.getType()==null,"类型不能为空");

        if (flag == 0) {
            //添加
            terminal.setSettingDate(new Date());
            User user = userMapper.selectById(userId);
            terminal.setRestaurantId(user.getResId());
            terminal.setState(0);
            //插入
            AssertUtil.isTrue(terminalMapper.insert(terminal)!=1,"添加失败");
            return;
        }
        AssertUtil.isTrue(terminalMapper.updateById(terminal)!=1,"修改失败");
    }

    @Override
    public void setFood(Integer foodId,Integer terminalId) {
        Terminal terminal = terminalMapper.selectById(terminalId);
        AssertUtil.isTrue(terminal==null,"该终端id不存在");
        AssertUtil.isTrue(terminal.getType()==0,"绑定机不能设置菜品");
        terminal.setFoodId(foodId);
        AssertUtil.isTrue(terminalMapper.updateById(terminal)!=1,"设置失败");

    }
}

