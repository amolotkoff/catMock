package com.amolotkoff.mocker.parser.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
public class RequestMethodParser {
    private final Object map;

    public RequestMethodParser(Object map) {
        this.map = map;
    }

    public RequestMethod Parse() throws Exception {
        log.info("\tparse request-method type...");

        Object method = Util.get(map, "method");
        RequestMethod requestMethod = null;

        if (method == null) {
            log.warn("\tNo found request-method type, substitute GET");
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