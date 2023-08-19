package com.amolotkoff.jr.attributes;

import com.amolotkoff.jr.IJR;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JRAttributeField implements IJR {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private IJR value;

    @Override
    public String toCode() {
        return String.format("%s = %s", name, value.toCode());
    }
}