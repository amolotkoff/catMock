package com.amolotkoff.mocker.parser.model.api;

import com.amolotkoff.mocker.parser.model.Import;
import com.amolotkoff.mocker.parser.model.ScriptModel;
import com.amolotkoff.mocker.parser.model.params.Param;
import com.amolotkoff.mocker.parser.model.result.ResultValue;
import com.amolotkoff.mocker.util.DelayContainer;
import com.amolotkoff.mocker.util.IDelayFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AsyncApi {
    private String name;
    private String path;
    private RequestMethod requestMethod;
    private Collection<String> produces;
    private HashMap<String, String> resultHeaders;
    private DelayContainer delayContainer;
    private ScriptModel script;
    private Param[] params;
    private String body;
    private Import imports;
    private HttpStatus[] accepting;

    public AsyncApi(String name,
                    String path,
                    RequestMethod requestMethod,
                    Collection<String> produces,
                    HashMap<String, String> resultHeaders,
                    DelayContainer delayContainer,
                    ScriptModel script,
                    Param[] params,
                    String body,
                    Import imports,
                    HttpStatus[] accepting) {

        this.name = name;
        this.path = path;
        this.requestMethod = requestMethod;
        this.produces = produces;
        this.delayContainer = delayContainer;
        this.resultHeaders = resultHeaders;
        this.script = script;
        this.params = params;
        this.body = body;
        this.imports = imports;
        this.accepting = accepting;
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

    public Collection<String> getProduces() {
        return produces;
    }

    public DelayContainer getDelay() {
        return delayContainer;
    }

    public HashMap<String, String> getResultHeaders() {
        return resultHeaders;
    }

    public Param[] getParams() {
        return params;
    }

    public ScriptModel getScript() {
        return script;
    }

    public String getBody() {
        return body;
    }
    public Import getImports() {
        return imports;
    }
    public HttpStatus[] getAccepting() {
        return accepting;
    }
}