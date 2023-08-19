package com.amolotkoff.jr.attributes;

import com.amolotkoff.jr.IJR;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JRAttribute implements IJR {

    @Getter
    @Setter
    private String name;

    @Getter
    private List<JRAttributeField> fields;

    @Override
    public String toCode() {
        StringBuilder container = new StringBuilder("@" + name);

        if (fields.size() > 0) {
            container.append("(");

            for (int i = 0; i < fields.size(); i++) {
                JRAttributeField field = fields.get(i);

                if (i > 0)
                    container.append(", ");

                container.append(field.toCode());
            }

            container.append(")");
        }

        return container.toString();
    }
}