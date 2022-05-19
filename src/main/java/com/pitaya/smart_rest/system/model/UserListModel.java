package com.pitaya.smart_rest.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName UserListModel
 * @author: lucine
 * @Description TODO
 * @date 2022/4/8 22:57
 * @Version 1.0版本
 */
@Data
public class UserListModel {
    /**
     * u.id,
     * u.user_name,
     * u.true_name,
     * u.phone,
     * u.email,
     * u.create_date,
     * u.login_date,
     * u.state,
     * u.descriptions,
     * r.restaurant_name,
     * GROUP_CONCAT( ro.role_name ) as roles
     */

    private Integer id;
    private String userName;
    private String trueName;
    private String phone;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginDate;
    private Integer state;
    private String descriptions="无";
    private String restaurantName;
    private String roles;//角色数组 用","分割
}
