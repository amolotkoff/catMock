package com.amolotkoff.jr.attributes;

import com.amolotkoff.jr.IJR;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JRAttributes implements IJR {

    @Getter
    private List<JRAttribute> values;

    @Override
    public String toCode() {
        StringBuilder container = new StringBuilder();

        for (JRAttribute attribute : values)
            container.append(String.format("") attribute.toCode());

        return null;
    }
}