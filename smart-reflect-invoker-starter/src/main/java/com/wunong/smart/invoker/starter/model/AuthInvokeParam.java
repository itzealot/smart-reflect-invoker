package com.wunong.smart.invoker.starter.model;

import com.wunong.smart.invoker.core.param.InvokeParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author create by zealot.zt
 */
@Getter
@Setter
@ToString(callSuper = true)
public class AuthInvokeParam extends InvokeParam {

    /**
     * 操作人
     */
    private User user;

}
