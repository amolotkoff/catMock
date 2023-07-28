package com.amolotkoff.mocker.parser.service;

import com.amolotkoff.mocker.file.FileUtil;
import com.amolotkoff.mocker.parser.model.params.Param;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryParamsParser {
    private final Logger logger;
    private final Object map;
    private final String path;

    public QueryParamsParser(Object map, String path) {
        this.logger = LogManager.getRootLogger();
        this.map = map;
        this.path = path;
    }

    public Param[] Parse() throws Exception {
        if (!Util.has(map, "query-params"))
            return new Param[0];

        HashMap<String, Object> params = Util.get(map, "query-params");
        List<Param> result = new ArrayList<>();

        for(Map.Entry<String, Object> param : params.entrySet())
            result.add(new Param(param.getKey(), param.getValue().toString(), String.class));

        return result.toArray(new Param[result.size()]);
    }
}