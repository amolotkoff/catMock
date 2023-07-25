package com.amolotkoff.mocker.parser.model.context;

public class MainContext {
    private SubContext[] subContexts;
    private String code;

    public MainContext(SubContext[] subContexts, String code) {
        this.subContexts = subContexts;
        this.code = code;
    }

    public SubContext[] getSubContexts() {
        return subContexts;
    }

    public String getCode() {
        return code;
    }
}