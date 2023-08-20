package com.amolotkoff.jr;

import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JRImports implements IJR {

    @Getter
    private List<String> values;

    @Override
    public String toCode() {
        StringBuilder container = new StringBuilder();

        for(String value : values)
            container.append(String.format("import %s;", value));

        return container.toString();
    }
}