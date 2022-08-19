package com.wunong.smart.invoker.starter.support;

import com.wunong.smart.invoker.starter.InvokeAuthentication;
import com.wunong.smart.invoker.starter.model.AuthInvokeParam;
import com.wunong.smart.invoker.starter.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.Objects;

/**
 * @author create by zealot.zt
 */
public class DefaultInvokeAuthentication implements InvokeAuthentication {

    /**
     * 环境变量
     */
    @Value("${spring.profiles.active:default}")
    private String env;

    /**
     * 是否开启生产环境
     */
    @Value("${app.backdoor.prd.enable:false}")
    private boolean enablePrd;

    @Override
    public void auth(AuthInvokeParam param) {
        User user = param.getUser();

        if (Objects.isNull(user) || StringUtils.isBlank(user.getId())) {
            throw new IllegalArgumentException("未知请求");
        }

        if (!isEnableInvoker()) {
            throw new IllegalArgumentException("未知请求");
        }
    }

    /**
     * 是否开启后门调用
     *
     * @return
     */
    public boolean isEnableInvoker() {
        return !"prd".equalsIgnoreCase(env) || isEnablePrd();
    }

    /**
     * 是否开启prd
     *
     * @return
     */
    protected boolean isEnablePrd() {
        return enablePrd;
    }

}
