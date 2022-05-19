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
import com.pitaya.smart_rest.dingdan.entity.OrderDetail;
import com.pitaya.smart_rest.dingdan.mapper.OrderDetailMapper;
import com.pitaya.smart_rest.dingdan.mapper.OrderMapper;
import com.pitaya.smart_rest.dingdan.query.OrderQuery;
import com.pitaya.smart_rest.system.entity.Module;
import com.pitaya.smart_rest.system.entity.Org;
import com.pitaya.smart_rest.system.entity.User;
import com.pitaya.smart_rest.system.mapper.*;
import com.pitaya.smart_rest.system.model.ModuleTree;
import com.pitaya.smart_rest.system.model.OrgModel;
import com.pitaya.smart_rest.system.model.ResModel;
import com.pitaya.smart_rest.system.service.impl.OrgServiceImpl;
import com.pitaya.smart_rest.system.service.impl.PermissionServiceImpl;
import com.pitaya.smart_rest.system.service.impl.UserServiceImpl;
import com.pitaya.smart_rest.utils.ArithmeticUtils;
import com.pitaya.smart_rest.utils.AssertUtil;
import com.pitaya.smart_rest.utils.CountUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    @Resource
    private RestaurantMapper restaurantMapper;
    @Resource
    private OrgMapper orgMapper;

    @Test
    void getOrgs(){
        //所有组织列表
        List<Org> orgList = orgMapper.selectList(null);
        List<OrgModel> result=new ArrayList<>();
        //查找出所有父类列表
        orgList.forEach(org -> {
            if (org.getParentId()==-1){
                OrgModel orgModel = new OrgModel();
                orgModel.setValue(org.getId());
                orgModel.setLabel(org.getOrgName());
                result.add(orgModel);
            }
        });
        result.forEach(r->{
            r.setChildren(getChild(r.getValue(), orgList));
        });

        System.out.println(result);

    }

    private List<OrgModel> getChild(Integer pid,List<Org> orgList) {
        List<OrgModel> childOrg = new ArrayList<>();
        orgList.forEach(org -> {
            if (pid==org.getParentId()){
                OrgModel orgModel = new OrgModel();
                orgModel.setValue(org.getId());
                orgModel.setLabel(org.getOrgName());
                childOrg.add(orgModel);
            }
        });
        childOrg.forEach(c->{
            c.setChildren(getChild(c.getValue(),orgList));
        });
        if (childOrg.size()==0){
            return null;
        }
        return childOrg;
    }
    @Test
    void contextLoads() {
        List<ResModel> list1 = restaurantMapper.selectAllRes();
        System.out.println(list1);
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


    @Test
    void floatyuns() {
        Float f1 = 27.111F;
        Float f2 = 32.14F;
        Float f3 = 33.003F;
        Float f4 = 222.24F;
        System.out.println("正常运算：");
        System.out.println(f1 + f2 + f3 + f4);
        BigDecimal b1 = new BigDecimal(Float.toString(f1));
        BigDecimal b2 = new BigDecimal(Float.toString(f2));
        BigDecimal b3 = new BigDecimal(Float.toString(f3));
        BigDecimal b4 = new BigDecimal(Float.toString(f4));
        System.out.println("精确运算");
        Float result = b1.add(b2).add(b3).add(b4).floatValue();
        System.out.println(result);

    }

    @Test
    void floatyuns1() {
        Float[] floats = {221.123F, 23F, 23.22F};
        System.out.println(CountUtil.floatAdd(floats));
    }

    @Test
    public void allRefund() {

//        Long orderId =20220214093337118L;
        String orderId="20220120161325538";
        //查询该订单号是否存在
        AssertUtil.isTrue(orderMapper.selectById(orderId) == null, "该订单号不存在");
        //查询该订单所有的子订单
        QueryWrapper<OrderDetail> queryWrapper = new QueryWrapper<OrderDetail>();
        queryWrapper.eq("order_id", orderId);
        List<OrderDetail> orderDetails = orderDetailMapper.selectList(queryWrapper);
        //遍历调用子订单退款
        orderDetails.forEach(od -> {
            Float total = CountUtil.floatAdd(new Float[]{od.getCashAcc(), od.getChargeAcc(), od.getVirtualAcc(), od.getAllowanceAcc(), od.getGiftAcc()});
            System.out.println(total);
        });
    }
}
