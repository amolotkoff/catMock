package com.amolotkoff.mocker.register;

import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.util.Collection;

public class RegisterMappingModel {
    public String path;
    public RequestMethod requestMethod;
    public String[] produces;
    public String[] consumes;
    public Method method;

    public RegisterMappingModel(String path, RequestMethod requestMethod, String[] produces, String[] consumes, Method method) {
        this.path = path;
        this.requestMethod = requestMethod;
        this.produces = produces;
        this.consumes = consumes;
        this.method = method;
    }
}