package com.amolotkoff.mocker.parser.service;

import com.amolotkoff.mocker.parser.model.result.ResultValue;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

public class ResultParser {
    public enum Word {
        RESULT
    }

    private final Logger logger;
    private final Object map;
    private final String path;
    private final Word word;

    public ResultParser(Object map, String path, Word word) {
        this.logger = LogManager.getRootLogger();
        this.map = map;
        this.path = path;
        this.word = word;
    }

    public ResultValue Parse() throws Exception {
        logger.info("\tparse result...");

        Object resultMap = Util.get(map, word.toString().toLowerCase());

        if (resultMap == null)
            return new ResultValue("", HttpStatus.OK, new HashMap<>());

        HttpStatus status = new StatusParser(resultMap).Parse();
        String body = new BodyValueParser(resultMap, path).Parse();
        body = body.replace("\"","\\\"").replace("\'","\\\'"); //need?

        HashMap<String, String> headers = new HeadersParser(resultMap, path).Parse();

        return new ResultValue(body, status, headers);
    }
}