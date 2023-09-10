package com.amolotkoff.builder.model;

import lombok.*;

@Data
@Builder
public class Clazz {

    @Singular
    private Field[] field;
}