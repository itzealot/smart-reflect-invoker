package com.wunong.smart.invoker.core.api;

import java.lang.reflect.Method;

/**
 * 方法选择器
 *
 * @author create by zealot.zt
 */
public interface MethodSelector {

    /**
     * 根据调用参数与方法列表选择参数
     *
     * @param clazz  Class
     * @param filter 方法过滤器
     * @return
     */
    Method select(Class<?> clazz, MethodFilter filter);

}
