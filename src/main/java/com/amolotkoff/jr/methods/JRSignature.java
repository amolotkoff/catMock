package com.amolotkoff.jr.methods;

import com.amolotkoff.jr.IJR;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Builder
public class JRSignature implements IJR {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Class returnType;

    @Getter
    @Setter
    private List<JRParam> params;


    @Override
    public String toCode() {
        StringBuilder container = new StringBuilder(String.format("%s %s (", returnType.getSimpleName(), name));

        for (int i = 0; i < params.size(); i++) {
            JRParam param = params.get(i);

            if (i > 0)
                container.append(", ");

            container.append(param.toCode());
        }

        container.append(")");

        return container.toString();
    }
}