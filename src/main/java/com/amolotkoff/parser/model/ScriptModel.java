package com.amolotkoff.parser.model;

import lombok.*;

@Data
public class ScriptModel {
    private final String imports;
    private final String action;
    private final String onStart;
    private final String preAction;
    private final String postAction;
}