package com.pitaya.smart_rest.activity.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pitaya.smart_rest.activity.model.ChargeModel;
import com.pitaya.smart_rest.activity.query.MemberActivityQuery;
import com.pitaya.smart_rest.activity.vo.ChargeVo;
import com.pitaya.smart_rest.base.BaseController;
import com.pitaya.smart_rest.activity.service.impl.ChargeServiceImpl;
import com.pitaya.smart_rest.base.BaseQuery;
import com.pitaya.smart_rest.base.ResultInfo;
import com.pitaya.smart_rest.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ChargeController
 * @author: lucine
 * @Description TODO
 * @date 2022/3/15 15:33
 * @Version 1.0版本
 */
@Controller
@RequestMapping("/activity/charge")
public class ChargeController extends BaseController {

    @Autowired
    private ChargeServiceImpl chargeService;

    @RequestMapping("index")
    public String index(){
        return "activity/memberCharge";
    }

    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, BaseQuery query){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        return chargeService.queryList(userId,query);
    }

    @PostMapping("charge")
    @ResponseBody
    public ResultInfo charge(String ids,Float virtualAcc,Float giftAcc,Float allowanceAcc,Float cashAcc,Float chargeAcc){
        System.out.println(ids);
        System.out.println("充值金额:"+virtualAcc+","+giftAcc+","+allowanceAcc+","+cashAcc+","+chargeAcc);

//        chargeService.charge(ids,virtualAcc,giftAcc,allowanceAcc,cashAcc,chargeAcc);
        return success("充值成功");
    }

}
