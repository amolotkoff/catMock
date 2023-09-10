package com.amolotkoff.builder.strategy.method;

import com.amolotkoff.builder.strategy.IBuilderStrategy;
import com.amolotkoff.builder.strategy.util.Formatter;
import lombok.*;

@Data
@Builder
public class Signature implements IBuilderStrategy {

    private final Class type;
    private final String name;
    private final Params params;

    @Override
    public String toCode() {
        return Formatter.builder()
                        .format("public %s %s (%s)", type.getSimpleName(), name, params.toCode())
                        .toString();
    }
}