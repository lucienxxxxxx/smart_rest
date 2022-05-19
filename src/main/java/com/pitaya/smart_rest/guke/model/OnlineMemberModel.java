package com.pitaya.smart_rest.guke.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pitaya.smart_rest.annotation.Double2Serializer;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName OnlineMemberModel
 * @author: lucine
 * @Description TODO
 * @date 2022/5/2 23:14
 * @Version 1.0版本
 */
@Data
public class OnlineMemberModel {
    @TableId(value="id" , type = IdType.AUTO)
    private Integer id;

    private String mobile;

    private String trueName;

    private String weChatId;

    private Integer orgId;

    private String orgName="没有组织";

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

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

}
