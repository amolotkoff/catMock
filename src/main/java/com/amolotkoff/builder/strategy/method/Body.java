package com.amolotkoff.builder.strategy.method;

import com.amolotkoff.builder.strategy.IBuilderStrategy;
import java.util.List;

import com.amolotkoff.builder.strategy.util.Formatter;
import lombok.*;

@Data
@Builder
public class Body implements IBuilderStrategy {

    @Singular
    private final List<IBuilderStrategy> statements;

    @Override
    public String toCode() {
        return Formatter.builder()
                        .format(statements, 2, "\n")
                        .toString();
    }
}