package com.pitaya.smart_rest.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pitaya.smart_rest.base.ResultInfo;
import com.pitaya.smart_rest.system.entity.Restaurant;
import com.pitaya.smart_rest.system.entity.User;
import com.pitaya.smart_rest.system.service.impl.RestaurantServiceImpl;
import com.pitaya.smart_rest.system.service.impl.UserServiceImpl;
import com.pitaya.smart_rest.utils.AssertUtil;
import com.pitaya.smart_rest.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.pitaya.smart_rest.base.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p>
 * 餐厅 前端控制器
 * </p>
 *
 * @author lucien
 * @since 2022-02-20
 */
@Controller
@RequestMapping("/system/restaurant")
public class RestaurantController extends BaseController {
    @Autowired
    private RestaurantServiceImpl restaurantService;
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("index")
    public String index(HttpServletRequest request, Model model) {
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        User user = userService.getById(userId);
        AssertUtil.isTrue(user==null,"该用户不存在");
        Restaurant rest = restaurantService.getById(user.getResId());
        if (rest==null){
            Restaurant restaurant = new Restaurant();
            model.addAttribute("rest", restaurant);
        }else {
            model.addAttribute("rest", rest);
        }
        return "system/restaurant";
    }

    @PostMapping("update")
    @ResponseBody
    public ResultInfo update(Restaurant restaurant){
        System.out.println(restaurant);
        if (restaurant.getId()==null){
            restaurant.setCreateDate(new Date());
            restaurant.setUpdateDate(new Date());
            restaurant.setState(1);
            AssertUtil.isTrue(!restaurantService.save(restaurant),"设置失败");
        }else {
            AssertUtil.isTrue(!restaurantService.updateById(restaurant),"设置失败");
        }
        return success("修改成功");
    }
}
