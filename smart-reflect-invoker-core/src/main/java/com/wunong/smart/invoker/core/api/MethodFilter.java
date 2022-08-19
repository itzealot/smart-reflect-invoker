package com.wunong.smart.invoker.core.api;

import java.lang.reflect.Method;

/**
 * 方法过滤器
 *
 * @author create by zealot.zt
 */
public interface MethodFilter {

    /**
     * 过滤对应方法
     *
     * @param methods
     * @return
     */
    Method filter(Method[] methods);

}
