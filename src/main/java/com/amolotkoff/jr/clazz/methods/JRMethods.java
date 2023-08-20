package com.amolotkoff.jr.clazz.methods;

import com.amolotkoff.jr.IJR;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JRMethods implements IJR {

    @Getter
    private List<JRMethod> methods;

    @Override
    public String toCode() {
        StringBuilder container = new StringBuilder();

        for(JRMethod method : methods)
            container.append(String.format("%s\n", method.toCode()));

        return container.toString();
    }
}