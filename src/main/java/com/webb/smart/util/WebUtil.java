package com.webb.smart.util;

import com.webb.smart.mvc.annotation.MediaTypes;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by webb on 17-4-9.
 */
public class WebUtil {
    private static final String CHARSET_UTF_8 = "UTF-8";

    /**
     * 从请求中获取所有参数
     */
    public static List<Object> getRequestParamMap(HttpServletRequest request, Class<?>[] controllerParamTypes) {
        List<Object> requestParamList = new ArrayList<>();
        Enumeration<String> paramNames = request.getParameterNames();
        int i = 0;
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues != null) {
                if (1 == paramValues.length) {
                    String paramValue = paramValues[0];
                    Class<?> paramType = controllerParamTypes[i];
                    if (paramType.equals(int.class) || paramType.equals(Integer.class)) {
                        requestParamList.add(Integer.parseInt(paramValue));
                    } else if (paramType.equals(long.class) || paramType.equals(Long.class)) {
                        requestParamList.add(Long.parseLong(paramValue));
                    } else if (paramType.equals(double.class) || paramType.equals(Double.class)) {
                        requestParamList.add(Double.parseDouble(paramValue));
                    } else if (paramType.equals(String.class)) {
                        requestParamList.add(paramValue);
                    }
                }
                // TODO: 17-4-9 数组
            }
            i++;
        }
        return requestParamList;
    }

    /**
     * 从请求中获取对应的对象
     */
    public static Object getRequestBody(HttpServletRequest request, Class<?> clazz) {
        InputStream inputStream;
        String tempStr;
        Object result = null;
        try {
            inputStream = request.getInputStream();
            if (request.getContentType().equals(MediaTypes.TEXT_PLAIN)) {
                result = IOUtils.toString(inputStream);
            } else if (request.getContentType().equals(MediaTypes.JSON_UTF_8)) {
                tempStr = IOUtils.toString(inputStream, String.valueOf(Charset.forName(CHARSET_UTF_8)));
                result = JsonUtil.fromJSON(tempStr);
            } else if (request.getContentType().equals(MediaTypes.JSON)) {
                tempStr = IOUtils.toString(inputStream);
                result = JsonUtil.fromJSON(tempStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
