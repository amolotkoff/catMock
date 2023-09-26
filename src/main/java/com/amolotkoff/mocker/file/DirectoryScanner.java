package com.amolotkoff.mocker.file;

import com.amolotkoff.parser.service.*;
import com.amolotkoff.mocker.reflect.ControllerReflectBuilder;
import com.amolotkoff.builder.strategy.*;
import com.amolotkoff.mocker.register.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.springframework.core.io.support.*;
import org.springframework.core.io.Resource;
import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class DirectoryScanner {

    @Autowired
    private ControllerReflectBuilder builder;
    @Autowired
    private UserDynamicControllerRegister register;
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private ResourcePatternResolver resourceResolver;

    @PostConstruct
    public void scan() throws Exception {
        log.info("Start parse configurations..");

        Path p = Paths.get(Util.HOME_DIRECTORY.getPath(), "build");

        if (Files.exists(p)) {
            try {
                Files.find(p, Integer.MAX_VALUE,
                                (path, attr) -> path.toString().endsWith(".yml") || path.toString().endsWith(".yaml"))
                        .forEach(path -> read(path));
            }
            catch (Exception e) {
                throw e;
            }
        }
        else {
            log.info("No found directory: build, so i download classes in resources");
            Resource[] resouces = ResourcePatternUtils.getResourcePatternResolver(resourceResolver).getResources("classpath:/static/build/*.yml");
            for (Resource resource : resouces) {
                java.io.File file = resource.getFile();
                read(file.toPath());
            }
        }
    }

    private void read(Path path) {
        log.info(String.format("\t{}", path));

        try {
            String yml = new String(Files.readAllBytes(path));
            Object parsed_yaml = new Yaml().load(yml);

            if (com.amolotkoff.parser.service.Util.has(parsed_yaml, "async")) // we would parse it next, when we need it (for example on parsing http controller)
                return;

            FileParser httpParser = new FileParser(parsed_yaml, path.toString());
            ReflectedFile httpModel = httpParser.Parse();
            RegisterModel registerModel = builder.Build(httpModel);
            register.registerUserController(registerModel);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}