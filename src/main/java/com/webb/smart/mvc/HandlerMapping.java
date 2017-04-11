package com.webb.smart.mvc;

import com.webb.smart.mvc.annotation.RequestMethod;
import com.webb.smart.mvc.bean.HandlerBody;
import com.webb.smart.mvc.bean.RequestBody;

import java.util.Map;

/**
 * 映射处理
 * Created by webb on 17-4-9.
 */
public class HandlerMapping {

    public static HandlerBody getHandler(String requestMethod, String requestPath) {
        // Controller Map 请求 -> 方法体 的映射
        Map<RequestBody, HandlerBody> methodMap = ControllerCollection.getMethodMap();
        for (Map.Entry<RequestBody, HandlerBody> methodEntry : methodMap.entrySet()) {
            RequestBody request = methodEntry.getKey();
            String reqPath = request.getRequestPath();
            RequestMethod reqMethod = request.getRequestMethod();
            if (reqPath.equals(requestPath) && reqMethod.name().equalsIgnoreCase(requestMethod)) {
                HandlerBody handler = methodEntry.getValue();
                if (handler != null) {
                    return handler;
                }
            }
        }
        return null;
    }
}
