package com.amolotkoff.jr;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JRCodeContainer implements IJR {

    @Getter
    @Setter
    private List<IJR> jrs;

    @Override
    public String toCode() {
        StringBuilder container = new StringBuilder();

        for (IJR jr : jrs)
            container.append(jr.toCode());

        return container.toString();
    }
}