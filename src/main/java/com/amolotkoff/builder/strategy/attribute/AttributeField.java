package com.amolotkoff.builder.strategy.attribute;

import com.amolotkoff.builder.strategy.IBuilderStrategy;
import com.amolotkoff.builder.strategy.util.Formatter;
import lombok.*;

@Data
@Builder
public class AttributeField implements IBuilderStrategy {

    private final String name;
    private final AttributeFieldValue value;

    @Override
    public String toCode() {
        return Formatter.builder()
                        .format("%s = %s", name, value.toCode())
                        .toString();
    }
}