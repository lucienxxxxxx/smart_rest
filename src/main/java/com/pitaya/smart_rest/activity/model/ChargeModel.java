package com.pitaya.smart_rest.activity.model;

import lombok.Data;

/**
 * @ClassName ChargeModel
 * @author: lucine
 * @Description TODO
 * @date 2022/3/15 16:23
 * @Version 1.0版本
 */
@Data
public class ChargeModel {
    private Integer id;
    private String mobile;
    private String trueName;
    private Integer memberType;
    private Integer orgId;
    private Float virtualAcc;
    private Float giftAcc;
    private Float allowanceAcc;
    private Float cashAcc;
    private Float chargeAcc;
    private Integer state;
    private Float total;
    private String orgName="无组织";

}
