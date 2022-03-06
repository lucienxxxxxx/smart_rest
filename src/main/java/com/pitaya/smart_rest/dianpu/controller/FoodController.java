package com.pitaya.smart_rest.dianpu.controller;


import com.pitaya.smart_rest.base.ResultInfo;
import com.pitaya.smart_rest.dianpu.entity.Food;
import com.pitaya.smart_rest.dianpu.entity.FoodTag;
import com.pitaya.smart_rest.dianpu.entity.RfidTuopan;
import com.pitaya.smart_rest.dianpu.query.FoodQuery;
import com.pitaya.smart_rest.dianpu.query.TuopanQuery;
import com.pitaya.smart_rest.dianpu.service.impl.FoodServiceImpl;
import com.pitaya.smart_rest.dianpu.service.impl.FoodTagServiceImpl;
import com.pitaya.smart_rest.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.pitaya.smart_rest.base.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 食物或菜品 前端控制器
 * </p>
 *
 * @author lucien
 * @since 2022-02-09
 */
@Controller
@RequestMapping("/dianpu/food")
public class FoodController extends BaseController {
    @Autowired
    private FoodServiceImpl foodService;
    @Autowired
    private FoodTagServiceImpl foodTagService;
    @GetMapping("index")
    public String index(Model model){
        List<FoodTag> list = foodTagService.list();
        model.addAttribute("tagList",list);
        return "dianpu/food";
    }

    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, FoodQuery foodQuery){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        return foodService.queryList(userId,foodQuery);
    }

    /**
     * 更新添加
     * @param
     * @param flag 1=修改 0=添加
     * @return
     */
    @PostMapping("addOrUpdate")
    @ResponseBody
    public ResultInfo addOrUpdate(HttpServletRequest request, Food food, Integer flag) {
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        foodService.addOrUpdate(food, flag,userId);
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
    public ResultInfo del(Integer foodId) {
        foodService.del(foodId);
        return success("删除成功");
    }

}
