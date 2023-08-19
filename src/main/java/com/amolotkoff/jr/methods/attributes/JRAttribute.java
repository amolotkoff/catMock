package com.amolotkoff.jr.methods.attributes;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JRAttribute {

    @Getter
    @Setter
    private String name;

    @Getter
    private List<JRAttributeField> fields;

    @Override
    public String toString() {
        StringBuilder container = new StringBuilder("@" + name);

        if (fields.size() > 0) {
            container.append("(");

            for (int i = 0; i < fields.size(); i++) {
                JRAttributeField field = fields.get(i);

                if (i > 0)
                    container.append(", ");

                container.append(String.format("%s = %s", field.getName(), field.getValue()));
            }

            container.append(")");
        }

        return container.toString();
    }
}