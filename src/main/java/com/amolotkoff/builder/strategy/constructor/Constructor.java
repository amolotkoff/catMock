package com.amolotkoff.builder.strategy.constructor;

import com.amolotkoff.builder.strategy.IBuilderStrategy;
import com.amolotkoff.builder.strategy.util.Formatter;
import lombok.*;

import java.util.List;

@Data
@Builder
public class Constructor implements IBuilderStrategy {

    private final List<IBuilderStrategy> statements;

    @Override
    public String toCode() {
        return Formatter.builder()
                        .format("public %s() {", 1)
                        .format(statements, 1, "\n")
                        .format("}", 1)
                        .toString();
    }
}