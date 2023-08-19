package com.amolotkoff.jr;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class JRCode implements IJR {

    @Getter
    @Setter
    private String value;

    @Override
    public String toCode() {
        return value;
    }
}