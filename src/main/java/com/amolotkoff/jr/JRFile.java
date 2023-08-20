package com.amolotkoff.jr;

import com.amolotkoff.jr.clazz.JRClass;
import lombok.*;
import com.amolotkoff.util.PathBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JRFile implements IJR {

    @Getter
    private PathBuilder path;

    @Getter
    private JRImports imports;

    @Getter
    private JRClass clazz;

    @Override
    public String toCode() {
        StringBuilder container = new StringBuilder();

        container.append(imports.toCode());
        container.append(clazz.toCode());

        return container.toString();
    }
}