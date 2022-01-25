package com.pitaya.smart_rest.system.entity;

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
 * 角色表
 * </p>
 *
 * @author lucien
 * @since 2022-01-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value="id" , type = IdType.AUTO)
    private Integer id;
    private String roleName;

    private String roleRemark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

    private Integer isValid;

    /**
     * 0=正常 1=停用
     */
    private Integer state;




}
