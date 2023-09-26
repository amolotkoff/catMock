package com.amolotkoff.parser.model.result;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

public class ResultValue {
    private String body;
    private HttpStatus status;
    private HashMap<String, String> headers;

    public ResultValue(String body, HttpStatus status, HashMap<String, String> headers) {
        this.body = body;
        this.status = status;
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public  HttpStatus getStatus() {
        return status;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }
}