package com.wunong.smart.invoker.starter;

import com.wunong.smart.invoker.core.api.MethodSelector;
import com.wunong.smart.invoker.core.api.ReflectInvoker;
import com.wunong.smart.invoker.core.impl.DefaultReflectInvoker;
import com.wunong.smart.invoker.core.impl.SpringReflectInvoker;
import com.wunong.smart.invoker.core.support.DefaultMethodSelector;
import com.wunong.smart.invoker.core.support.ParameterNameDiscoverHelper;
import com.wunong.smart.invoker.starter.support.DefaultInvokeAuthentication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author create by zealot.zt
 */
@Configuration
public class RestInvokeConfiguration {

    /**
     * 包名称
     */
    public static final String PACKAGE = "com.wunong.smart.invoker";

    /**
     * 后门请求
     */
    public static final String BACKDOOR = "/backdoor";

    /**
     * spring后门请求
     */
    public static final String BACKDOOR_SPRING = BACKDOOR + "/spring.invoke";

    /**
     * 后门请求
     */
    public static final String BACKDOOR_DEFAULT = BACKDOOR + "/default.invoke";

    /**
     * 参数名称解析器
     *
     * @return
     */
    @Bean
    public ParameterNameDiscoverHelper defaultParameterNameDiscoverHelper() {
        return new ParameterNameDiscoverHelper();
    }

    /**
     * 方法选择器
     *
     * @return
     */
    @ConditionalOnMissingBean(MethodSelector.class)
    @Bean
    public MethodSelector defaultMethodSelector() {
        return new DefaultMethodSelector();
    }

    @Bean
    public ReflectInvoker springReflectInvoker() {
        return new SpringReflectInvoker();
    }

    @Bean
    public ReflectInvoker defaultReflectInvoker() {
        return new DefaultReflectInvoker();
    }

    @ConditionalOnMissingBean(InvokeAuthentication.class)
    @Bean
    public InvokeAuthentication defaultInvokeAuthentication() {
        return new DefaultInvokeAuthentication();
    }

}
