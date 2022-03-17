package com.pitaya.smart_rest.activity.vo;

import com.pitaya.smart_rest.activity.model.ChargeModel;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ChargeVo
 * @author: lucine
 * @Description TODO
 * @date 2022/3/15 16:48
 * @Version 1.0版本
 */
@Data
public class ChargeVo {
    private List<Map<String, String>> chargeModels;
}
