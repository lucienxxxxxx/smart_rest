package com.pitaya.smart_rest.config;


import com.pitaya.smart_rest.interceptor.NoLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @ClassName MvcConfig
 * @author: lucine
 * @Description TODO
 * @date 2021/12/8 23:46
 * @Version 1.0版本
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Bean // 将方法的返回值交给IOC维护
    public NoLoginInterceptor noLoginInterceptor() {
        return new NoLoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 需要一个实现了拦截器功能的实例对象，这里使用的是noLoginInterceptor
        registry.addInterceptor(noLoginInterceptor())
                // 设置需要被拦截的资源
                .addPathPatterns("/**") // 默认拦截所有的资源
                // 设置不需要被拦截的资源   页面资源文件     登录接口   登录页面
                .excludePathPatterns("/assets/**", "/toLogin","/login");
    }
}
