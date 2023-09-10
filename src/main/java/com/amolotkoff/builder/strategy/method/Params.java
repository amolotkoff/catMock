package com.amolotkoff.builder.strategy.method;

import com.amolotkoff.builder.strategy.IBuilderStrategy;
import com.amolotkoff.builder.strategy.util.Formatter;
import lombok.*;

import java.util.List;

@Data
@Builder
public class Params implements IBuilderStrategy {

    @Singular
    private List<Param> values;

    @Override
    public String toCode() {
        return Formatter.builder()
                        .format(values, 0, ", ")
                        .toString();
    }
}