package com.amolotkoff.builder.strategy;

import com.amolotkoff.builder.strategy.constructor.Constructor;
import com.amolotkoff.builder.strategy.method.Method;
import com.amolotkoff.builder.strategy.util.Formatter;
import lombok.*;

import java.util.List;

@Data
@Builder
public class Clazz implements IBuilderStrategy {

    private final String name;

    @Singular
    private final List<Field> fields;
    @Singular
    private final List<Method> methods;

    private final Constructor constructor;

    @Override
    public String toCode() {
        return Formatter.builder()
                .format("public class %s {", name)
                .line()
                .format(fields, 1, "\n")
                .line()
                .format(constructor.toCode(), name)
                .line()
                .format(methods, 1, "\n")
                .toString();
    }
}