package com.amolotkoff.mocker.parser.model.api;

import com.amolotkoff.mocker.parser.model.ScriptModel;
import com.amolotkoff.mocker.parser.model.context.MainContext;
import com.amolotkoff.mocker.parser.model.result.AsyncResultValue;
import com.amolotkoff.mocker.util.DelayContainer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

public class AsyncApi {
    private String name;
    private String path;
    private RequestMethod requestMethod;
    private Collection<String> produces;
    private DelayContainer delayContainer;
    private ScriptModel script;
    private Import imports;
    private HttpStatus[] accepting;
    private MainContext mainContext;
    private AsyncResultValue resultValue;

    public AsyncApi(String name,
                    String path,
                    RequestMethod requestMethod,
                    Collection<String> produces,
                    DelayContainer delayContainer,
                    ScriptModel script,
                    MainContext mainContext,
                    AsyncResultValue resultValue,
                    Import imports,
                    HttpStatus[] accepting) {

        this.name = name;
        this.path = path;
        this.requestMethod = requestMethod;
        this.produces = produces;
        this.delayContainer = delayContainer;
        this.script = script;
        this.mainContext = mainContext;
        this.resultValue = resultValue;
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
    public ScriptModel getScript() {
        return script;
    }
    public AsyncResultValue getResultValue() {
        return resultValue;
    }
    public Import getImports() {
        return imports;
    }
    public HttpStatus[] getAccepting() {
        return accepting;
    }
    public MainContext getContext() {
        return mainContext;
    }
}