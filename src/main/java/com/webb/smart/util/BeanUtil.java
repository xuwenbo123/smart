package com.webb.smart.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bean助手类
 *
 * Created by webb on 17-4-10.
 */
public class BeanUtil {

    /**
     * 定义Bean映射
     */
    private static final Map<Class<?>, Object> beanMap = new HashMap<>();

    static {
        List<Class<?>> beanClassList = ClassUtil.getBeanClassList();
        for (Class<?> beanClass : beanClassList) {
            Object obj = ReflectUtil.newInstance(beanClass);
            beanMap.put(beanClass, obj);
        }
    }

    /**
     * 获取Bean映射
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return beanMap;
    }

    /**
     * 获取Bean实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls) {
        if (!beanMap.containsKey(cls)) {
            throw new RuntimeException("can't get bean by class" + cls);
        }
        return (T) beanMap.get(cls);
    }
}
