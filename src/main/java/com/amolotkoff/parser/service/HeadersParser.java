package com.amolotkoff.parser.service;

import com.amolotkoff.mocker.file.FileUtil;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class HeadersParser {
    private final Logger logger;
    private final Object map;
    private final String path;

    public HeadersParser(Object map, String path) {
        this.logger = LogManager.getRootLogger();
        this.map = map;
        this.path = path;
    }

    public HashMap<String, String> Parse() throws Exception {
        logger.info("\tparse headers...");

        if (Util.has(map, "headers")) {
            HashMap<String, Object> headersMap = Util.get(map, "headers");
            HashMap<String, String> headers = new HashMap<>();

            for (Map.Entry<String, Object> entry : headersMap.entrySet()) {
                String headerName = entry.getKey();
                Object header = entry.getKey();

                if (Util.isMap(header))
                    if (Util.has(header, "file")) {
                        String relateHeaderFile = Util.get(header, "file");
                        Path filePath = Paths.get(path, relateHeaderFile);
                        String file = FileUtil.Load(filePath);
                        headers.put(headerName, file);
                        continue;
                    }

                headers.put(entry.getKey(), header.toString());
            }

            return headers;
        }

        return new HashMap<>();
    }
}