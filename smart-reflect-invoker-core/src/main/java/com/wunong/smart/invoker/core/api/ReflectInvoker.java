package com.wunong.smart.invoker.core.api;

import com.wunong.smart.invoker.core.param.InvokeParam;

/**
 * 反射调用器
 *
 * @author create by zealot.zt
 */
public interface ReflectInvoker {

    /**
     * 执行调用，若出错，则返回异常
     *
     * @param param 调用参数
     * @return 返回调用值
     */
    Object invoke(InvokeParam param);

}
