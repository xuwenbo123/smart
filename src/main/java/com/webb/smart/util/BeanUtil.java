package com.webb.smart.util;

import com.webb.smart.mvc.annotation.Inject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
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

        if (MapUtils.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                Field[] beanFields = beanClass.getDeclaredFields(); // 获取Bean类的所有成员变量
                if (ArrayUtils.isNotEmpty(beanFields)) {
                    for (Field beanField : beanFields) {
                        if (beanField.isAnnotationPresent(Inject.class)) {  // 当前成员变量是否带有Inject注解
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass); // 获取Bean实例
                            if (beanFieldInstance != null) {
                                // 通过反射初始化BeanField的值
                                ReflectUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
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
