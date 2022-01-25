package com.pitaya.smart_rest.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色模块表
 * </p>
 *
 * @author lucien
 * @since 2022-01-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value="id" , type = IdType.AUTO)
    private Integer id;
    private Integer roleId;

    private Integer moduleId;

    private String aclValue;

    private Date createDate;

    private Date updateDate;


}
