package com.amolotkoff.builder.strategy.imports;

import com.amolotkoff.builder.strategy.IBuilderStrategy;
import com.amolotkoff.builder.strategy.util.Formatter;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.Iterator;
import java.util.List;

@Data
@Builder
public class Imports implements IBuilderStrategy {

    @Singular
    private final List<String> values;

    @Override
    public String toCode() {
        return ""; // TO-DO
    }
}