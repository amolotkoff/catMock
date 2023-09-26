package com.amolotkoff.parser.service;

import com.amolotkoff.mocker.file.Util;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class BodyValueParser {

    private final Object map;
    private final String path;

    public BodyValueParser(Object map, String path) {
        this.map = map;
        this.path = path;
    }

    public String Parse() throws Exception {
        Object value = com.amolotkoff.parser.service.Util.get(map, "value");

        if (value == null)
            return "\"\"";

        if (com.amolotkoff.parser.service.Util.isMap(value)) {
            String relateFilePath = com.amolotkoff.parser.service.Util.get(value, "file");
            Path filePath = Paths.get(path, relateFilePath);

            return Util.FormatFile(Util.Load(filePath), FilenameUtils.getExtension(filePath.toString()));
        }

        return value.toString();
    }
}