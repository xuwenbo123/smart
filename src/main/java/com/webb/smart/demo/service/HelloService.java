package com.webb.smart.demo.service;

import com.webb.smart.mvc.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by webb on 17-4-10.
 */
@Service
public class HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloService.class);

    public void helloService() {
        logger.info("hello service");
    }

}
