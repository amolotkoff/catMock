package com.amolotkoff.jr.clazz.methods;

import com.amolotkoff.jr.IJR;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class JRMethodBody implements IJR {

    @Getter
    @Setter
    private IJR value;

    @Override
    public String toCode() {
        return value.toCode();
    }
}