package com.webb.smart.util;

import java.lang.reflect.Field;
import java.util.Map;

import com.webb.smart.mvc.annotation.Inject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

/**
 * 依赖注入工具类
 *
 * Created by webb on 17-4-10.
 */
public class IocUtil {
    static {
        Map<Class<?>, Object> beanMap = BeanUtil.getBeanMap(); // 获取bean类与Bean实例的映射关系

    }
}
