package com.wunong.smart.invoker.core.support;

import com.wunong.smart.invoker.core.api.MethodFilter;
import com.wunong.smart.invoker.core.api.MethodSelector;

import java.lang.reflect.Method;

/**
 * 默认的方法选择器
 *
 * @author create by zealot.zt
 */
public class DefaultMethodSelector implements MethodSelector {

    @Override
    public Method select(Class<?> clazz, MethodFilter filter) {
        Method[] methods = clazz.getDeclaredMethods();
        if (methods.length == 0) {
            return null;
        }

        return filter.filter(methods);
    }

}
