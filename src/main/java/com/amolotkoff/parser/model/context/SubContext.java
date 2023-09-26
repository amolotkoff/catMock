package com.amolotkoff.parser.model.context;

public class SubContext {
    private String name;
    private Class type;
    private Object value;

    public SubContext(String name, Class type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Class getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
}
