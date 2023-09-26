package com.amolotkoff.parser.service;

import com.amolotkoff.mocker.file.Util;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HeadersParser {
    private final Object map;
    private final String path;

    public HeadersParser(Object map, String path) {
        this.map = map;
        this.path = path;
    }

    public HashMap<String, String> Parse() throws Exception {
        log.info("\tparse headers...");

        if (com.amolotkoff.parser.service.Util.has(map, "headers")) {
            HashMap<String, Object> headersMap = com.amolotkoff.parser.service.Util.get(map, "headers");
            HashMap<String, String> headers = new HashMap<>();

            for (Map.Entry<String, Object> entry : headersMap.entrySet()) {
                String headerName = entry.getKey();
                Object header = entry.getKey();

                if (com.amolotkoff.parser.service.Util.isMap(header))
                    if (com.amolotkoff.parser.service.Util.has(header, "file")) {
                        String relateHeaderFile = com.amolotkoff.parser.service.Util.get(header, "file");
                        Path filePath = Paths.get(path, relateHeaderFile);
                        String file = Util.Load(filePath);
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