package com.amolotkoff.jr.methods;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Builder
public class JRSignature {

    @Getter
    @Setter
    private Class returnType;

    @Getter
    @Setter
    private List<JRParam> params;
}