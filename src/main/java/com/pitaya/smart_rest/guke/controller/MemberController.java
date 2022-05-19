package com.pitaya.smart_rest.guke.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pitaya.smart_rest.activity.service.impl.ChargeServiceImpl;
import com.pitaya.smart_rest.base.ResultInfo;
import com.pitaya.smart_rest.guke.entity.Member;
import com.pitaya.smart_rest.guke.query.MemberQuery;
import com.pitaya.smart_rest.guke.service.impl.MemberServiceImpl;
import com.pitaya.smart_rest.utils.AssertUtil;
import com.pitaya.smart_rest.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.pitaya.smart_rest.base.BaseController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lucien
 * @since 2022-02-08
 */
@Controller
@RequestMapping("/guke/member")
public class MemberController extends BaseController {

    @Autowired
    private MemberServiceImpl memberService;
    @Autowired
    private ChargeServiceImpl chargeService;


    @PostMapping("addInfoByExcel")
    @ResponseBody
    public ResultInfo addInfoByExcel(@RequestBody JSONArray list){
        List<Map<String, Object>> resultList=memberService.addInfoByExcel(list);
        return success("成功",resultList);
    }

    @PostMapping("chargeByExcel")
    @ResponseBody
    public ResultInfo chargeByExcel(@RequestBody JSONArray list,HttpServletRequest request){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
//        System.out.println("list"+list);
        Map<String, List> map = memberService.chargeByExcel(list);
        List<JSONObject> chargeList = map.get("chargeList");
        List<JSONObject> errorList = map.get("errorList");
//        System.out.println("chargeList"+chargeList);
//        System.out.println("errorList"+errorList);
        if (!errorList.isEmpty()){
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setResult(errorList);
            resultInfo.setMsg("数据有误，请检查");
            resultInfo.setCode(300);
            return resultInfo;
        }
        for (int i = 0; i < chargeList.size(); i++) {
            JSONObject chargeObject = list.getJSONObject(i);
            String trueName = chargeObject.getString("trueName");
            String phone = chargeObject.getString("phone");
            Float virtualAcc = Float.valueOf(chargeObject.getString("virtualAcc"));
            Float giftAcc = Float.valueOf(chargeObject.getString("giftAcc"));
            Float allowanceAcc = Float.valueOf(chargeObject.getString("allowanceAcc"));
            Float cashAcc = Float.valueOf(chargeObject.getString("cashAcc"));
            Float chargeAcc = Float.valueOf(chargeObject.getString("chargeAcc"));
            QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
            memberQueryWrapper.eq("mobile",phone);
            Member member = memberService.getOne(memberQueryWrapper);
            AssertUtil.isTrue(member==null,"出现错误，出现不存在用户");
            String chargeId= String.valueOf(member.getId());
            chargeService.chargeSingle(userId,chargeId, virtualAcc, giftAcc, allowanceAcc, cashAcc, chargeAcc);

        }

        return success("充值成功");
    }

    /**
     * 每日新增列表
     */
    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, MemberQuery memberQuery){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        return memberService.queryNewMemberOrderByDate(userId, memberQuery);
    }


}
