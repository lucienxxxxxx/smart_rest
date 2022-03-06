package com.pitaya.smart_rest.dianpu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pitaya.smart_rest.base.BaseQuery;
import com.pitaya.smart_rest.base.ResultInfo;
import com.pitaya.smart_rest.dianpu.entity.Food;
import com.pitaya.smart_rest.dianpu.entity.FoodTag;
import com.pitaya.smart_rest.dianpu.query.FoodQuery;
import com.pitaya.smart_rest.dianpu.service.impl.FoodTagServiceImpl;
import com.pitaya.smart_rest.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.pitaya.smart_rest.base.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lucien
 * @since 2022-02-14
 */
@Controller
@RequestMapping("/dianpu/tag")
public class FoodTagController extends BaseController {
    @Autowired
    private FoodTagServiceImpl tagService;

    @GetMapping("index")
    public String index(){
        return "dianpu/tag";
    }

    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, BaseQuery baseQuery){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        return tagService.queryList(userId,baseQuery);
    }

    /**
     * 更新添加
     * @param
     * @param flag 1=修改 0=添加
     * @return
     */
    @PostMapping("addOrUpdate")
    @ResponseBody
    public ResultInfo addOrUpdate(HttpServletRequest request, FoodTag foodTag, Integer flag) {
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        tagService.addOrUpdate(foodTag,flag,userId);
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
    public ResultInfo del(Integer tagId) {
        tagService.del(tagId);
        return success("删除成功");
    }

//    @PostMapping("queryAllTag")
//    @ResponseBody
//    public List<Map<Integer, String>> queryAllTag(Integer foodId){
//        QueryWrapper<FoodTag> foodTagQueryWrapper = new QueryWrapper<>();
//        return null;
//
//
//    }
}
