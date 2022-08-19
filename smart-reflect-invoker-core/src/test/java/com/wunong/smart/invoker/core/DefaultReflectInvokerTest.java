package com.wunong.smart.invoker.core;

import com.wunong.smart.invoker.core.impl.DefaultReflectInvoker;
import com.wunong.smart.invoker.core.param.InvokeParam;
import com.wunong.smart.invoker.core.support.DefaultMethodSelector;
import com.wunong.smart.invoker.core.support.ParameterNameDiscoverHelper;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author create by zealot.zt
 */
public class DefaultReflectInvokerTest {

    private DefaultReflectInvoker invoker = new DefaultReflectInvoker();

    @Before
    public void before() {
        invoker.setMethodSelector(new DefaultMethodSelector());
        invoker.setParameterNameDiscoverHelper(new ParameterNameDiscoverHelper());
    }

    @Test
    public void testHelloEmpty() {
        InvokeParam param = InvokeParam.builder()
                .className(MethodHelper.class.getName())
                .methodName("hello")
                .build();

        invoker.invoke(param);
    }

    @Test
    public void testStaticHello() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "11111");

        invoker.invoke(InvokeParam.builder()
                .className(MethodHelper.class.getName())
                .methodName("staticHello")
                .paramTypes(Arrays.asList("String"))
                .params(map)
                .build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHelloStringException() {
        Map<String, Object> map = new HashMap<>();
        map.put("message1", "value");
        map.put("message2", "value");

        InvokeParam param = InvokeParam.builder()
                .className(MethodHelper.class.getName())
                .methodName("hello")
                .paramTypes(Arrays.asList("String"))
                .params(map)
                .build();

        invoker.invoke(param);
    }

    @Test
    public void testHelloStringNull() {
        Map<String, Object> map = new HashMap<>();
        map.put("message1", "value");

        InvokeParam param = InvokeParam.builder()
                .className(MethodHelper.class.getName())
                .methodName("hello")
                .paramTypes(Arrays.asList("String"))
                .params(map)
                .build();

        invoker.invoke(param);
    }

    @Test
    public void testHelloStringMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "value");

        InvokeParam param = InvokeParam.builder()
                .className(MethodHelper.class.getName())
                .methodName("hello")
                .paramTypes(Arrays.asList("String"))
                .params(map)
                .build();

        invoker.invoke(param);
    }

}
