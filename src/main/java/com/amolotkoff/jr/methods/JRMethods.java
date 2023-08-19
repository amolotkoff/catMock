package com.amolotkoff.jr.methods;

import lombok.*;

import java.util.List;

@NoArgsConstructor
public class JRMethods implements IReflectBuilder {

    @Getter
    private List<JRMethod> methods;

    @Override
    public void IReflectBuild() {

    }

    public JRMethod addMethod() {

    }
}
