package com.amolotkoff.jr.clazz.methods;

import com.amolotkoff.jr.attributes.*;
import com.amolotkoff.jr.IJR;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JRMethod implements IJR {

    @Getter
    @Setter
    private JRSignature signature;

    @Getter
    @Setter
    private IJR body;

    @Getter
    private List<JRAttribute> attributes;

    @Override
    public String toCode() {
        StringBuilder container = new StringBuilder();

        for(JRAttribute attribute : attributes)
            container.append(String.format("%s\n", attribute.toCode()));

        container.append(String.format("public %s {\n%s}", signature.toCode(), body.toCode()));

        return container.toString();
    }
}