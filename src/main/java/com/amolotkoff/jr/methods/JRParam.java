package com.amolotkoff.jr.methods;

import com.amolotkoff.jr.methods.attributes.JRAttribute;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JRParam {

    @Getter
    @Setter
    private String name;

    @Getter
    private List<JRAttribute> attributes;
}