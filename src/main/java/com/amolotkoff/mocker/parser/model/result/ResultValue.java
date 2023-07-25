package com.amolotkoff.mocker.parser.model.result;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

public class ResultValue {
    public String body;
    public HttpStatus status;

    public ResultValue(String body, HttpStatus status) {
        this.body = body;
        this.status = status;
    }
}