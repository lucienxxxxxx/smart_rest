package com.pitaya.smart_rest.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 工作人员信息表
 * </p>
 *
 * @author lucien
 * @since 2022-01-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value="id" , type = IdType.AUTO)
    private Integer id;
    /**
     * 用户名	
     */
    private String userName;

    /**
     * 密码	
     */
    private String userPwd;

    /**
     * 绑定消费者ID
     */
    private Integer memberId;

    /**
     * 真实姓名
     */
    private String trueName;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 家庭住址
     */
    private String homeAddress;

    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
     * 更新信息日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    /**
     * 最后一次登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginDate;

    /**
     * 状态信息0=停用 1=正常
     */
    private Integer state;

    /**
     * 描述
     */
    private String descriptions;

    private Integer isValid;

    @TableField(exist = false)
    private String roleIds; // 用户对应的角色ID
}
