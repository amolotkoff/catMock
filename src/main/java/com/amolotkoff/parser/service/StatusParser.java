package com.amolotkoff.parser.service;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;

public class StatusParser {
    private final Logger logger;
    private final Object map;

    public StatusParser(Object map) {
        this.logger = LogManager.getRootLogger();
        this.map = map;
    }

    public HttpStatus Parse() throws Exception {
        logger.info("\tparse status...");

        try {
            Integer index = Util.get(map, "status");
            return HttpStatus.valueOf(index);
        }
        catch (Exception e) {
            throw e;
        }

    }
}