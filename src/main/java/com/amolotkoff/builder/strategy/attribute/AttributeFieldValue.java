package com.amolotkoff.builder.strategy.attribute;

import com.amolotkoff.builder.strategy.IBuilderStrategy;
import lombok.*;

@Data
@Builder
public class AttributeFieldValue implements IBuilderStrategy {

    private final String value;

    @Override
    public String toCode() {
        return value;
    }
}