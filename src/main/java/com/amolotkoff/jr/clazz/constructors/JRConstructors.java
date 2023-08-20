package com.amolotkoff.jr.clazz.constructors;

import com.amolotkoff.jr.IJR;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JRConstructors implements IJR {

    @Getter
    private List<JRConstructor> values;

    @Override
    public String toCode() {
        StringBuilder container = new StringBuilder();

        for(JRConstructor value : values)
            container.append(value.toCode());

        return container.toString();
    }
}