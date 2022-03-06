package com.pitaya.smart_rest.base;


import lombok.Data;

@Data
public class BaseQuery {
    private Integer page=1;
    private Integer limit=20;


}
