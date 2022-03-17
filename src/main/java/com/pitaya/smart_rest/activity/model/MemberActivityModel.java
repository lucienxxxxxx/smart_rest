package com.pitaya.smart_rest.activity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName MemberActivityModel
 * @author: lucine
 * @Description TODO
 * @date 2022/3/14 14:29
 * @Version 1.0版本
 */
@Data
public class MemberActivityModel {

    private Integer id;

    private Integer memberId;

    private Integer chargeActivityId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date useDate;

    /**
     * 是否已经核销：0=未核销 1=核销
     */
    private Integer isUse;

    private String mobile;

    private String note;

    private String trueName;

    private String activityName;
}
