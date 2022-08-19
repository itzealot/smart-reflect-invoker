package com.wunong.smart.invoker.core.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 调用参数
 *
 * @author create by zealot.zt
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvokeParam implements Serializable {

    /**
     * 类标识，如spring bean标识、全类名
     */
    private String className;

    /**
     * 对应的方法名
     */
    private String methodName;

    /**
     * 调用参数，支持json格式
     */
    private Map<String, Object> params;

    /**
     * 参数类型列表
     */
    private List<String> paramTypes;

    /**
     * 获取调用标识
     *
     * @return
     */
    public String getInvokeKey() {
        return className + "." + methodName;
    }

    /**
     * 获取参数Map
     *
     * @return
     */
    public Map<String, Object> getParams() {
        return params == null ? Collections.emptyMap() : params;
    }

    /**
     * 获取参数类型列表
     *
     * @return
     */
    public List<String> getParamTypes() {
        return paramTypes == null ? Collections.emptyList() : paramTypes;
    }

}
