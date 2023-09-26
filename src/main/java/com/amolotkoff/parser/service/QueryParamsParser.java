package com.amolotkoff.parser.service;

import com.amolotkoff.parser.model.params.Param;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class QueryParamsParser {
    private final Object map;
    private final String path;

    public QueryParamsParser(Object map, String path) {
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