package com.pitaya.smart_rest.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pitaya.smart_rest.base.BaseController;
import com.pitaya.smart_rest.base.ResultInfo;
import com.pitaya.smart_rest.system.entity.Restaurant;
import com.pitaya.smart_rest.system.entity.User;
import com.pitaya.smart_rest.system.model.ResModel;
import com.pitaya.smart_rest.system.query.RestaurantQuery;
import com.pitaya.smart_rest.system.service.impl.RestaurantServiceImpl;
import com.pitaya.smart_rest.system.service.impl.UserServiceImpl;
import com.pitaya.smart_rest.utils.AssertUtil;
import com.pitaya.smart_rest.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RestaurantController
 * @author: lucine
 * @Description 餐厅管理
 * @date 2022/3/29 17:03
 * @Version 1.0版本
 */
@Controller
@RequestMapping("system/restaurant")
public class RestaurantController extends BaseController {

    @Autowired
    private RestaurantServiceImpl restaurantService;
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("getAllRestaurant")
    @ResponseBody
    public List<ResModel> getAllRestaurant(){
        List<ResModel> resDate = restaurantService.getAllRestaurant();
        return resDate;
    }

    @PostMapping("foodMenuMove")
    @ResponseBody
    public ResultInfo foodMenuMove(Integer resId,Integer toResId,Integer isCover){
        restaurantService.foodMenuMove(resId,toResId,isCover);
        return success();
    }

    /**
     * 管理页面
     * @return
     */
    @GetMapping("index")
    public String index() {
        return "system/restaurant";
    }

    /**
     * 表格渲染
     * @param query
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> list(RestaurantQuery query) {
        return restaurantService.queryList(query);
    }

    /**
     * 更新添加用户
     * @param flag       1=修改 0=添加
     * @return
     */
    @PostMapping("addOrUpdate")
    @ResponseBody
    public ResultInfo addOrUpdate(Restaurant restaurant, Integer flag) {
        restaurantService.addOrUpdate(restaurant, flag);
        String msg = "用户添加成功";
        if (flag == 1) {
            msg = "用户修改成功";
        }
        return success(msg);
    }

    /**
     * 删除
     * @param
     * @return
     */
    @PostMapping("del")
    @ResponseBody
    public ResultInfo del(HttpServletRequest request, Integer id) {
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        User user = userService.getById(userId);
        AssertUtil.isTrue(user.getResId()==id,"不能删除自己所属的餐厅");
        restaurantService.del(id,userId);
        return success("删除成功");
    }

    /**
     * 修改状态
     * @return
     */
    @PostMapping("switchStatus")
    @ResponseBody
    public ResultInfo switchStatus(HttpServletRequest request,Integer id, Integer state) {
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        User user = userService.getById(userId);
        AssertUtil.isTrue(user.getResId()==id,"不能更改自己所属的餐厅的状态");
        restaurantService.switchStatus(id, state,userId);
        return success("修改状态成功");
    }

}
