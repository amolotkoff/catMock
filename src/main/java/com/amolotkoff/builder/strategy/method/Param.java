package com.amolotkoff.builder.strategy.method;

import com.amolotkoff.builder.strategy.IBuilderStrategy;
import com.amolotkoff.builder.strategy.attribute.Attributes;
import com.amolotkoff.builder.strategy.util.Formatter;
import lombok.*;

@Data
@Builder
public class Param implements IBuilderStrategy {

    private Attributes attributes;
    private String name;

    @Override
    public String toCode() {
        return Formatter.builder()
                        .format("%s %s", attributes.toCode(), name)
                        .toString();
    }
}