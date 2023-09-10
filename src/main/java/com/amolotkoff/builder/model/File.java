package com.amolotkoff.builder.model;

import lombok.*;

@Data
@Builder
public class File {

    @Singular
    private String[] imports;

    private Clazz clazz;
}