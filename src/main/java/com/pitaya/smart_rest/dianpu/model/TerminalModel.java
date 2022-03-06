package com.pitaya.smart_rest.dianpu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName TerminalModel
 * @author: lucine
 * @Description TODO
 * @date 2022/2/12 9:23
 * @Version 1.0版本
 */
@Data
public class TerminalModel {
    @TableId(value = "id", type = IdType.AUTO)
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

    private String descriptions;

    private String foodName;
}
