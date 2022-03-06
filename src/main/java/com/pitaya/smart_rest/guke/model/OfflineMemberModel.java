package com.pitaya.smart_rest.guke.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName OfflineMemberModel
 * @author: lucine
 * @Description TODO
 * @date 2022/3/5 10:47
 * @Version 1.0版本
 */
@Data
public class OfflineMemberModel {

    @TableId(value="id" , type = IdType.AUTO)
    private Integer id;

    private Integer orgId;


    private Integer resId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

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
     * 逻辑号
     */
    private String logicName;


}
