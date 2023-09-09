package com.amolotkoff.mocker.register;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Component
@Slf4j
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

        log.info("mapping: {}\n with apis:", model.controller.getClass().getSimpleName());

        for (RegisterMappingModel api : model.mappings) {
            log.info("http: {}, {}\n\t\tconsumes Content-Type:{}\n\t\tproduces Content-Type:{}",
                     api.requestMethod,
                     api.path,
                     api.consumes,
                     api.produces);

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