package com.webb.smart.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 反射工具类
 *
 * Created by webb on 17-4-9.
 */
public class ReflectUtil {

    private static final Logger logger = LoggerFactory.getLogger(ReflectUtil.class);

    /**
     * 创建实例
     */
    public static Object newInstance(Class<?> cls) {
        Object instance;
        try {
            instance = cls.newInstance();
        } catch (Exception e) {
            logger.error("new instance fail");
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 调用方法
     */
    public static Object invokeMethod(Object obj, Method method, Object... args) {
        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(obj, args);
        } catch (Exception e) {
            logger.error("invoke fail");
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 设置成员变量值
     */
    public static void setField(Object obj, Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(obj, value);
        } catch (Exception e) {
            logger.error("set field fail");
            throw new RuntimeException(e);
        }
    }

    public static Object invokeControllerMethod(Class<?> clazz, Method method, List<Object> paramList) {
        try {
            method.setAccessible(true);
            Object instance = clazz.newInstance();
            return method.invoke(instance, paramList.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
