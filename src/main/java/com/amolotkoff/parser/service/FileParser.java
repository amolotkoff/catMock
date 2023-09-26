package com.amolotkoff.parser.service;

import com.amolotkoff.builder.strategy.ReflectedFile;
import com.amolotkoff.builder.strategy.imports.Imports;
import com.amolotkoff.mocker.file.Util;
import com.amolotkoff.parser.exceptions.NotMapException;
import com.amolotkoff.parser.model.http.HttpControllerModel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Paths;
import java.util.HashMap;

@Slf4j
@Data
public class FileParser {

    private final Object map;
    private final String path;

    public ReflectedFile Parse() throws Exception {
        log.info("\tparse http...");

        if (!com.amolotkoff.parser.service.Util.isMap(map))
            throw new NotMapException();



        HashMap<String, Object> httpMap = com.amolotkoff.parser.service.Util.get(map, "http", true, null);
        String parentPath = Paths.get(path).getParent().toString();

        return ReflectedFile.builder()
                   .imports(Imports.builder().build())
                   .clazz();

        return new HttpControllerModel(generateName(path, httpMap),
                                       new ContextParser(httpMap, parentPath).Parse(),
                                       new APIsParser(httpMap, parentPath).Parse(),
                                       new ScriptParser(httpMap).Parse());
    }

    private String generateName(String path, HashMap<String, Object> httpMap) throws Exception {
        if (com.amolotkoff.parser.service.Util.has(httpMap, "name"))
            return com.amolotkoff.parser.service.Util.get(httpMap, "name");

        return Util.GetBaseFileName(path, true);
    }
}