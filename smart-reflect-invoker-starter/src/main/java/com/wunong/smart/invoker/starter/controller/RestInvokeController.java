package com.wunong.smart.invoker.starter.controller;

import com.wunong.smart.invoker.core.impl.DefaultReflectInvoker;
import com.wunong.smart.invoker.core.impl.SpringReflectInvoker;
import com.wunong.smart.invoker.starter.InvokeAuthentication;
import com.wunong.smart.invoker.starter.RestInvokeConfiguration;
import com.wunong.smart.invoker.starter.model.AuthInvokeParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Rest后门调用
 *
 * @author create by zealot.zt
 */
@Slf4j
@RestController
@RequestMapping(RestInvokeConfiguration.BACKDOOR)
public class RestInvokeController {

    @Resource
    private SpringReflectInvoker springReflectInvoker;

    @Resource
    private DefaultReflectInvoker defaultReflectInvoker;

    @Resource
    private InvokeAuthentication invokeAuthentication;

    @PostMapping("/spring.invoke")
    public Object invokeSpring(@RequestBody AuthInvokeParam param) {
        invokeAuthentication.auth(param);

        return springReflectInvoker.invoke(param);
    }

    @PostMapping("/default.invoke")
    public Object invoke(@RequestBody AuthInvokeParam param) {
        invokeAuthentication.auth(param);

        return defaultReflectInvoker.invoke(param);
    }

}
