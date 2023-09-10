package com.amolotkoff.builder.strategy.method;

import com.amolotkoff.builder.strategy.IBuilderStrategy;
import lombok.*;

@Data
@Builder
public class Body implements IBuilderStrategy {

    @Override
    public String toCode() {
        return null;
    }
}
