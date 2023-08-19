package com.amolotkoff.jr.methods.attributes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
public class JRAttributeValue {

    @Getter
    @Setter
    private String value;

    @Override
    public String toString() {
        return value;
    }
}