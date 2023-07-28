package com.amolotkoff.mocker.parser.service.async;

import com.amolotkoff.mocker.parser.model.params.Param;
import com.amolotkoff.mocker.parser.model.result.AsyncResultValue;
import com.amolotkoff.mocker.parser.model.result.ResultValue;
import com.amolotkoff.mocker.parser.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

public class AsyncResultParser {

    private final Logger logger;
    private final Object map;
    private final String path;

    public AsyncResultParser(Object map, String path) {
        this.logger = LogManager.getRootLogger();
        this.map = map;
        this.path = path;
    }

    public AsyncResultValue Parse() throws Exception {
        logger.info("\tparse async-result...");

        Object resultMap = Util.get(map, "result");

        if (resultMap == null)
            return new AsyncResultValue("", new Param[0], new HashMap<>());

        Param[] queryParams = new QueryParamsParser(resultMap, path).Parse();
        HashMap<String, String> headers = new HeadersParser(resultMap, path).Parse();
        String body = new BodyValueParser(resultMap, path).Parse();
        //body = body.replace("\"","\\\"").replace("\'","\\\'"); //need?

        return new AsyncResultValue(body, queryParams, headers);
    }
}
