package com.amolotkoff.mocker.register;

import java.util.Collection;

public class RegisterModel {
    public Object controller;
    public Collection<RegisterMappingModel> mappings;

    public RegisterModel(Object controller, Collection<RegisterMappingModel> mappings) {
        this.controller = controller;
        this.mappings = mappings;
    }
}