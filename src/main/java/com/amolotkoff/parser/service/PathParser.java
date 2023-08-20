package com.amolotkoff.parser.service;

import com.amolotkoff.parser.exceptions.KeyNotFoundException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class PathParser {
    private final Logger logger;
    private final Object map;

    public PathParser(Object map) {
        this.logger = LogManager.getRootLogger();
        this.map = map;
    }

    public String Parse() throws Exception {
        if (!Util.has(map, "path"))
            throw new KeyNotFoundException();

        String path = Util.get(map, "path");

        return path;
    }
}