package com.pitaya.smart_rest.activity.entity;

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
 * 会员活动表
 * </p>
 *
 * @author lucien
 * @since 2022-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_member_activity")
public class MemberActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value="id" , type = IdType.AUTO)
    private Integer id;

    private Integer memberId;

    private Integer chargeActivityId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date useDate;

    /**
     * 是否已经核销：0=未核销 1=核销
     */
    private Integer isUse;


}
