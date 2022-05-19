package com.pitaya.smart_rest.guke.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pitaya.smart_rest.activity.service.impl.ChargeServiceImpl;
import com.pitaya.smart_rest.base.BaseController;
import com.pitaya.smart_rest.base.ResultInfo;
import com.pitaya.smart_rest.dianpu.entity.RfidUser;
import com.pitaya.smart_rest.guke.entity.Member;
import com.pitaya.smart_rest.guke.query.MemberQuery;
import com.pitaya.smart_rest.guke.service.impl.OfflineMemberServiceImpl;
import com.pitaya.smart_rest.utils.AssertUtil;
import com.pitaya.smart_rest.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OfflineMember
 * @author: lucine
 * @Description TODO
 * @date 2022/3/3 16:49
 * @Version 1.0版本
 */
@Controller
@RequestMapping("guke/offlineMember")
public class OfflineMemberController extends BaseController {

    @Autowired
    private OfflineMemberServiceImpl memberService;
    @Autowired
    private ChargeServiceImpl chargeService;

    @RequestMapping("index")
    public String index(){
        return "guke/offline/member";
    }


    /**
     * 打开导入excel批量添加页面
     * @return
     */
    @RequestMapping("toAddBatch")
    public String addBatch(){
        return "guke/offline/addBatch";
    }

    /**
     * 打开导入excel批量充值页面
     * @return
     */
    @RequestMapping("toChargeBatch")
    public String toChargeBatch(){
        return "guke/offline/chargeBatch";
    }
    /**
     * 导入excel批量添加
     * @return
     */
    @PostMapping("addBatch")
    @ResponseBody
    public ResultInfo addBatchByExcel(@RequestBody JSONArray list,HttpServletRequest request){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        List<Map<String, Object>> resultList=memberService.addBatch(list,userId);
        if (resultList.isEmpty()){
            return success("成功",resultList);
        }else {
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setCode(300);
            resultInfo.setMsg("数据有误，请检查");
            resultInfo.setResult(resultList);
            return resultInfo;
        }

    }

    /**
     * 分页-条件查询
     * @param request
     * @param memberQuery
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, MemberQuery memberQuery){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        return memberService.queryList(userId, memberQuery);
    }

    /**
     * 更新
     *
     * @param
     * @param flag 1=修改 0=添加
     * @return
     */
    @PostMapping("addOrUpdate")
    @ResponseBody
    public ResultInfo addOrUpdate(HttpServletRequest request, Member member, RfidUser rfidUser,Integer flag) {
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        memberService.addOrUpdate(member,rfidUser,flag,userId);
        String msg = "添加成功";
        if (flag == 1) {
            msg = "修改成功";
        }
        return success(msg);
    }

    /**
     * 修改状态
     *
     * @param
     * @return
     */
    @PostMapping("switchStatus")
    @ResponseBody
    public ResultInfo switchStatus(Integer id, Integer state) {
        memberService.switchStatus(id,state);
        return success("修改状态成功");
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
        memberService.del(id);
        return success("删除成功");
    }

    @PostMapping("chargeByExcel")
    @ResponseBody
    public ResultInfo chargeByExcel(@RequestBody JSONArray list,HttpServletRequest request){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        List<Map<String, Object>> resultList=memberService.chargeByExcel(list,userId);
        if (resultList.isEmpty()){
            return success("成功",resultList);
        }else {
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setCode(300);
            resultInfo.setMsg("数据有误，请检查");
            resultInfo.setResult(resultList);
            return resultInfo;
        }

    }
}
