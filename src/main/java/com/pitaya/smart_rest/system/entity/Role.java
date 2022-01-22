package com.pitaya.smart_rest.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
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
    private Integer id;
    private String roleName;

    private String roleRemake;

    private LocalDateTime createDate;

    /**
     * 0=正常 1=停用
     */
    private Integer state;

    private String descriptions;


}
