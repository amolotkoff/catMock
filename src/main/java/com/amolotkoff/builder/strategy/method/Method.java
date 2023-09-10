package com.amolotkoff.builder.strategy.method;

import com.amolotkoff.builder.strategy.IBuilderStrategy;
import com.amolotkoff.builder.strategy.attribute.Attribute;
import com.amolotkoff.builder.strategy.util.*;
import com.amolotkoff.builder.strategy.attribute.Attributes;
import lombok.*;

import java.text.Normalizer;

@Data
@Builder
public class Method implements IBuilderStrategy {

    private Attributes attributes;
    private Signature signature;
    private Body body;

    @Override
    public String toCode() {
        return Formatter.builder()
                        .format(attributes, 1)
                        .line()
                        .format(signature, 1)
                        .format(" {")
                        .line()
                        .format(body, 2)
                        .format("}", 1)
                        .line()
                        .toString();
    }
}