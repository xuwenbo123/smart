package com.webb.smart.util;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.webb.smart.mvc.annotation.Inject;

/**
 * 依赖注入工具类
 *
 * Created by webb on 17-4-10.
 */
public class IocUtil {
    static {
        Map<Class<?>, Object> beanMap = BeanUtil.getBeanMap(); // 获取bean类与Bean实例的映射关系

        if (MapUtils.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                Field[] beanFields = beanClass.getDeclaredFields(); // 获取Bean类的所有成员变量
                if (ArrayUtils.isNotEmpty(beanFields)) {
                    for (Field beanField : beanFields) {
                        if (beanField.isAnnotationPresent(Inject.class)) { // 当前成员变量是否带有Inject注解
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
}
