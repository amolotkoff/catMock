package com.amolotkoff.parser.model.result;

import com.amolotkoff.parser.model.params.Param;

import java.util.HashMap;

public class AsyncResultValue {
    private String body;
    private Param[] queryParams;
    private HashMap<String, String> headers;

    public AsyncResultValue(String body, Param[] queryParams, HashMap<String, String> headers) {
        this.body = body;
        this.queryParams = queryParams;
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public Param[] getQueryParams() {
        return queryParams;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

}
