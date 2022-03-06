package com.pitaya.smart_rest.guke.controller;

import com.pitaya.smart_rest.base.BaseController;
import com.pitaya.smart_rest.base.ResultInfo;
import com.pitaya.smart_rest.dianpu.entity.RfidUser;
import com.pitaya.smart_rest.guke.entity.Member;
import com.pitaya.smart_rest.guke.query.MemberQuery;
import com.pitaya.smart_rest.guke.service.impl.OfflineMemberServiceImpl;
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

    @RequestMapping("index")
    public String index(){
        return "guke/offline/member";
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
}
