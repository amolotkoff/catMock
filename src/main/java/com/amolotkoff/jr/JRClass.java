package com.amolotkoff.jr;

import com.amolotkoff.jr.methods.*;
import com.amolotkoff.jr.fields.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JRClass implements IJR {

    @Getter
    private

    @Getter
    private JRFields fields;

    @Getter
    private JRMethods methods;

    @Override
    public void IReflectBuild() {

    }

    @Override
    public String toCode() {
        return null;
    }
}
