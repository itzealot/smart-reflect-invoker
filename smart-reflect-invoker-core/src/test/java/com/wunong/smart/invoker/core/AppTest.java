package com.wunong.smart.invoker.core;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author create by zealot.zt on 2018/4/29
 */
public class AppTest extends TestCase {

    public AppTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    public void testApp() {
        assertTrue(true);
    }

}
