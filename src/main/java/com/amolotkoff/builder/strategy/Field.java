package com.amolotkoff.builder.strategy;

import com.amolotkoff.builder.strategy.IBuilderStrategy;
import com.amolotkoff.builder.strategy.util.Formatter;
import lombok.*;

@Data
@Builder
public class Field implements IBuilderStrategy {

    private final Class type;
    private final String name;
    private final Object value;

    @Override
    public String toCode() {
        return Formatter.builder()
                        .format("private %s %s;", 1, type.getSimpleName(), name)
                        .line()
                        .toString();
    }
}