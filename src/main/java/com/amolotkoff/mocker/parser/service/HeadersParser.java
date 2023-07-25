package com.amolotkoff.mocker.parser.service;

import com.amolotkoff.mocker.parser.exceptions.NotMapException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.HashMap;
import java.util.Map;

public class HeadersParser {
    private final Logger logger;
    private final Object map;

    public HeadersParser(Object map) {
        this.logger = LogManager.getRootLogger();
        this.map = map;
    }

    public HashMap<String, String> Parse() throws Exception {
        logger.info("\tparse headers...");
        HashMap<String, String> header_map = Util.get(map, "headers");
        HashMap<String, String> headers = new HashMap<>();

        if (header_map == null)
            return headers;

        for (Map.Entry<String, String> entry : header_map.entrySet())
            headers.put(entry.getKey(), entry.getValue());

        return headers;
    }
}