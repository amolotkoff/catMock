package com.amolotkoff.parser.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AcceptStatusParser {

    private final HttpStatus[] DEFAULT_STATUSES = new HttpStatus[] { HttpStatus.OK, HttpStatus.ACCEPTED, HttpStatus.CREATED };
    private final Object map;
    private final String path;

    public AcceptStatusParser(Object map, String path) {
        this.map = map;
        this.path = path;
    }

    public HttpStatus[] Parse() throws Exception {
        if(!Util.has(map, "check"))
            return DEFAULT_STATUSES;

        Object checkMap = Util.get(map, "check");

        if (!Util.has(checkMap, "status"))
            return DEFAULT_STATUSES;

        Object status = Util.get(checkMap, "status");

        if(status instanceof Integer)
            return new HttpStatus[]{ parseStatus((Integer) status) };

        List<HttpStatus> statuses = new ArrayList<>();

        for(String splitted : status.toString().split(",")) {
            String trimmed = splitted.trim();
            if (!trimmed.equals(""))
                statuses.add(parseStatus(trimmed));
        }

        return statuses.toArray(new HttpStatus[statuses.size()]);
    }

    private HttpStatus parseStatus(String value) throws Exception {
        return parseStatus(Integer.valueOf(value));
    }

    private HttpStatus parseStatus(Integer value) throws Exception {
        try {
            return HttpStatus.valueOf(value);
        }
        catch (Exception e) {
            throw e;
        }
    }
}