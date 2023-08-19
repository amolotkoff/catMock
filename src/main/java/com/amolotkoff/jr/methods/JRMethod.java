package com.amolotkoff.jr.methods;

import com.amolotkoff.jr.IJR;
import lombok.*;

@Builder
@NoArgsConstructor
public class JRMethod {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private JRSignature signature;

    @Getter
    @Setter
    private IJR body;

    @Override
    public String toString() {
        StringBuilder container = new StringBuilder();


    }
}