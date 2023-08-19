package com.amolotkoff.jr.methods.attributes;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JRAttributeField {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Object value;
}