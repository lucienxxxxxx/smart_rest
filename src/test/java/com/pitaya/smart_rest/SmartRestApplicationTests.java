package com.pitaya.smart_rest;

import com.pitaya.smart_rest.system.mapper.RoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SmartRestApplicationTests {
    @Resource
    private RoleMapper roleMapper;

    @Test
    void contextLoads() {
        System.out.println(roleMapper.queryAllRoles(4));
    }

}
