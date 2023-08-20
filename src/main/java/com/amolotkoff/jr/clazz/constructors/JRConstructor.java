package com.amolotkoff.jr.clazz.constructors;

import com.amolotkoff.jr.IJR;
import com.amolotkoff.jr.clazz.methods.JRParam;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JRConstructor implements IJR {

    @Getter
    @Setter
    private String clazzName;

    @Getter
    private List<JRParam> params;

    @Getter
    @Setter
    private IJR body;

    @Override
    public String toCode() {
        StringBuilder container = new StringBuilder();

        container.append(String.format("%s (", clazzName));

        for (int i = 0; i < params.size(); i++) {
            JRParam param = params.get(i);

            if (i > 0)
                container.append(", ");

            container.append(param.toCode());
        }

        container.append(String.format(") {\n%s}", body.toCode()));

        return container.toString();
    }
}