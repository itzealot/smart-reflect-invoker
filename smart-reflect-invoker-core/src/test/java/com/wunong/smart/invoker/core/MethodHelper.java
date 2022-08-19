package com.wunong.smart.invoker.core;

/**
 * @author create by zealot.zt
 */
public class MethodHelper {

    public void hello() {
        System.out.println("hello");
    }

    public void hello(String message) {
        System.out.println("hello String:" + message);
    }

    public static void staticHello(String message) {
        System.out.println("static hello String:" + message);
    }

}
