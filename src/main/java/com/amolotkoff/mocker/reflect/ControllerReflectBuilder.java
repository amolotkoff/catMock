package com.amolotkoff.mocker.reflect;

import com.amolotkoff.mocker.file.FileUtil;
import com.amolotkoff.mocker.parser.model.api.Api;
import com.amolotkoff.mocker.parser.model.context.SubContext;
import com.amolotkoff.mocker.parser.model.http.HttpControllerModel;
import com.amolotkoff.mocker.register.*;
import com.amolotkoff.mocker.util.*;
import com.amolotkoff.mocker.controllers.ApiController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ControllerReflectBuilder {
    @Autowired
    private ContextService contextService;
    @Autowired
    private ApiController delayController;

    private final JavaCompiler compiler;
    private final DiagnosticCollector<JavaFileObject> diagnostics;
    private final InMemoryFileManager manager;
    private final List<String> options;


    public ControllerReflectBuilder() throws Exception {
        this.compiler = ToolProvider.getSystemJavaCompiler();
        this.diagnostics = new DiagnosticCollector<>();
        this.manager = new InMemoryFileManager(compiler.getStandardFileManager(null, null, null));
        this.options = exportLibs();
    }

    private List<String> exportLibs() {
        Path libFolder = Paths.get(FileUtil.HOME_DIRECTORY.toPath().toString(), "lib");

        if (!Files.exists(libFolder))
            return null;

        List<String> options = new ArrayList<>();
        options.add("-classpath");

        StringBuilder libs = new StringBuilder();
        libs.append(".");

        log.info("load libs from /lib folder...");

        //import external libs
        FileUtil.parseDirectory(Paths.get(FileUtil.HOME_DIRECTORY.toPath().toString(), "lib"), ".jar")
                   .forEach((s) -> {
                       //logger.log(Level.INFO, String.format("\t%s", s));

                       log.info("{}...", s.toString());

                       if (libs.length() > 1)
                           libs.append(";");

                       libs.append(s.toString());
                   });

        log.info("end of loading libs");

        options.add(libs.toString());

        return options;
    }

    public RegisterModel Build(HttpControllerModel model) throws Exception {
        JavaFileObject sourceFile = new JavaSourceFromString(model.QualifiedName(), model.toString());
        List<JavaFileObject> sourceFiles = new ArrayList<>();

        sourceFiles.add(sourceFile);
        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, diagnostics, options, null, sourceFiles);

        boolean result = task.call();

        if (!result) {
            diagnostics.getDiagnostics()
                    .forEach(d -> {
                        log.info(String.valueOf(d));
                    });
        } else {

            ClassLoader classLoader = manager.getClassLoader(null);
            Class<?> clazz = classLoader.loadClass(model.QualifiedName());

            Object instance = clazz.getConstructors()[0].newInstance(); //now we have only one constructor

            //inject context service field
            Field contextServiceField = clazz.getDeclaredField("contextService");
            contextServiceField.setAccessible(true);
            contextServiceField.set(instance, contextService);

            //inject delaysFactories and inject values to controllers

            for (SubContext context : model.getContext().getSubContexts()) {
                Field contextField = clazz.getDeclaredField(context.getName());
                contextField.setAccessible(true);
                contextField.set(instance, context.getValue());
            }

            for (Api api : model.getApis()) {
                DelayContainer delayContainer = api.getDelay();
                IDelayFactory factory = delayContainer.factory;

                Field delayApiField = clazz.getDeclaredField(String.format("delay_%s", api.getName()));
                delayApiField.setAccessible(true);
                delayApiField.set(instance, factory);

                delayController.AddApi(instance.getClass().getSimpleName(), api);
            }

            List<RegisterMappingModel> mappingModels = new ArrayList<>();
            RegisterModel registerModel = new RegisterModel(instance, mappingModels);

            for(Api api : model.getApis()) {
                Method method = Arrays.stream(clazz.getMethods())
                                      .filter(x -> x.getName().equals(api.getName()))
                                      .findFirst().orElse(null);

                String[] produces = api.getProduces().toArray(new String[api.getProduces().size()]);
                String[] consumes = api.getConsumes().toArray(new String[api.getConsumes().size()]);

                mappingModels.add(new RegisterMappingModel(api.getPath(),
                                                           api.getRequestMethod(),
                                                           produces,
                                                           consumes,
                                                           method));

            }

            return registerModel;
        }

        return null;
    }
}