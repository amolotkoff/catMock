package com.amolotkoff.jr.clazz.fields;

import com.amolotkoff.jr.IJR;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JRField implements IJR {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Class type;

    @Override
    public String toCode() {
        return String.format("%s %s", name, type.getSimpleName());
    }
}
