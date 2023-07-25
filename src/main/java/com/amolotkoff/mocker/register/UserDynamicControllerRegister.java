package com.amolotkoff.mocker.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Component
public class UserDynamicControllerRegister {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    public UserDynamicControllerRegister(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    public void registerUserController(RegisterModel model) throws Exception {

        /*
        RequestMappingInfo.BuilderConfiguration options = new RequestMappingInfo.BuilderConfiguration();
        options.setPathMatcher(new PathMa);
        options.setPatternParser(new PathPatternParser());
        */

        System.out.println("MAPPING " + model);
        for (RegisterMappingModel api : model.mappings) {
            handlerMapping.registerMapping(
                    RequestMappingInfo.paths(api.path)
                            .methods(api.requestMethod)
                            .consumes(api.consumes)
                            .produces(api.produces)
                            .build(),
                    model.controller,
                    api.method);
        }
    }
}