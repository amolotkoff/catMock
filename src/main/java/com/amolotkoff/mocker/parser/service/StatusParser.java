package com.amolotkoff.mocker.parser.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

@Slf4j
public class StatusParser {
    private final Object map;

    public StatusParser(Object map) {
        this.map = map;
    }

    public HttpStatus Parse() throws Exception {
        log.info("\tparse status...");

        try {
            Integer index = Util.get(map, "status");
            return HttpStatus.valueOf(index);
        }
        catch (Exception e) {
            throw e;
        }
    }
}