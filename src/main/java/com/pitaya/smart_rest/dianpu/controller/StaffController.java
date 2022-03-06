package com.pitaya.smart_rest.dianpu.controller;

import com.pitaya.smart_rest.base.BaseController;
import com.pitaya.smart_rest.base.ResultInfo;
import com.pitaya.smart_rest.dianpu.query.StaffQuery;
import com.pitaya.smart_rest.guke.entity.Member;
import com.pitaya.smart_rest.guke.service.impl.StaffServiceImpl;
import com.pitaya.smart_rest.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName StaffController
 * @author: lucine
 * @Description TODO
 * @date 2022/3/1 15:58
 * @Version 1.0版本
 */
@Controller
@RequestMapping("/dianpu/staff")
public class StaffController extends BaseController {
    @Autowired
    private StaffServiceImpl staffService;

    /**
     * 主页
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "dianpu/staff";
    }


    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, StaffQuery staffQuery){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        return staffService.queryList(userId,staffQuery);
    }

    /**
     * 更新添加用户
     *
     * @param
     * @param flag 1=修改 0=添加
     * @return
     */
    @PostMapping("addOrUpdate")
    @ResponseBody
    public ResultInfo addOrUpdate(HttpServletRequest request, Member member, Integer flag) {
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        staffService.addOrUpdate(member,flag,userId);
        String msg = "添加成功";
        if (flag == 1) {
            msg = "修改成功";
        }
        return success(msg);
    }

    /**
     * 删除
     *
     * @param
     * @return
     */
    @PostMapping("del")
    @ResponseBody
    public ResultInfo del(Integer id) {
        staffService.del(id);
        return success("删除成功");
    }

    /**
     * 修改状态
     *
     * @param
     * @return
     */
    @PostMapping("switchStatus")
    @ResponseBody
    public ResultInfo switchStatus(Integer id,Integer state) {
        staffService.switchStatus(id,state);
        return success("修改状态成功");
    }
    
}
