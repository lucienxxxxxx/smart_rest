package com.pitaya.smart_rest.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
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
    private Long memberId;

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
    private String homeAdress;

    /**
     * 创建日期
     */
    private LocalDateTime createDate;

    /**
     * 更新信息日期
     */
    private LocalDateTime updateDate;

    /**
     * 最后一次登录时间
     */
    private LocalDateTime loginDate;

    /**
     * 状态信息0=停用 1=正常
     */
    private Integer state;

    /**
     * 描述
     */
    private String descriptions;


}
