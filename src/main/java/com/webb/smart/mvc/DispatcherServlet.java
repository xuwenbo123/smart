package com.webb.smart.mvc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webb.smart.mvc.bean.HandlerBody;
import com.webb.smart.util.BeanUtil;
import com.webb.smart.util.ClassUtil;
import com.webb.smart.util.IocUtil;
import com.webb.smart.util.MVCHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * 前端控制器
 *
 * 1. 初始化相关配置: 类扫描/路由匹配
 * 2. 转发请求到 HandlerMapping
 * 3. 反射调用Controller方法,并解耦
 * 4. 根据返回值,响应视图或数据
 *
 * Created by webb on 17-4-9.
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    /**
     * 初始化相关配置 扫描类 - 路由Map
     */
    @Override
    public void init() throws ServletException {
        ControllerCollection.init();
        ClassUtil.loadClass(BeanUtil.class.getName(), true);
        ClassUtil.loadClass(IocUtil.class.getName(), true);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(MVCHelper.UTF_8);
        String requestMethod = request.getMethod();
        String requestPath = MVCHelper.getRequestPath(request);

        logger.info("request : {} : {}", requestMethod, requestPath);

        // 将"/"请求重定向到默认首页
        if (MVCHelper.URL_PATH_SEPARATOR.equals(requestPath)) {
            MVCHelper.redirectRequest(MVCHelper.REQ_DEFAULT_HOME_PAGE, request, response);
            return;
        }

        HandlerBody handler = HandlerMapping.getHandler(requestMethod, requestPath);

        if (handler == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        HandlerInvoker.invokeHandler(request, response, handler);
    }
}
