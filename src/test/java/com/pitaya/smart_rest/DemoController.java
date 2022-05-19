package com.pitaya.smart_rest;

import com.pitaya.smart_rest.base.BaseController;
import com.pitaya.smart_rest.base.BaseQuery;
import com.pitaya.smart_rest.base.ResultInfo;
import com.pitaya.smart_rest.dianpu.entity.RfidTuopan;
import com.pitaya.smart_rest.dianpu.query.TuopanQuery;
import com.pitaya.smart_rest.dianpu.service.impl.RfidTuopanServiceImpl;
import com.pitaya.smart_rest.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName mobang
 * @author: lucine
 * @Description TODO
 * @date 2022/3/29 19:47
 * @Version 1.0版本
 */
public class DemoController extends BaseController {
    @Autowired
    private RfidTuopanServiceImpl rfidTuopanService;

/*

  @GetMapping("index")
    public String index() {
        return "dianpu/tuopan";
    }

 */

/*
 @GetMapping("list")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, BaseQuery query) {
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        return rfidTuopanService.queryList(userId, query);
    }
 */


    /**
     * 更新添加用户
     *
     * @param rfidTuopan 用户实体
     * @param flag       1=修改 0=添加
     * @return
     */
    @PostMapping("addOrUpdate")
    @ResponseBody
    public ResultInfo addOrUpdate(HttpServletRequest request, RfidTuopan rfidTuopan, Integer flag) {
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        rfidTuopanService.addOrUpdate(rfidTuopan, flag, userId);
        String msg = "用户添加成功";
        if (flag == 1) {
            msg = "用户修改成功";
        }
        return success(msg);
    }

    /**
     * 删除
     *
     * @param tuopanId
     * @return
     */
    @PostMapping("del")
    @ResponseBody
    public ResultInfo del(Integer tuopanId) {
        rfidTuopanService.del(tuopanId);
        return success("删除成功");
    }

    /**
     * 修改状态
     *
     * @param tuopanId
     * @return
     */
    @PostMapping("switchStatus")
    @ResponseBody
    public ResultInfo switchStatus(Integer tuopanId, Integer state) {
        rfidTuopanService.switchStatus(tuopanId, state);
        return success("修改状态成功");
    }


}
