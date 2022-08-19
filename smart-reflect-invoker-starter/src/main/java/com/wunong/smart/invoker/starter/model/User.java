package com.wunong.smart.invoker.starter.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author create by zealot.zt
 */
@Data
public class User implements Serializable {

    /**
     * 用户编号
     */
    private String id;

    /**
     * 角色
     */
    private Integer role;

    /**
     * 用户名称
     */
    private String name;

}
