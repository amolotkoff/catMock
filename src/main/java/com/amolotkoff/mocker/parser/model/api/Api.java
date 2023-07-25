package com.amolotkoff.mocker.parser.model.api;

import com.amolotkoff.mocker.parser.model.ScriptModel;
import com.amolotkoff.mocker.util.DelayContainer;
import com.amolotkoff.mocker.util.IDelayFactory;
import com.amolotkoff.mocker.parser.model.result.ResultValue;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;
import java.util.HashMap;

public class Api {
    private String name;
    private String path;

    private RequestMethod requestMethod;
    private Collection<String> consumes;
    private Collection<String> produces;

    private DelayContainer delay;

    private HashMap<String, String> resultHeaders;
    private ResultValue resultValue;
    private ScriptModel script;

    private AsyncApi asyncApi;

    public Api(String name,
               String path,
               RequestMethod requestMethod,
               Collection<String> consumes,
               Collection<String> produces,
               DelayContainer delay,
               HashMap<String, String> resultHeaders,
               ResultValue resultValue,
               ScriptModel script) {
        this(name, path, requestMethod, consumes, produces, delay, resultHeaders, resultValue, script, null);
    }

    public Api(String name,
               String path,
               RequestMethod requestMethod,
               Collection<String> consumes,
               Collection<String> produces,
               DelayContainer delay,
               HashMap<String, String> resultHeaders,
               ResultValue resultValue,
               ScriptModel script,
               AsyncApi asyncApi) {

        this.name = name;
        this.path = path;
        this.requestMethod = requestMethod;
        this.consumes = consumes;
        this.produces = produces;
        this.delay = delay;
        this.resultHeaders = resultHeaders;
        this.resultValue = resultValue;
        this.script = script;
        this.asyncApi = asyncApi;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }


    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public Collection<String> getConsumes() {
        return consumes;
    }

    public Collection<String> getProduces() {
        return produces;
    }

    public DelayContainer getDelay() {
        return delay;
    }

    public HashMap<String, String> getResultHeaders() {
        return resultHeaders;
    }

    public ResultValue getResultValue() {
        return resultValue;
    }

    public ScriptModel getScript() {
        return script;
    }

    public AsyncApi getAsyncApi() {
        return asyncApi;
    }
}