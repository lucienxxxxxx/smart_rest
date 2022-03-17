package com.pitaya.smart_rest.activity.entity;

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
 * 充值活动
 * </p>
 *
 * @author lucien
 * @since 2022-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_charge_activity")
public class ChargeActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value="id" , type = IdType.AUTO)
    private Integer id;
    private String activityName;
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

    @TableField(exist = false)
    private String dateRange;
}
