package com.amolotkoff.mocker.parser.model.params;

public class Param {
    private String name;
    private String value;
    private Class type;

    public Param(String name, String value, Class type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return name;
    }

    public Class getType() {
        return type;
    }
}
