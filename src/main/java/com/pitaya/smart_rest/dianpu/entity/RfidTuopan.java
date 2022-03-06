package com.pitaya.smart_rest.dianpu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 托盘RFID卡
 * </p>
 *
 * @author lucien
 * @since 2022-02-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_rfid_tuopan")
public class RfidTuopan implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    /**
     * 字符串卡号
     */
    private String logicName;

    /**
     * 0=启用 1=停用
     */
    private Integer stateId;

    private Integer memberId;

    private String descriptions;

    private Date bangdingTime;

    /**
     * 默认是0，再次绑定同一ID后为1
     */
    private Integer delayedFlag;

    /**
     * 绑定的订单单号
     */
    private String orderId;

    /**
     * 餐厅id
     */
    private Integer resId;


}
