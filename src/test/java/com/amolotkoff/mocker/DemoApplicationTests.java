package com.amolotkoff.mocker;

import com.amolotkoff.mocker.parser.model.http.HttpControllerModel;
import com.amolotkoff.mocker.parser.service.HTTPParser;
import com.amolotkoff.mocker.parser.service.Util;
import com.amolotkoff.mocker.register.RegisterModel;
import com.amolotkoff.mocker.util.Context;
import com.amolotkoff.mocker.util.ContextService;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.yaml.snakeyaml.Yaml;

import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

public class DemoApplicationTests {

    private final String no_context_source = "http-controller:\n" +
                                             "  name: \"MySuperController\"\n" +
                                             "  api:\n" +
                                             "    api1: #static result\n" +
                                             "      url: \"/mock/api1/get\"\n" +
                                             "      result:\n" +
                                             "        status: 200\n" +
                                             "        value: \"{ body: [\\\"Ok\\\", \\\"False\\\"]}\"\n";

    private final String source = "http:\n" +
            "  script: \"int i = 5;\"\n" +
            "  context:\n" +
            "    script: \"String token = \\\"jwt-dsfbfdsbgfbfb54gb4v45g45g4\\\";\"\n" +
            "    ssltoken: \"jwt-ssl-eve4rh34rh34vfu347f34f3bfv\"\n" +
            "  api:\n" +
            "    api1:\n" +
            "      delay: 3000 #ms\n" +
            "      method: get\n" +
            "      path: \"/mock/{id}/get\"\n" +
            "      produces: \"application/json\"\n" +
            "      #consumes: \"application/json\"\n" +
            "      result:\n" +
            "        status: 200\n" +
            "        value: \"${source}\"\n" +
            "    api2:\n" +
            "      delay: 3000 #ms\n" +
            "      method: get\n" +
            "      path: \"/mock/get\"\n" +
            "      produces: \"application/json\"\n" +
            "      #consumes: \"application/json\"\n" +
            "      result:\n" +
            "        status: 200\n" +
            "        value: \"${source}\"";

    @Test
    public void parse() throws Exception {
        /*
        Object parsed_yaml = new Yaml().load(source);

        HTTPParser httpParser = new HTTPParser(parsed_yaml, "disk://sdfgfd\\superName.yml");
        HttpControllerModel httpModel = httpParser.Parse();
        String s = httpModel.toString();*/
    }

    @Test
    public void parse_load_class() throws Exception {
        /*
        Parser parser = new Parser();
        HttpControllerModel model = parser.Parse(no_context_source);
        ControllerReflectBuilder builder = new ControllerReflectBuilder();
        Object result = builder.Build(model);

        Object clazz = result.getClass().getMethods();
        System.out.println(result);*/

    }

    @Test
    public void substitutions() {
        ContextService service = new ContextService();
        Context context = service.getContext();

        context.put("hello", "${world}");
        context.put("world", "!!!");

        String result = context.substitude("${hello}");
    }

    @Test
    public void d() {
        HttpStatus status = HttpStatus.OK;

        System.out.println(status.name());
    }
}