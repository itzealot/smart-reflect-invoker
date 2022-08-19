package com.wunong.smart.invoker.core.impl;

import com.wunong.smart.invoker.core.param.InvokeParam;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Jdk反射调用
 *
 * @author create by zealot.zt
 */
public class DefaultReflectInvoker extends AbstractReflectInvoker {

    public DefaultReflectInvoker() {
        super();
    }

    public DefaultReflectInvoker(boolean usingPreciseMatch) {
        super(usingPreciseMatch);
    }

    @Override
    public Object doInvoke(InvokeParam param) {
        Class<?> clazz;

        try {
            clazz = Class.forName(param.getClassName());
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("can't find class " + param.getClassName(), e);
        }

        // 选择方法
        Method method = selectMethodNonNull(clazz, param);

        // 获取参数
        Object[] args = resolveInvokeArgs(method, param);

        // 获取调用实例
        Object instance = newInstance(clazz, method);

        // 反射调用
        return invoke(method, instance, args);
    }

    /**
     * 反射获取实例信息
     *
     * @param clazz
     * @param method
     * @return
     */
    private Object newInstance(Class<?> clazz, Method method) {
        if (Modifier.isStatic(method.getModifiers())) {
            return null;
        }
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("can't new instance for " + clazz, e);
        }
    }

}
