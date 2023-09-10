package com.amolotkoff.builder.strategy.attribute;

import com.amolotkoff.builder.strategy.IBuilderStrategy;
import com.amolotkoff.builder.strategy.util.Formatter;
import lombok.*;

import java.util.List;

@Data
@Builder
public class Attributes implements IBuilderStrategy {

    @Singular
    private List<Attribute> attributes;

    @Override
    public String toCode() {
        return Formatter.builder()
                        .format(attributes, 0, " ")
                        .toString();
    }
}