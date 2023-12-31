package com.amolotkoff.mocker.parser.service;

import com.amolotkoff.mocker.parser.exceptions.NoAPIFoundException;
import com.amolotkoff.mocker.parser.model.ScriptModel;
import com.amolotkoff.mocker.parser.model.api.Api;
import com.amolotkoff.mocker.parser.model.api.AsyncApi;
import com.amolotkoff.mocker.parser.service.async.AsyncProxyParser;
import com.amolotkoff.mocker.parser.service.delay.DelayParser;
import com.amolotkoff.mocker.util.DelayContainer;
import com.amolotkoff.mocker.parser.model.result.ResultValue;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@Slf4j
public class APIsParser {

    private final Object map;
    private final String path;

    public APIsParser(Object map, String path) {
        this.map = map;
        this.path = path;
    }

    public Api[] Parse() throws Exception {
        log.info("\tparse apis...");

        HashMap<String, Object> apis_map = Util.<HashMap<String, Object>>get(map, "api");

        if (apis_map == null)
            throw new NoAPIFoundException();

        List<Api> apis = new ArrayList<>();

        for (Map.Entry<String, Object> api : apis_map.entrySet()) {
            Api apiModel = parseAPI(api.getValue(), api.getKey());
            apis.add(apiModel);
        }

        Api[] apiArray = new Api[apis.size()];
        apiArray = apis.toArray(apiArray);
        log.info("\tsuccess...");

        return apiArray;
    }

    private Api parseAPI(Object map, String name) throws Exception {
        log.info("\tparse api...");

        String path = Util.<String>get(map, "path");

        if (path == null)
            throw new Exception("No found path");

        ScriptModel scriptModel = new ScriptParser(map).Parse();
        ResultValue resultValue = new ResultParser(map, path, ResultParser.Word.RESULT).Parse();
        RequestMethod requestMethod = new RequestMethodParser(map).Parse();
        DelayContainer delay = new DelayParser(map).Parse();
        Collection<String> produces = new ProducerConsumerParser(map, ProducerConsumerParser.Type.PRODUCES).Parse();
        Collection<String> consumes = new ProducerConsumerParser(map, ProducerConsumerParser.Type.CONSUMES).Parse();
        AsyncApi asyncApi = new AsyncProxyParser(map, this.path).Parse();

        return new Api(name,
                       path,
                       requestMethod,
                       consumes,
                       produces,
                       delay,
                       resultValue,
                       scriptModel,
                       asyncApi);
    }
}