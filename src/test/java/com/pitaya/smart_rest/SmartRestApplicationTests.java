package com.pitaya.smart_rest;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pitaya.smart_rest.dianpu.entity.FoodTag;
import com.pitaya.smart_rest.dianpu.entity.Terminal;
import com.pitaya.smart_rest.dianpu.mapper.FoodTagMapper;
import com.pitaya.smart_rest.dianpu.mapper.TerminalMapper;
import com.pitaya.smart_rest.dianpu.query.TerminalQuery;
import com.pitaya.smart_rest.dianpu.service.impl.FoodTagServiceImpl;
import com.pitaya.smart_rest.dingdan.mapper.OrderDetailMapper;
import com.pitaya.smart_rest.dingdan.mapper.OrderMapper;
import com.pitaya.smart_rest.dingdan.query.OrderQuery;
import com.pitaya.smart_rest.system.entity.Module;
import com.pitaya.smart_rest.system.entity.User;
import com.pitaya.smart_rest.system.mapper.ModuleMapper;
import com.pitaya.smart_rest.system.mapper.RoleMapper;
import com.pitaya.smart_rest.system.mapper.UserMapper;
import com.pitaya.smart_rest.system.model.ModuleTree;
import com.pitaya.smart_rest.system.service.impl.OrgServiceImpl;
import com.pitaya.smart_rest.system.service.impl.PermissionServiceImpl;
import com.pitaya.smart_rest.system.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SmartRestApplicationTests {
    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserMapper userMapper;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private OrgServiceImpl orgService;
    @Resource
    private OrderDetailMapper orderDetailMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private TerminalMapper terminalMapper;
    @Resource
    private FoodTagMapper foodTagMapper;
    @Resource
    private ModuleMapper moduleMapper;
    @Autowired
    private FoodTagServiceImpl foodTagService;
    @Autowired
    private PermissionServiceImpl permissionService;
    @Test
    void contextLoads() {
        List<ModuleTree> maps = permissionService.queryModelPermissions(2);
        System.out.println(maps);
    }

    @Test
    void test1() {
        TerminalQuery terminalQuery = new TerminalQuery();
        User user = userMapper.selectById(4);
        System.out.println(user);
        terminalQuery.setRestaurantId(user.getResId());
        Page<Terminal> pages = new Page<Terminal>(terminalQuery.getPage(), terminalQuery.getLimit());
        IPage<Terminal> iPage = terminalMapper.selectModelPage(pages, terminalQuery);
        List<Terminal> list = iPage.getRecords();
        long count = iPage.getTotal();
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
        linkedHashMap.put("code", 0);
        linkedHashMap.put("msg", "");
        linkedHashMap.put("count", count);
        linkedHashMap.put("data", list);
        System.out.println(list);
    }

}
