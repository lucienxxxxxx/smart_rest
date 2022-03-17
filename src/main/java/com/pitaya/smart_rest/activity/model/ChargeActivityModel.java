package com.pitaya.smart_rest.activity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName ChargeActivityModel
 * @author: lucine
 * @Description TODO
 * @date 2022/3/13 18:14
 * @Version 1.0版本
 */
@Data
public class ChargeActivityModel {
    @TableId(value="id" , type = IdType.AUTO)
    private Integer id;

    /**
     * 活动类型：0=送金额 1=送礼品
     */
    private Integer activityType;

    /**
     * 充值
     */
    private Float chargeMomey;

    /**
     * 实际到账
     */
    private Float discountsMomey;

    private Integer state;

    private String note;

    /**
     * 能不能多次参加，
     */
    private Integer multi;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    private Integer resId;

    private String dateRange;

    private String activityName;
}
