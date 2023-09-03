package com.amolotkoff.mocker.parser.service;

import com.amolotkoff.mocker.file.FileUtil;
import com.amolotkoff.mocker.parser.exceptions.NotMapException;
import com.amolotkoff.mocker.parser.model.http.HttpControllerModel;
import com.amolotkoff.mocker.parser.service.async.AsyncParser;
import com.amolotkoff.mocker.parser.service.async.AsyncProxyParser;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import java.nio.file.Paths;
import java.util.HashMap;

@Slf4j
public class HTTPParser {

    private final Object map;
    private final String path;

    public HTTPParser(Object map, String path) {
        this.map = map;
        this.path = path;
    }

    public HttpControllerModel Parse() throws Exception {
        log.info("\tparse http...");

        if (!Util.isMap(map))
            throw new NotMapException();

        HashMap<String, Object> httpMap = Util.get(map, "http", true, null);
        String parentPath = Paths.get(path).getParent().toString();

        return new HttpControllerModel(generateName(path, httpMap),
                                       new ContextParser(httpMap, parentPath).Parse(),
                                       new APIsParser(httpMap, parentPath).Parse(),
                                       new ScriptParser(httpMap).Parse(),
                                       new ImportParser(httpMap).Parse());
    }

    private String generateName(String path, HashMap<String, Object> httpMap) throws Exception {
        if (Util.has(httpMap, "name"))
            return Util.get(httpMap, "name");

        return FileUtil.GetBaseFileName(path, true);
    }
}