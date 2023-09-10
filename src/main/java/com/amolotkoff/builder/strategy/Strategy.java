package com.amolotkoff.builder.strategy;

import com.amolotkoff.builder.strategy.method.Method;
import lombok.*;

@Data
@Builder
public class Strategy implements IBuilderStrategy {

    @Singular
    private Method[] method;

    @Override
    public String toCode() {

        return null;
    }
}
