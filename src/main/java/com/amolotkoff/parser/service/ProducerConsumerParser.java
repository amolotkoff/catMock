package com.amolotkoff.parser.service;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ProducerConsumerParser {
    public enum Type {
        PRODUCES,
        CONSUMES
    }

    private final Logger logger;
    private final Object map;
    private final Type type;

    public ProducerConsumerParser(Object map, Type type) {
        this.logger = LogManager.getRootLogger();
        this.map = map;
        this.type = type;
    }

    public Collection<String> Parse() throws Exception {
        if (!Util.<String>has(map, type.toString().toLowerCase()))
            return new ArrayList<>(0);

        return Arrays.asList(Util.<String>get(map, type.toString().toLowerCase())); // no multiply values
    }
}