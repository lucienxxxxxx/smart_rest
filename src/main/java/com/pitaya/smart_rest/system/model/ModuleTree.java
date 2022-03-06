package com.pitaya.smart_rest.system.model;

import lombok.Data;

import java.util.List;

/**
 * @ClassName ModuleTree
 * @author: lucine
 * @Description TODO
 * @date 2022/3/6 10:31
 * @Version 1.0版本
 */
@Data
public class ModuleTree {
    private Integer id;
    private Integer pid;
    private String orders;
    private String grade;
    private String name;
    private String url;
    private String icon;
    private List<ModuleTree> subMenus;
}
