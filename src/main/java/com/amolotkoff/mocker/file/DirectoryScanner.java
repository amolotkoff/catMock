package com.amolotkoff.mocker.file;

import com.amolotkoff.mocker.parser.model.http.HttpControllerModel;
import com.amolotkoff.mocker.parser.service.*;
import com.amolotkoff.mocker.reflect.ControllerReflectBuilder;
import com.amolotkoff.mocker.register.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.springframework.core.io.support.*;
import org.springframework.core.io.Resource;
import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
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

        Path p = Paths.get(FileUtil.HOME_DIRECTORY.getPath(), "build");

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
                File file = resource.getFile();
                read(file.toPath());
            }
        }
    }

    private void read(Path path) {
        log.info(String.format("\t{}", path));

        try {
            String yml = new String(Files.readAllBytes(path));
            Object parsed_yaml = new Yaml().load(yml);

            if (Util.has(parsed_yaml, "async")) // we would parse it next, when we need it (for example on parsing http controller)
                return;

            HTTPParser httpParser = new HTTPParser(parsed_yaml, path.toString());
            HttpControllerModel httpModel = httpParser.Parse();
            RegisterModel registerModel = builder.Build(httpModel);
            register.registerUserController(registerModel);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}