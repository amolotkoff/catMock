package com.amolotkoff.jr;

import lombok.*;

public class Formatter {
    private Formatter parent;

    protected int tabs;

    public Formatter(Formatter parent, int tabs) {
        this.parent = parent;
        this.tabs = tabs;
    }

    public Formatter(int tabs) {
        this(null, tabs);
    }

    public Formatter(Formatter parent) {
        this(parent, 1);
    }

    //TODO
    public String format(String source) {
        return null;
    }
}