package com.webb.smart.mvc;

import com.webb.smart.mvc.annotation.Controller;
import com.webb.smart.mvc.annotation.RequestMapping;
import com.webb.smart.mvc.annotation.RequestMethod;
import com.webb.smart.mvc.bean.HandlerBody;
import com.webb.smart.mvc.bean.RequestBody;
import com.webb.smart.util.ClassUtil;
import com.webb.smart.util.Config;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller类收集器
 *
 * Created by webb on 17-4-9.
 */
public class ControllerCollection {

    /**
     * Controller Map 请求 -> 方法体的映射
     */
    private static final Map<RequestBody, HandlerBody> methodMap = new HashMap<>();

    /**
     * Controller扫描包的目录
     */
    private static final String scanPackage = Config.getScanPackage();

    /**
     * 初始化
     */
    public static void init() {
        // 获取到Controller注解的类列表
        List<Class<?>> controllerClassList = ClassUtil.getClassListByAnnotation(scanPackage, Controller.class);
        if (CollectionUtils.isNotEmpty(controllerClassList)) {
            for (Class<?> controllerClass : controllerClassList) {
                Method[] controllerMethods = controllerClass.getMethods(); // 获取Controller类中的所有方法
                if (ArrayUtils.isNotEmpty(controllerMethods)) {
                    for (Method controllerMethod : controllerMethods) {
                        handlerControllerMethod(controllerMethod, controllerClass);
                    }
                }
            }
        }
    }

    /**
     * 根据Method的注解加入Controller Map 请求 -> 方法体的映射
     */
    private static void handlerControllerMethod(Method controllerMethod, Class<?> controllerClass) {
        if (controllerMethod.isAnnotationPresent(RequestMapping.class)) {
            String requestPath = controllerMethod.getAnnotation(RequestMapping.class).value();
            RequestMethod requestMethod = controllerMethod.getAnnotation(RequestMapping.class).method();
            String responseMediaType = controllerMethod.getAnnotation(RequestMapping.class).produces();
            methodMap.put(new RequestBody(requestMethod, requestPath), new HandlerBody(controllerClass, controllerMethod, responseMediaType));
        }
    }

    public static Map<RequestBody, HandlerBody> getMethodMap() {
        return methodMap;
    }
}
