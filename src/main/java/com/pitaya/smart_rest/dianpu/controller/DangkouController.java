package com.pitaya.smart_rest.dianpu.controller;


import com.pitaya.smart_rest.base.ResultInfo;
import com.pitaya.smart_rest.dianpu.entity.Dangkou;
import com.pitaya.smart_rest.dianpu.entity.RfidTuopan;
import com.pitaya.smart_rest.dianpu.query.DangkuoQuery;
import com.pitaya.smart_rest.dianpu.query.TuopanQuery;
import com.pitaya.smart_rest.dianpu.service.impl.DangkouServiceImpl;
import com.pitaya.smart_rest.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.pitaya.smart_rest.base.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 档口 前端控制器
 * </p>
 *
 * @author lucien
 * @since 2022-03-29
 */
@Controller
@RequestMapping("/dianpu/dangkou")
public class DangkouController extends BaseController {

    @Autowired
    private DangkouServiceImpl dangkouService;

    /**
     * 页面路由
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "dianpu/dangkuo";
    }

    /**
     * 表格渲染
     * @param request
     * @param query
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, DangkuoQuery query){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        return dangkouService.queryList(userId,query);
    }

    /**
     * 更新与添加
     * @param flag 1=修改 0=添加
     * @return
     */
    @PostMapping("addOrUpdate")
    @ResponseBody
    public ResultInfo addOrUpdate(HttpServletRequest request, Dangkou dangkou, Integer flag) {
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        dangkouService.addOrUpdate(dangkou,flag,userId);
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
        dangkouService.del(id);
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
    public ResultInfo switchStatus(Integer id, Integer state) {
        dangkouService.switchStatus(id,state);
        return success("修改状态成功");
    }
}
