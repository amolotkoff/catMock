package com.amolotkoff.builder.strategy.attribute;

import com.amolotkoff.builder.strategy.IBuilderStrategy;
import com.amolotkoff.builder.strategy.util.Formatter;
import lombok.*;

import java.util.List;

@Data()
@Builder
public class Attribute implements IBuilderStrategy {

    @Singular
    private final List<AttributeField> fields;
    private final String name;

    @Override
    public String toCode() {
        return Formatter.builder()
                        .format("@%s(", name)
                        .format(fields, 0, ", ")
                        .format(")")
                        .toString();
    }
}