package com.wunong.smart.invoker.core.support;

import com.wunong.smart.invoker.core.api.MethodFilter;
import com.wunong.smart.invoker.core.param.InvokeParam;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 调用方法过滤器
 *
 * @author create by zealot.zt
 */
public class InvokeMethodFilter implements MethodFilter {

    /**
     * 调用参数
     */
    private final InvokeParam param;

    /**
     * 是否精确匹配
     */
    private final boolean preciseMatch;

    public InvokeMethodFilter(InvokeParam param) {
        this(param, true);
    }

    public InvokeMethodFilter(InvokeParam param, boolean preciseMatch) {
        this.param = param;
        this.preciseMatch = preciseMatch;
    }

    @Override
    public Method filter(Method[] methods) {
        if (methods == null || methods.length == 0) {
            return null;
        }

        // 按照方法名称过滤
        List<Method> ms = Stream.of(methods)
                .filter(method -> method.getName().equals(param.getMethodName()))
                .collect(Collectors.toList());

        return findMethod(ms, param);
    }

    /**
     * 查找对应方法
     *
     * @param methods
     * @param param
     * @return
     */
    protected Method findMethod(List<Method> methods, InvokeParam param) {
        // 方法类型，精确匹配
        for (Method method : methods) {
            // 精确匹配
            if (preciseMatch) {
                if (isPreciseMatch(method, param)) {
                    return method;
                }
            } else {
                if (isMatch(method, param)) {
                    return method;
                }
            }
        }

        return null;
    }

    /**
     * 方法对应参数类型是否匹配
     *
     * @param method
     * @param param
     * @return
     */
    private boolean isMatch(Method method, InvokeParam param) {
        List<String> paramTypes = param.getParamTypes();

        if (CollectionUtils.isNotEmpty(paramTypes)) {
            for (Class<?> clazz : method.getParameterTypes()) {
                // 若当前参数列表类型不在输入的参数中，直接过滤
                if (!paramTypes.contains(clazz.getSimpleName())) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 方法对应参数类型是否匹配
     *
     * @param method
     * @param param
     * @return
     */
    private boolean isPreciseMatch(Method method, InvokeParam param) {
        List<String> paramTypes = param.getParamTypes();
        int size = paramTypes.size();

        // 参数数量&对应传入参数数量
        if (method.getParameterCount() != size
                || size != param.getParams().size()) {
            return false;
        }

        return isMatch(method, param);
    }

}
