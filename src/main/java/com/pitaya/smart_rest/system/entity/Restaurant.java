package com.pitaya.smart_rest.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 餐厅
 * </p>
 *
 * @author lucien
 * @since 2022-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_restaurant")
public class Restaurant implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value="id" , type = IdType.AUTO)
    private Integer id;

    private String restaurantName;

    /**
     * 0=正常 1=暂停 2=停用
     */
    private Integer state;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    private String address;

    private String descriptions;

    private Integer bangdingTime;

    private Integer delayedBangdingTime;

    private Integer bangdingMinAccount;

    private Integer bangdingMultiAccount;

    private Integer minAccountQuota;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Time breakfastStart;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Time breakfastEnd;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Time lunchStart;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Time lunchEnd;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Time dinnerStart;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Time dinnerEnd;

    private Integer tpUpdate;
}
