package com.webb.smart.demo.controller;

import com.webb.smart.demo.service.HelloService;
import com.webb.smart.util.BeanUtil;
import com.webb.smart.util.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Created by webb on 17-4-10.
 */
public class Log4jDemo {

    private static final Logger logger = LoggerFactory.getLogger(Log4jDemo.class);

    public static void main(String[] args) {
//        logger.debug("test");
//        logger.info("test {} : {}", 1, 2);

        /*HelloService helloService = (HelloService) ReflectUtil.newInstance(HelloService.class);

        helloService.helloService();*/

        /*BeanUtil.getBeanMap();

        Field[] fields = HelloController.class.getDeclaredFields();

        HelloController helloController = (HelloController) ReflectUtil.newInstance(HelloController.class);

        for (Field field : fields) {
            ReflectUtil.setField(helloController, field, ReflectUtil.newInstance(HelloService.class));
        }

        helloController.helloService("aa");
*/
    }
}
