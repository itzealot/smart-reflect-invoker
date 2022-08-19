package com.wunong.smart.invoker.core.support;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 方法参数名解析辅助类
 *
 * @author create by zealot.zt
 */
public class ParameterNameDiscoverHelper {

    private final ParameterNameDiscoverer parameterNameDiscoverer;

    public ParameterNameDiscoverHelper() {
        this(new DefaultParameterNameDiscoverer());
    }

    public ParameterNameDiscoverHelper(ParameterNameDiscoverer discoverer) {
        this.parameterNameDiscoverer = discoverer;
    }

    /**
     * 解析方法的参数名
     *
     * @param method
     * @return
     */
    public String[] getParameterNames(Method method) {
        return parameterNameDiscoverer.getParameterNames(method);
    }

    /**
     * 解析构造函数的参数名
     *
     * @param ctor
     * @return
     */
    public String[] getParameterNames(Constructor<?> ctor) {
        return parameterNameDiscoverer.getParameterNames(ctor);
    }

}
