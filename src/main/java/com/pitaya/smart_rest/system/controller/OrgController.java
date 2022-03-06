package com.pitaya.smart_rest.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pitaya.smart_rest.base.ResultInfo;
import com.pitaya.smart_rest.system.entity.Org;
import com.pitaya.smart_rest.system.query.OrgQuery;
import com.pitaya.smart_rest.system.service.impl.OrgServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.pitaya.smart_rest.base.BaseController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 机构会员 可以有上级机构 前端控制器
 * </p>
 *
 * @author lucien
 * @since 2022-01-26
 */
@Controller
@RequestMapping("/system/org")
public class OrgController extends BaseController {
    @Autowired
    private OrgServiceImpl orgService;

    /**
     * 主页
     * @return
     */
    @GetMapping("index")
    public String index(){
        return "system/org";
    }

    /**
     * 表格渲染
     * @param orgQuery
     * @return
     */
    @GetMapping("queryAllOrgByParams")
    @ResponseBody
    public Map<String,Object> list(OrgQuery orgQuery){
        return orgService.queryAllOrgByParams(orgQuery);
    }

    @PostMapping("add")
    @ResponseBody
    public ResultInfo add(Org org){
        orgService.add(org);
        return success("添加成功");
    }

    @PostMapping("update")
    @ResponseBody
    public ResultInfo update(Org org){
        orgService.update(org);
        return success("修改成功");
    }

    /**
     * 删除
     * @param orgId
     * @return
     */
    @PostMapping("del")
    @ResponseBody
    public ResultInfo del(Integer orgId){
        orgService.del(orgId);
        return success("删除成功");
    }
}
