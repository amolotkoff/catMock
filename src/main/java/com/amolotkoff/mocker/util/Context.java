package com.amolotkoff.mocker.util;

import org.apache.commons.text.StringSubstitutor;

import java.util.HashMap;

public class Context {
    private final ContextService service;
    private final HashMap<String, Object> substitutions;
    private final HashMap<String, Object> values;
    private final StringSubstitutor substitutor;

    public Context(ContextService service) {
        this.service = service;
        this.substitutions = new HashMap<>();
        this.substitutor = new StringSubstitutor(substitutions);
        this.values = new HashMap<>();
    }

    public void clear() {
        substitutions.clear();
        values.clear();
    }

    public void release() {
        service.ReturnToStack(this);
    }

    public String substitude(String source) {
        return substitutor.replace(source);
    }

    public void put(String substitution, Object value) {
        substitutions.put(substitution, value);
    }

    public void save(String name, Object value) {
        values.put(name, value);
    }

    public Object get(String name) {
        return values.get(name);
    }
}