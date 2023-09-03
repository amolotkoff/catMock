package com.amolotkoff.mocker.parser.service;

import com.amolotkoff.mocker.file.FileUtil;
import com.amolotkoff.mocker.parser.exceptions.NotMapException;
import com.amolotkoff.mocker.parser.model.http.HttpControllerModel;
import com.amolotkoff.mocker.parser.service.async.AsyncParser;
import com.amolotkoff.mocker.parser.service.async.AsyncProxyParser;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.UUID;

import java.nio.file.Paths;
import java.util.HashMap;

public class HTTPParser {
    private final Logger logger;
    private final Object map;
    private final String path;

    public HTTPParser(Object map, String path) {
        this.logger = LogManager.getLogger();
        this.map = map;
        this.path = path;
    }

    public HttpControllerModel Parse() throws Exception {
        logger.info("\tparse http...");

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