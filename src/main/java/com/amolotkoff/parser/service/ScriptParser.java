package com.amolotkoff.parser.service;

import com.amolotkoff.parser.model.ScriptModel;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ScriptParser {
    private final Logger logger;
    private final Object map;

    public ScriptParser(Object map) {
        this.logger = LogManager.getRootLogger();
        this.map = map;
    }

    public ScriptModel Parse() throws Exception {
        logger.info("\tparse script...");
        String script = Util.<String>get(map, "script");

        return new ScriptModel(script != null ? script : "");
    }
}