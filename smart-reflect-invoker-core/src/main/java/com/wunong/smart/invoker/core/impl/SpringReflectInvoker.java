package com.wunong.smart.invoker.core.impl;

import com.wunong.smart.invoker.core.param.InvokeParam;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Method;

/**
 * Spring反射调用实现
 *
 * @author create by zealot.zt
 */
public class SpringReflectInvoker extends AbstractReflectInvoker {

    /**
     * 反射调用，并返回结果
     *
     * @param param
     * @return
     */
    @Override
    public Object doInvoke(InvokeParam param) {
        if (!context.containsBean(param.getClassName())) {
            throw new IllegalArgumentException("can't find bean name " + param.getClassName());
        }

        // 获取Bean
        Object beanProxy = context.getBean(param.getClassName());

        // 根据参数选择方法
        Method method = selectMethodNonNull(beanProxy.getClass(), param);

        // 获取代理对象对应的目标对象
        Class<?> target = AopUtils.getTargetClass(beanProxy);

        // 根据目标对象获取实际的方法，后续可以获取对应参数列表
        Method targetMethod = selectMethodNonNull(target, param);
        Object[] args = resolveInvokeArgs(targetMethod, param);

        return invoke(method, beanProxy, args);
    }

}
