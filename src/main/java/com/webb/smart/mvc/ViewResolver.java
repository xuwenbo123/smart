package com.webb.smart.mvc;

import com.webb.smart.mvc.annotation.MediaTypes;
import com.webb.smart.mvc.bean.HandlerBody;
import com.webb.smart.util.JsonUtil;
import com.webb.smart.util.MVCHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 视图解析器
 *
 * Created by webb on 17-4-9.
 */
public class ViewResolver {

    private static final Logger logger = LoggerFactory.getLogger(ViewResolver.class);

    public static void resolveView(HttpServletRequest request, HttpServletResponse response, Object controllerMethodResult, HandlerBody handler) {
        try {
            String requestPath = MVCHelper.getRequestPath(request);
            String responseMediaType = handler.getResponseMediaType();
            if (MediaTypes.TEXT_PLAIN_UTF_8.equals(responseMediaType)) {
                logger.info("{} : 返回格式CharSet = {}, 返回结果 = {}", requestPath, MediaTypes.TEXT_PLAIN_UTF_8, controllerMethodResult);
                response.getWriter().print(controllerMethodResult);
            } else if (MediaTypes.JSON_UTF_8.equals(responseMediaType)) {
                String resultJSON = JsonUtil.toJSON(controllerMethodResult);
                logger.info("{} : 返回格式CharSet = {}, 返回结果 = {}", requestPath, MediaTypes.JSON_UTF_8, resultJSON);
                response.getWriter().print(resultJSON);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
