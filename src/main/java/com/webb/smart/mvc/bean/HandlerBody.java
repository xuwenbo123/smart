package com.webb.smart.mvc.bean;

import java.lang.reflect.Method;

/**
 * 处理方法体
 */
public class HandlerBody {

    private Class<?> controllerClass;

    private Method controllerMethod;

    private String responseMediaType;

    public HandlerBody(Class<?> controllerClass, Method controllerMethod, String responseMediaType) {
        this.controllerClass = controllerClass;
        this.controllerMethod = controllerMethod;
        this.responseMediaType = responseMediaType;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getControllerMethod() {
        return controllerMethod;
    }

    public String getResponseMediaType() {
        return responseMediaType;
    }

}
