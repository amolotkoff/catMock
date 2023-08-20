package com.amolotkoff.parser.service;

import com.amolotkoff.mocker.file.FileUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.nio.file.Path;
import java.nio.file.Paths;

public class BodyValueParser {
    private final Logger logger;
    private final Object map;
    private final String path;

    public BodyValueParser(Object map, String path) {
        this.logger = LogManager.getRootLogger();
        this.map = map;
        this.path = path;
    }

    public String Parse() throws Exception {
        Object value = Util.get(map, "value");

        if (value == null)
            return "\"\"";

        if (Util.isMap(value)) {
            String relateFilePath = Util.get(value, "file");
            Path filePath = Paths.get(path, relateFilePath);

            return FileUtil.FormatFile(FileUtil.Load(filePath), FilenameUtils.getExtension(filePath.toString()));
        }

        return value.toString();
    }
}