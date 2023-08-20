package com.amolotkoff.parser.service.async;

import com.amolotkoff.parser.model.api.AsyncApi;
import com.amolotkoff.parser.service.Util;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.yaml.snakeyaml.Yaml;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AsyncProxyParser {
    private final Logger logger;
    private final Object httpMap;
    private final String path;

    public AsyncProxyParser(Object httpMap, String path) {
        this.logger = LogManager.getRootLogger();
        this.httpMap = httpMap;
        this.path = path;
    }

    public AsyncApi Parse() throws Exception {
        if (!Util.has(httpMap, "async"))
            return null;

        Path asyncPath = Paths.get(path, Util.get(httpMap, "async").toString());

        logger.info(String.format("\tload async at path:%s...", asyncPath));

        String yml = new String(Files.readAllBytes(asyncPath));
        Object asyncYAML = new Yaml().load(yml);

        return new AsyncParser(asyncYAML, asyncPath.toString()).Parse();
    }
}