package com.wunong.smart.invoker.core.impl;

import com.alibaba.fastjson.JSONObject;
import com.wunong.smart.invoker.core.api.MethodSelector;
import com.wunong.smart.invoker.core.api.ReflectInvoker;
import com.wunong.smart.invoker.core.param.InvokeParam;
import com.wunong.smart.invoker.core.support.InvokeMethodFilter;
import com.wunong.smart.invoker.core.support.ParameterNameDiscoverHelper;
import lombok.Setter;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Spring反射调用实现
 *
 * @author create by zealot.zt
 */
@Setter
public abstract class AbstractReflectInvoker implements ReflectInvoker, ApplicationContextAware {

    /**
     * 包装类
     */
    protected static final List<Class<?>> WRAP_CLASS = Arrays.asList(Boolean.class, Character.class, Byte.class,
            Short.class, Integer.class, Long.class, Float.class, Double.class, String.class, Object.class);

    /**
     * spring上下文对象
     */
    protected ApplicationContext context;

    @Resource
    protected ParameterNameDiscoverHelper parameterNameDiscoverHelper;

    @Resource
    protected MethodSelector methodSelector;

    /**
     * 是否精确匹配
     */
    @Setter
    protected boolean usingPreciseMatch = false;

    public AbstractReflectInvoker() {
        super();
    }

    public AbstractReflectInvoker(boolean usingPreciseMatch) {
        this.usingPreciseMatch = usingPreciseMatch;
    }

    @Override
    public final Object invoke(InvokeParam param) {
        if (StringUtils.isBlank(param.getClassName())) {
            throw new IllegalArgumentException("class name can't be empty");
        }

        if (StringUtils.isBlank(param.getMethodName())) {
            throw new IllegalArgumentException("method name can't be empty");
        }

        return doInvoke(param);
    }

    /**
     * 执行调用
     *
     * @param param
     * @return
     */
    protected abstract Object doInvoke(InvokeParam param);

    /**
     * 根据执行对应的方法以及参数解析出调用参数
     *
     * @param method 未被代理的方法
     * @param param  调用参数
     * @return
     */
    protected Object[] resolveInvokeArgs(Method method, InvokeParam param) {
        // 解析方法的参数
        String[] params = parameterNameDiscoverHelper.getParameterNames(method);

        // 没有参数，直接返回
        if (params == null || params.length == 0) {
            return null;
        }

        // 传入的参数值
        Map<String, Object> invokeParams = param.getParams();

        // 原有的参数类型
        Class<?>[] parameterTypes = method.getParameterTypes();

        int len = params.length;
        List<Object> objects = new ArrayList<>(len);

        // 获取当前方法的参数列表
        for (int i = 0; i < len; i++) {
            // 对应参数类型
            Class<?> parameterType = parameterTypes[i];
            // 参数名
            String paramName = params[i];

            // 若传入的调用参数没有对应参数名，直接返回null值
            Object paramValue;
            if (invokeParams.containsKey(paramName)) {
                paramValue = parseParameterValue(parameterType, invokeParams.get(paramName));
            } else {
                if (parameterType.isPrimitive()) {
                    throw new IllegalArgumentException("primitive value is required for param mame "
                            + paramName + " on " + param.getInvokeKey());
                }

                paramValue = null;
            }

            objects.add(paramValue);
        }

        if (CollectionUtils.isEmpty(objects)) {
            return null;
        }

        // 返回对应参数
        return objects.toArray(new Object[0]);
    }

    /**
     * 解析参数对应值
     *
     * @param parameterType
     * @param paramValue
     * @return
     */
    protected Object parseParameterValue(Class<?> parameterType, Object paramValue) {
        // 若没有传值，直接返回
        if (paramValue == null || "null".equals(paramValue)) {
            return null;
        }

        // 为原始数据类型或包装了下，直接转换
        if (parameterType.isPrimitive() || WRAP_CLASS.contains(parameterType)) {
            String value = paramValue.toString();
            // 转换当前值
            return ConvertUtils.convert(value, parameterType);
        } else {
            // json序列化与反序列化
            String json = JSONObject.toJSONString(paramValue);
            return JSONObject.parseObject(json, parameterType);
        }
    }

    /**
     * 根据调用参数与实例选择方法
     *
     * @param clazz
     * @param param
     * @return
     */
    protected Method selectMethodNonNull(Class<?> clazz, InvokeParam param) {
        Method m = methodSelector.select(clazz, new InvokeMethodFilter(param, isUsingPreciseMatch()));

        if (m == null) {
            throw new IllegalArgumentException("can't find method " + param.getMethodName()
                    + " on " + param.getClassName());
        }

        return m;
    }

    /**
     * 反射调用
     *
     * @param method
     * @param instance
     * @param args
     * @return
     */
    protected Object invoke(Method method, Object instance, Object[] args) throws RuntimeException {
        try {
            if (args == null) {
                return method.invoke(instance);
            }

            // 执行方法调用
            return method.invoke(instance, args);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof RuntimeException) {
                // 运行时异常，直接返回
                throw (RuntimeException) e.getCause();
            }

            // 其他引起的调用异常，直接抛出
            throw new IllegalArgumentException("invoke failed on " + getInvokeKey(method), e.getCause());
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("access failed on " + getInvokeKey(method), e.getCause());
        }
    }

    protected String getInvokeKey(Method method) {
        return method.getDeclaringClass().getSimpleName() + "." + method.getName();
    }

    /**
     * 是否使用精确匹配
     *
     * @return
     */
    protected boolean isUsingPreciseMatch() {
        return usingPreciseMatch;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

}
