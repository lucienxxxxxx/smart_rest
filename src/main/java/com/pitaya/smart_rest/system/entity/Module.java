package com.pitaya.smart_rest.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 权限模块
 * </p>
 *
 * @author lucien
 * @since 2022-01-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_module")
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value="id" , type = IdType.AUTO)
    private Integer id;
    /**
     * 资源名
     */
    private String moduleName;

    /**
     * 资源样式
     */
    private String moduleStyle;

    /**
     * 资源url地址
     */
    private String url;

    /**
     * 上级资源id
     */
    private Integer parentId;

    /**
     * 层级
     */
    private Integer grade;

    /**
     * 0=启动 1=停用
     */
    private Integer state;

    /**
     * 上级资源权限码
     */
    private String parentOptValue;

    /**
     * 权限码
     */
    private String optValue;

    /**
     * 排序号
     */
    private Integer orders;

    /**
     * 有效状态
     */
    private Integer isValid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;


    /**
     * 描述
     */
    private String descriptions;


}
