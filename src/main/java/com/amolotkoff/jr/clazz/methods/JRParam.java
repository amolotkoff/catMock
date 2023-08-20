package com.amolotkoff.jr.clazz.methods;

import com.amolotkoff.jr.IJR;
import com.amolotkoff.jr.attributes.JRAttribute;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JRParam implements IJR {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Class type;

    @Getter
    private List<JRAttribute> attributes;

    @Override
    public String toCode() {
        StringBuilder container = new StringBuilder();

        for(int i = 0; i < attributes.size(); i++) {
            JRAttribute attribute = attributes.get(i);
            container.append(String.format("%s ", attribute.toCode()));
        }

        container.append(String.format("%s %s", type.getSimpleName(), name));

        return container.toString();
    }
}