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
 * 档口
 * </p>
 *
 * @author lucien
 * @since 2022-03-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_dangkou")
public class Dangkou implements Serializable {

    @TableId(value="id" , type = IdType.AUTO)
    private Integer id;

    private Integer resId;//餐厅id

    private String dangkouName;//档口名称

    private String descriptions;//备注

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;//创建日期


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;//修改日期

    /**
     * 0=正常 1=暂停 2=停用
     */
    private Integer state;//状态

}
