package com.amolotkoff.parser.service;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.bind.annotation.RequestMethod;

public class RequestMethodParser {
    private final Logger logger;
    private final Object map;

    public RequestMethodParser(Object map) {
        this.logger = LogManager.getRootLogger();
        this.map = map;
    }

    public RequestMethod Parse() throws Exception {
        logger.info("\tparse request-method type...");

        Object method = Util.get(map, "method");
        RequestMethod requestMethod = null;

        if (method == null) {
            logger.warn("\tNo found request-method type, substitute GET");
            return RequestMethod.GET;
        }

        try {
            requestMethod = RequestMethod.valueOf(method.toString().toUpperCase());
            return requestMethod;
        }
        catch (IllegalArgumentException e) {
            throw new Exception(e.getMessage());
        }
    }
}