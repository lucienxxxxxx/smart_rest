package com.pitaya.smart_rest.system.model;

import lombok.Data;

import java.util.List;

/**
 * @ClassName OrgModel
 * @author: lucine
 * @Description 查询所有机构返回类
 * @date 2022/4/12 21:59
 * @Version 1.0版本
 */
@Data
public class OrgModel {
    private Integer value;
    private String label;
    private List<OrgModel> children;
}
