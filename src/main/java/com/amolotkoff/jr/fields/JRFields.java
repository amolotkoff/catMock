package com.amolotkoff.jr.fields;

import com.amolotkoff.jr.IJR;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JRFields implements IJR {

    @Getter
    private List<JRField> fields;

    @Override
    public String toCode() {
        StringBuilder container = new StringBuilder();

        for(JRField field : fields)
            container.append(String.format("private %s;\n", field.toCode()));

        return container.toString();
    }
}
