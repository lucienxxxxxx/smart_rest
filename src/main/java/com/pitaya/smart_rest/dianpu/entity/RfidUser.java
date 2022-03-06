package com.pitaya.smart_rest.dianpu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * RFID卡(用户和管理卡)
 * </p>
 *
 * @author lucien
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_rfid_user")
public class RfidUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value="id" , type = IdType.AUTO)
    private Integer id;
    private String logicName;

    /**
     * 0=停用 1=启用
     */
    private Integer stateId;

    /**
     * 0 线下卡（可绑定托盘卡）1管理卡
     */
    private Integer typeId;

    private Integer memberId;

    private String descriptions;

    private Integer resId;


}
