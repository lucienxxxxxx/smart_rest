package com.pitaya.smart_rest.dianpu.controller;


import com.pitaya.smart_rest.base.BaseQuery;
import com.pitaya.smart_rest.base.ResultInfo;
import com.pitaya.smart_rest.dianpu.entity.FoodTag;
import com.pitaya.smart_rest.dianpu.entity.Terminal;
import com.pitaya.smart_rest.dianpu.query.TerminalQuery;
import com.pitaya.smart_rest.dianpu.service.impl.FoodTagServiceImpl;
import com.pitaya.smart_rest.dianpu.service.impl.TerminalServiceImpl;
import com.pitaya.smart_rest.system.entity.User;
import com.pitaya.smart_rest.utils.CookieUtil;
import com.pitaya.smart_rest.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.pitaya.smart_rest.base.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 终端或者取餐台 前端控制器
 * </p>
 *
 * @author lucien
 * @since 2022-02-09
 */
@Controller
@RequestMapping("/dianpu/terminal")
public class TerminalController extends BaseController {
    @Autowired
    private TerminalServiceImpl terminalService;
    @Autowired
    private FoodTagServiceImpl foodTagService;
    @GetMapping("index")
    public String index(Model model){
        List<FoodTag> list = foodTagService.list();
        model.addAttribute("tagList",list);
        return "dianpu/terminal";
    }

    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request,TerminalQuery terminalQuery){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        return terminalService.queryList(userId,terminalQuery);
    }

    /**
     * 更新添加
     *
     * @param terminal 用户实体
     * @param flag 1=修改 0=添加
     * @return
     */
    @PostMapping("addOrUpdate")
    @ResponseBody
    public ResultInfo addOrUpdate(HttpServletRequest request,Terminal terminal, Integer flag) {
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        terminalService.addOrUpdate(terminal, flag,userId);
        String msg = "添加成功";
        if (flag == 1) {
            msg = "修改成功";
        }
        return success(msg);
    }

    /**
     * 删除
     *
     * @param terminalId
     * @return
     */
    @PostMapping("del")
    @ResponseBody
    public ResultInfo del(Integer terminalId) {
        terminalService.del(terminalId);
        return success("删除成功");
    }

    /**
     * 修改状态
     *
     * @param terminalId
     * @return
     */
    @PostMapping("switchStatus")
    @ResponseBody
    public ResultInfo switchStatus(Integer terminalId,Integer state) {
        terminalService.switchStatus(terminalId,state);
        return success("修改状态成功");
    }
    /**
     * 修改当前食物
     *
     * @param foodId
     * @return
     */
    @PostMapping("setFood")
    @ResponseBody
    public ResultInfo setFood(Integer foodId,Integer terminalId) {
        terminalService.setFood(foodId,terminalId);
        return success("设置成功");
    }
}
