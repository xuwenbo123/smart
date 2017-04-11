package com.webb.smart.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by webb on 17-4-9.
 */
public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * 将Java对象转为JSON字符串
     */
    public static <T> String toJSON(T obj) {
        String jsonStr;
        try {
            jsonStr = JSONObject.toJSONString(obj);
        } catch (Exception e) {
            logger.error("Java 转 Json 出错!", e);
            throw new RuntimeException(e);
        }
        return jsonStr;
    }

    /**
     * 将JSON字符串转为Java对象
     */
    public static Object fromJSON(String json) {
        Object jsonObject;
        try {
            jsonObject = JSONObject.toJSON(json);
        } catch (Exception e) {
            logger.error("Json 转 Java 出错!", e);
            throw new RuntimeException(e);
        }
        return jsonObject;
    }
}
