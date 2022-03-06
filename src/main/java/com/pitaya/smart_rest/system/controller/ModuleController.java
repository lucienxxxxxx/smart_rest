package com.pitaya.smart_rest.system.controller;


import com.pitaya.smart_rest.base.ResultInfo;
import com.pitaya.smart_rest.system.model.TreeModel;
import com.pitaya.smart_rest.system.service.impl.ModuleServiceImpl;
import com.pitaya.smart_rest.system.service.impl.PermissionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.pitaya.smart_rest.system.entity.Module;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pitaya.smart_rest.base.BaseController;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限模块 前端控制器
 * </p>
 *
 * @author lucien
 * @since 2022-01-22
 */
@Controller
@RequestMapping("/system/module")
public class ModuleController extends BaseController {

    @Autowired
    private ModuleServiceImpl moduleService;

    @Autowired
    private PermissionServiceImpl permissionService;

    @RequestMapping("queryModules")
    @ResponseBody
    public ResultInfo queryModules(Integer userId){
        return success("成功",permissionService.queryModelPermissions(userId));
    }

    /**
     * 模块权限主页
     * @return
     */
    @GetMapping("index")
    public String index(){
        return "system/module";
    }

    @RequestMapping("queryAllModules")
    @ResponseBody
    public List<TreeModel> queryAllModules(Integer roleId) {
        System.out.println(moduleService.queryAllModules(roleId));
        return moduleService.queryAllModules(roleId);
    }

    /**
     * 渲染表格
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryModuleList() {
        return moduleService.queryModuleList();
    }
    /***
     * 进入授权页面
     * @param roleId
     * @return java.lang.String
     */
    @RequestMapping("toAddGrantPage")
    public String toAddGrantPage(Integer roleId, HttpServletRequest request) {
        // 将需要授权的角色ID设置到请求域中
        request.setAttribute("roleId", roleId);
        return "system/roleGrant";
    }

    /**
     * 打开添加资源的页面
     * @param grade 层级
     * @param parentId 父菜单ID
     * @return java.lang.String
     */
    @RequestMapping("toAddModulePage")
    public String toAddModulePage(Integer grade, Integer parentId, HttpServletRequest request) {
        // 将数据设置到请求域中
        request.setAttribute("grade", grade);
        request.setAttribute("parentId", parentId);
        return "system/moduleAdd";
    }

    /**
     * 打开修改资源的页面
     * @param id
     * @return java.lang.String
     */
    @RequestMapping("toUpdateModulePage")
    public String toUpdateModulePage(Integer id, Model model) {
        // 将要修改的资源对象设置到请求域中
        model.addAttribute("module", moduleService.getById(id));
        return "system/moduleUpdate";
    }

    /**
     * 添加资源
     * @param module
     * @return
     */
    @PostMapping("add")
    @ResponseBody
    public ResultInfo addModule(Module module) {
        moduleService.addModule(module);
        return success("添加资源成功！");
    }

    /**
     * 修改资源
     * @param module
     */
    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateModule(Module module) {
        moduleService.updateModule(module);
        return success("修改资源成功！");
    }

    /**
     * 删除资源
     * @param id
     */
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteModule(Integer id) {
        moduleService.deleteModule(id);
        return success("删除资源成功！");
    }


}
