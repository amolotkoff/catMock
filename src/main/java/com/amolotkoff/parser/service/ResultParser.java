package com.amolotkoff.parser.service;

import com.amolotkoff.parser.model.result.ResultValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

@Slf4j
public class ResultParser {
    public enum Word {
        RESULT
    }

    private final Object map;
    private final String path;
    private final Word word;

    public ResultParser(Object map, String path, Word word) {
        this.map = map;
        this.path = path;
        this.word = word;
    }

    public ResultValue Parse() throws Exception {
        log.info("\tparse result...");

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