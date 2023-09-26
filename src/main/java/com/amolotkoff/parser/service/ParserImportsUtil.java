package com.amolotkoff.parser.service;

public class ParserImportsUtil {

    private String IMPORTS = "import org.springframework.web.bind.annotation.*;\n" +
                             "import org.springframework.http.HttpStatus;\n" +
                             "import org.springframework.http.*;\n" +
                             "import org.springframework.util.*;\n" +
                             "import org.apache.commons.text.StringSubstitutor;\n"+
                             "import org.apache.logging.log4j.Logger;\n" +
                             "import org.apache.logging.log4j.LogManager;\n"+
                             "import java.util.HashMap;\n"+
                             "import javax.servlet.http.*;\n" +
                             "import java.util.Map;\n" +
                             "import com.amolotkoff.mocker.util.*;\n" +
                             "import reactor.core.publisher.Mono;\n" +
                             "import org.springframework.web.reactive.function.client.WebClient;\n"+
                             "import reactor.core.Disposable;\n" +
                             "import java.time.Duration;\n";

    public String prepareImports(String source) {
        String imports = source.replace(",", ";");
        StringBuilder builder = new StringBuilder();

        for(String _import : imports.split(";")) {
            _import = _import.trim();
            if (!_import.isEmpty())
                builder.append(String.format("import %s;", _import));
        }

        return builder.toString();
    }
}