package com.amolotkoff.builder.strategy;

import com.amolotkoff.builder.strategy.imports.Imports;
import com.amolotkoff.builder.strategy.util.Formatter;
import lombok.*;

import java.util.List;

@Data
@Builder
public class ReflectedFile implements IBuilderStrategy {

    private final Imports imports;
    private final Clazz clazz;

    @Override
    public String toCode() {
        return Formatter.builder()
                        .format(imports)
                        .format(clazz)
                        .toString();
    }
}