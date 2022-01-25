package com.pitaya.smart_rest.system.controller;


import com.pitaya.smart_rest.base.ResultInfo;
import com.pitaya.smart_rest.system.entity.Role;
import com.pitaya.smart_rest.system.query.RoleQuery;
import com.pitaya.smart_rest.system.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.pitaya.smart_rest.base.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author lucien
 * @since 2022-01-22
 */
@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleServiceImpl roleService;
    @GetMapping("index")
    public String index(){
        return "system/role";
    }

    @GetMapping("list")
    @ResponseBody
    public Map<String,Object> list(RoleQuery roleQuery){
        return roleService.selectRolesByParams(roleQuery);
    }

    @PostMapping("addOrUpdate")
    @ResponseBody
    public ResultInfo addOrUpdate(Role role, Integer flag){
        roleService.addOrUpdate(role,flag);
        String msg="添加角色成功";
        if (flag==1){
            msg="修改角色成功";
        }
        return success(msg);
    }

    @PostMapping("del")
    @ResponseBody
    public ResultInfo del(Integer roleId){
        roleService.del(roleId);
        return success("删除角色成功");
    }

    @PostMapping("queryAllRoles")
    @ResponseBody
    public List<Map<String,Object>> queryAllRoles(Integer userId){
        return roleService.queryAllRolesByUserId(userId);
    }
    /**
     * 角色授权
     * @param roleId
     * @param mIds
     */
    @PostMapping("addGrant")
    @ResponseBody
    public ResultInfo addGrant(Integer roleId, Integer[] mIds) {

        roleService.addGrant(roleId, mIds);

        return success("角色授权成功！");
    }



}
