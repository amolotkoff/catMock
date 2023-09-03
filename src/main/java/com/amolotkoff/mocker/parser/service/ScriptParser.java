package com.amolotkoff.mocker.parser.service;

import com.amolotkoff.mocker.parser.model.ScriptModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScriptParser {
    private final Object map;

    public ScriptParser(Object map) {
        this.map = map;
    }

    public ScriptModel Parse() throws Exception {
        log.info("\tparse script...");
        String script = Util.<String>get(map, "script");

        return new ScriptModel(script != null ? script : "");
    }
}