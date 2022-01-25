package com.pitaya.smart_rest.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MybatisPlusConfig
 * @author: lucine
 * @Description mybatisPlus配置
 * @date 2021/12/8 23:47
 * @Version 1.0版本
 */
@Configuration
@MapperScan("com.pitaya.smart_rest.*.mapper*")
public class MybatisPlusConfig {
    // 最新版 分页设置
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        return interceptor;
    }
}
