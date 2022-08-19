package com.wunong.smart.invoker.starter;

import com.wunong.smart.invoker.starter.model.AuthInvokeParam;

/**
 * 认证调用
 *
 * @author create by zealot.zt
 */
public interface InvokeAuthentication {

    /**
     * 认证，认证不通过，可以直接返回获取抛出异常
     *
     * @param param
     */
    void auth(AuthInvokeParam param) throws RuntimeException;

}
