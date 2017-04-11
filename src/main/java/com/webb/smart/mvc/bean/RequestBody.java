package com.webb.smart.mvc.bean;

import com.webb.smart.mvc.annotation.RequestMethod;

/**
 * 请求体
 */
public class RequestBody {

    /**
     * 请求方法 [POST] [GET]
     */
    private RequestMethod requestMethod;

    /**
     * 请求路由
     */
    private String requestPath;

    public RequestBody(RequestMethod requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }
}
