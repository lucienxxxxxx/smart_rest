package com.pitaya.smart_rest.dianpu.controller;


import com.pitaya.smart_rest.base.ResultInfo;
import com.pitaya.smart_rest.dianpu.entity.RfidTuopan;
import com.pitaya.smart_rest.dianpu.entity.RfidUser;
import com.pitaya.smart_rest.dianpu.query.CardQuery;
import com.pitaya.smart_rest.dianpu.query.TuopanQuery;
import com.pitaya.smart_rest.dianpu.service.impl.RfidUserServiceImpl;
import com.pitaya.smart_rest.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.pitaya.smart_rest.base.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * RFID卡(用户和管理卡) 前端控制器
 * </p>
 *
 * @author lucien
 * @since 2022-03-01
 */
@Controller
@RequestMapping("/dianpu/rfid-user")
public class RfidUserController extends BaseController {

    @Autowired
    private RfidUserServiceImpl rfidUserService;

    /**
     * 主页
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "dianpu/card";
    }


    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, CardQuery cardQuery){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        return rfidUserService.queryList(userId,cardQuery);
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
    public ResultInfo addOrUpdate(HttpServletRequest request, RfidUser rfidUser, Integer flag) {
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        rfidUserService.addOrUpdate(rfidUser,flag,userId);
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
    public ResultInfo del(Integer cardId) {
        rfidUserService.del(cardId);
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
    public ResultInfo switchStatus(Integer cardId,Integer state) {
        rfidUserService.switchStatus(cardId,state);
        return success("修改状态成功");
    }
}
