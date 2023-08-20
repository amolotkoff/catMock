package com.amolotkoff.jr.clazz;

import com.amolotkoff.jr.IJR;
import com.amolotkoff.jr.attributes.JRAttributes;
import com.amolotkoff.jr.clazz.constructors.JRConstructors;
import com.amolotkoff.jr.clazz.methods.*;
import com.amolotkoff.jr.clazz.fields.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JRClass implements IJR {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private JRAttributes attributes;

    @Getter
    private JRFields fields;

    @Getter
    private JRConstructors constructors;

    @Getter
    private JRMethods methods;

    @Override
    public String toCode() {
        StringBuilder container = new StringBuilder();

        container.append(attributes.toCode());
        container.append(String.format("public class %s {", name));
        container.append(fields.toCode());
        container.append(constructors.toCode());
        container.append(methods.toCode());
        container.append("}");

        return container.toString();
    }
}