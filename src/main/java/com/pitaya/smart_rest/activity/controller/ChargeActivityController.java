package com.pitaya.smart_rest.activity.controller;


import com.pitaya.smart_rest.activity.entity.ChargeActivity;
import com.pitaya.smart_rest.activity.query.ChargeActivityQuery;
import com.pitaya.smart_rest.activity.service.impl.ChargeActivityServiceImpl;
import com.pitaya.smart_rest.base.ResultInfo;
import com.pitaya.smart_rest.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.pitaya.smart_rest.base.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 充值活动 前端控制器
 * </p>
 *
 * @author lucien
 * @since 2022-03-13
 */
@Controller
@RequestMapping("/activity/chargeActivity")
public class ChargeActivityController extends BaseController {

    @Autowired
    private ChargeActivityServiceImpl chargeActivityService;

    @RequestMapping("index")
    public String index(){
        return "activity/chargeActivity";
    }

    /**
     * 分页-多条件查询
     * @param request
     * @param
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, ChargeActivityQuery query){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        return chargeActivityService.queryList(userId,query);
    }

    /**
     * 更新添加
     *
     * @param
     * @param flag 1=修改 0=添加
     * @return
     */
    @PostMapping("addOrUpdate")
    @ResponseBody
    public ResultInfo addOrUpdate(HttpServletRequest request, ChargeActivity chargeActivity, Integer flag) {
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        chargeActivityService.addOrUpdate(chargeActivity,flag,userId);
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
        chargeActivityService.del(id);
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
        chargeActivityService.switchStatus(id,state);
        return success("修改状态成功");
    }
}
