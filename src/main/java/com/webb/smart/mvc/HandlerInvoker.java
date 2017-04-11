package com.webb.smart.mvc;

import com.webb.smart.mvc.annotation.PostParam;
import com.webb.smart.mvc.annotation.RequestMethod;
import com.webb.smart.mvc.bean.HandlerBody;
import com.webb.smart.util.BeanUtil;
import com.webb.smart.util.ReflectUtil;
import com.webb.smart.util.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 * Handler调用器
 *
 * Created by webb on 17-4-9.
 */
public class HandlerInvoker {

    /**
     * 请求处理器
     * 根据请求，获取请求参数
     * 调用Method，获取返回值
     * 调用View，返回响应
     */
    public static void invokeHandler(HttpServletRequest request, HttpServletResponse response, HandlerBody handler) {
        List<Object> controllerMethodParamList = null;
        Method controllerMethod = handler.getControllerMethod();
        if (request.getMethod().equals(RequestMethod.POST.toString())) {
            List<Class<?>> getParameterTypes = null;
            Class<?> postParamType = null;
            for (Parameter p : controllerMethod.getParameters()) {
                if (p.isAnnotationPresent(PostParam.class)) {
                    postParamType = p.getType();
                } else {
                    getParameterTypes.add(p.getType());
                }
            }
            controllerMethodParamList = WebUtil.getRequestParamMap(request, getParameterTypes.toArray(new Class<?>[0]));
            Object postParamObject = WebUtil.getRequestBody(request, postParamType);
            controllerMethodParamList.add(0, postParamObject);
        }
        else if (request.getMethod().equals(RequestMethod.GET.toString())) {
            controllerMethodParamList = WebUtil.getRequestParamMap(request, controllerMethod.getParameterTypes());
        }
//        Object controllerClass = ReflectUtil.newInstance(handler.getControllerClass()); // 这样子获取会有问题，因为这里是重新创建了一个类
        Object controllerClass = BeanUtil.getBean(handler.getControllerClass()); // 这里需要从BeanUtil中取，否则获取不到依赖注入的内容
        Object controllerMethodResult = ReflectUtil.invokeMethod(controllerClass, handler.getControllerMethod(), controllerMethodParamList.toArray());
        ViewResolver.resolveView(request, response, controllerMethodResult, handler);
    }
}
