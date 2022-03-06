package com.pitaya.smart_rest.dianpu.entity;

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
 * 终端或者取餐台
 * </p>
 *
 * @author lucien
 * @since 2022-02-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_terminal")
public class Terminal implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value="id" , type = IdType.AUTO)
    private Integer id;
    private String terminalName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date settingDate;
    /**
     * 0=邦定机 1=取餐机
     */
    private Integer type;
    /**
     * 0=启用 1=停用
     */
    private Integer state;

    /**
     * 描述
     */
    private String descriptions;

    private Integer foodId;

    private Integer restaurantId;


}
