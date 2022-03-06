package com.pitaya.smart_rest.guke.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 
 * </p>
 *
 * @author lucien
 * @since 2022-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_member")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value="id" , type = IdType.AUTO)
    private Integer id;

    private Integer orgId;

    private String nickname;

    private Integer resId;

    private String weChatId;

    private Integer isValid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    private String faceUrl;

    private String trueName;
    private String mobile;
    /**
     * 0=启用 1=禁用  2=挂失 3=删除
     */
    private Integer state;

    private String note;
    /**
     * 虚拟账号用于员工或领导的赠送
     */
    private Float virtualAcc;

    /**
     * 活动赠送
     */
    private Float giftAcc;

    /**
     * 补贴账号
     */
    private Float allowanceAcc;

    /**
     * 现金充值 支付宝等
     */
    private Float cashAcc;

    /**
     * 在线充值
     */
    private Float chargeAcc;

    /**
     * 如参与充值赠送活动用，每次数据随充值账号消费减小而减小。直到为0. 可提现金额=提现账号-该字段数据
     */
    private Float cashoutLimit;

    /**
     * 会员号
     */
    private Integer inductionId;

    /**
     *  0线上 1线下 2员工
     */
    private Integer memberType;


}
