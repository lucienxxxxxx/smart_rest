package com.pitaya.smart_rest.stats.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @ClassName MemberStats
 * @author: lucine
 * @Description TODO
 * @date 2022/4/14 0:07
 * @Version 1.0版本
 */
@Data
public class MemberStats {
    @TableId
    private Integer id;
    private String totalLevel;
    private Integer counts;
}
