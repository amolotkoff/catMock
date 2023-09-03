package com.amolotkoff.mocker.parser.service;

import com.amolotkoff.mocker.parser.exceptions.KeyNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PathParser {
    private final Object map;

    public PathParser(Object map) {
        this.map = map;
    }

    public String Parse() throws Exception {
        if (!Util.has(map, "path"))
            throw new KeyNotFoundException();

        String path = Util.get(map, "path");

        return path;
    }
}