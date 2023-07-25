package com.amolotkoff.mocker.parser.model;

public class Import {
    private String imports;

    public Import() {
        imports = "";
    }

    public Import(String imports) {
        this.imports = imports;
    }

    public String getImports() {
        return imports;
    }
}