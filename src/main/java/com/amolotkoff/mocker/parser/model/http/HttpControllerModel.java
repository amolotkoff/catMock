package com.amolotkoff.mocker.parser.model.http;

import com.amolotkoff.mocker.file.FileUtil;
import com.amolotkoff.mocker.parser.model.Import;
import com.amolotkoff.mocker.parser.model.ScriptModel;
import com.amolotkoff.mocker.parser.model.api.Api;
import com.amolotkoff.mocker.parser.model.api.AsyncApi;
import com.amolotkoff.mocker.parser.model.context.MainContext;
import com.amolotkoff.mocker.parser.model.context.SubContext;
import com.amolotkoff.mocker.parser.model.params.Param;
import com.amolotkoff.mocker.parser.model.result.ResultValue;
import com.amolotkoff.mocker.util.IDelayFactory;
import io.netty.handler.codec.Headers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpControllerModel {
    private final String CONTROLLER_PACKAGE = "com.amolotkoff.vtb.mock";
    private final String IMPORTS = "import org.springframework.web.bind.annotation.*;\n" +
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

    private String name;
    private MainContext context;
    private Api[] apis;
    private ScriptModel script;
    private Import imports;

    private Logger logger = LogManager.getRootLogger();

    public HttpControllerModel(String name, MainContext context, Api[] apis, ScriptModel script, Import imports) {
        this.name = name;
        this.context = context;
        this.apis = apis;
        this.script = script;
        this.imports = imports;
    }

    public String getName() {
        return name;
    }

    public Api[] getApis() {
        return apis;
    }

    public String QualifiedName() {
        return String.format("%s.%s", CONTROLLER_PACKAGE, name);
    }

    public MainContext getContext() {
        return context;
    }

    @Override
    public String toString() {
        StringBuilder packageBuilder = new StringBuilder();
        StringBuilder contextBuilder = new StringBuilder();
        StringBuilder constructorBuilder = new StringBuilder();
        StringBuilder methodsBuilder = new StringBuilder();

        imports(packageBuilder);

        contextFromCode(contextBuilder);
        context(contextBuilder);
        contextContextService(contextBuilder);
        contextDelay(contextBuilder);
        headersContext(contextBuilder);
        loggerContext(contextBuilder);

        constructor(constructorBuilder);

        //api-methods
        api(methodsBuilder, contextBuilder);

        packageBuilder.append("\n\n");
        contextBuilder.append("\n");
        constructorBuilder.append("\n");

        String result = packageBuilder.toString() +
                        String.format("public class %s {\n", name) +
                        contextBuilder.toString() +
                        constructorBuilder.toString() +
                        methodsBuilder.toString() +
                        "}";

        logger.debug(result);

        return result;
    }

    private void loggerContext(StringBuilder contextBuilder) {
        //contextBuilder.append(String.format("\tprivate final Logger logger = LogManager.getLogger(%s.class);\n", name));
    }

    private void imports(StringBuilder packageBuilder) {
        packageBuilder.append(String.format("package %s;\n\n%s%s",
                              CONTROLLER_PACKAGE,
                              IMPORTS,
                              imports.getImports()));
    }

    private void contextFromCode(StringBuilder context) {
        context.append("\n");
        String code = this.context.getCode();

        for(String splitted : code.split(";")) {
            splitted = splitted.trim();
            if (splitted != null)
                if (!splitted.equals(""))
                    context.append(String.format("\t%s\n", code));
        }
    }

    private void headersContext(StringBuilder contextBuilder) {
        for(Api api : apis)
            contextBuilder.append(String.format("\tprivate MultiValueMap<String, String> responseHeaders_%s = new LinkedMultiValueMap<>();\n", api.getName()));
    }

    private void headersConstructor(StringBuilder constructorBuilder) {
        //http

        for(Api api : apis) {
            for (Map.Entry<String, String> header : api.getResultValue().getHeaders().entrySet())
                constructorBuilder.append(String.format("\t\tthis.responseHeaders_%s.add(\"%s\", \"%s\");\n", api.getName(), header.getKey(), header.getValue()));
        }
    }

    private void context(StringBuilder context) {
        for (SubContext subContext : this.context.getSubContexts()) {
            if (subContext.getType().getSimpleName().equals(String.class.getSimpleName())) //check if we deal with String type
                context.append(String.format("\tprivate String %s;\n", subContext.getName()));
            else if (subContext.getType().getSimpleName().equals(Character.class.getSimpleName()))
                context.append(String.format("\tprivate %s %s = \'\';\n", subContext.getType().getSimpleName(), subContext.getName()));
            else
                context.append(String.format("\tprivate %s %s;\n", subContext.getType().getSimpleName(), subContext.getName()));
        }
    }

    private String formatString(String content) {
        final int MAX_LITERAL = 65500;

        if (content.length() < MAX_LITERAL)
            return "\"" + content + "\";";

        StringBuilder literalBuilder = new StringBuilder();

        int currentLength = 0;

        while(currentLength < content.length()) {
            if (currentLength > 0)
                literalBuilder.append(" +\n");

            literalBuilder.append("\t\t\"");

            for (int i = 0; i < MAX_LITERAL; i++) {
                literalBuilder.append(content.charAt(currentLength));
                currentLength++;
            }

            literalBuilder.append("\"\n");
        }

        literalBuilder.append(";");

        return literalBuilder.toString();
    }

    private void contextContextService(StringBuilder context) {
        context.append("\tprivate ContextService contextService;\n");
    }

    private void contextDelay(StringBuilder context) {
        for(Api api : apis)
            context.append(String.format("\tprivate %s delay_%s;\n", "IDelayFactory", api.getName()));
    }

    private void constructor(StringBuilder constructor) {
        //append all Delays params

        constructor.append(String.format("\tpublic %s(){\n", name));

        //set webClients
        for(Api api : apis) {
            AsyncApi asyncApi = api.getAsyncApi();

            if (asyncApi != null) {
                constructor.append(String.format("\t\tthis.asyncWebClient_%s = WebClient.builder().build();\n", asyncApi.getName()));
            }
        }

        for (String splitted : script.getCode().split("\n")) {
            String trimmed = splitted.trim();

            if (!trimmed.equals(""))
                constructor.append(String.format("\t\t%s\n", trimmed));
        }

        headersConstructor(constructor);

        constructor.append("\t}\n");
    }

    private void api(StringBuilder methodsBuilder, StringBuilder contextBuilder) {
        for (Api api : apis) {
            asyncWebClientContext(api, contextBuilder);

            methodsBuilder.append(String.format("\tpublic Mono<ResponseEntity<String>> %s(", api.getName()));

            String path = api.getPath();
            Pattern pathPattern = Pattern.compile("\\{\\w+\\}");
            Matcher matcher = pathPattern.matcher(path);

            int paramI = 0;
            while (matcher.find()) {
                String paramPath = matcher.group().substring(1, matcher.group().length() - 1);

                if (paramI > 0)
                    methodsBuilder.append(",");

                methodsBuilder.append(String.format("@PathVariable(name = \"%s\") String %s", paramPath, paramPath));
                paramI++;
            }

            if(paramI > 0)
                methodsBuilder.append(", ");

            methodsBuilder.append(String.format("@RequestBody(required = false) String requestBody, "));
            methodsBuilder.append(String.format("@RequestHeader Map<String, String> requestHeaders, "));
            methodsBuilder.append(String.format("HttpServletRequest request"));

            methodsBuilder.append(") {\n"); // end of method declaration
            if (api.getAsyncApi() != null)
                methodsBuilder.append("\t\tfinal Context context = contextService.getContext();\n");

            //methodsBuilder.append(String.format("\t\tlogger.info(\"%s\");\n", api.getName())); //log info
            methodsBuilder.append("\t\treturn Mono.defer(() -> {\n");

            if (api.getAsyncApi() == null)
                methodsBuilder.append("\t\t\tfinal Context context = contextService.getContext();\n");

            //body of method
            methodsBuilder.append("\t\t\tfinal String requestPath = request.getRequestURI();\n");
            methodsBuilder.append("\t\t\tString responseBody = \"\";\n");
            methodsBuilder.append("\t\t\tHttpStatus responseStatus;\n\n");

            //script of body method

            ResultValue result = api.getResultValue();
            String resultStatus = result.getStatus().name().split(" ")[0];
            String responseBody = result.getBody();

            //substitution of params when we got response-body ${} matches , TO-DO: refactor smart substitution

            Pattern substitutionPattern = Pattern.compile("\\$\\{\\w+\\}");
            matcher = substitutionPattern.matcher(responseBody);

            while (matcher.find()) {
                String substitute = matcher.group().substring(2, matcher.group().length() - 1);
                methodsBuilder.append(String.format("\t\t\tcontext.put(\"%s\", %s);\n", substitute, substitute));
            }

            for(SubContext ctx : context.getSubContexts()) {
                methodsBuilder.append(String.format("\t\t\tcontext.put(\"%s\", %s);\n", ctx.getName(), ctx.getName()));
            }

            methodsBuilder.append(String.format("\t\t\tresponseBody = \"%s\";\n", responseBody));
            methodsBuilder.append(String.format("\t\t\tresponseStatus = HttpStatus.%s;\n", resultStatus));
            methodsBuilder.append(String.format("\t\t\tresponseBody = context.substitude(responseBody);\n"));

            ScriptModel apiScript = api.getScript();
            methodsBuilder.append(String.format("\t\t\t%s\n", apiScript.getCode()));
            //check on async
            freeContextWhenNoAsync(api, methodsBuilder);

            methodsBuilder.append(String.format("\t\t\treturn Mono.just(new ResponseEntity<String>(responseBody, responseHeaders_%s , responseStatus));\n\t\t})", api.getName()));
            methodsBuilder.append(String.format(".delaySubscription(Duration.ofMillis(delay_%s.get()))", api.getName()));
            asyncTailAppend(api, methodsBuilder);

            methodsBuilder.append("\t}\n");
        }
    }

    private void freeContextWhenNoAsync(Api api, StringBuilder methodsBuilder) {
        AsyncApi asyncApi = api.getAsyncApi();

        if (asyncApi == null)
            methodsBuilder.append(String.format("\t\t\tcontext.release();\n"));
    }

    private void asyncTailAppend(Api api, StringBuilder methodsBuilder) {
        AsyncApi asyncApi = api.getAsyncApi();

        if (asyncApi == null)
            methodsBuilder.append(";\n"); // end of Mono method, need ;
        else {
            methodsBuilder.append("\n\t\t.doOnSuccess(c -> {\n");
            //methodsBuilder.append(String.format("\t\t\tlogger.info(\"async %s\");\n", api.getAsyncApi().getName())); //log info

            //async api contexts
            MainContext asyncApiContext = asyncApi.getContext();

            for(SubContext subContext : asyncApiContext.getSubContexts()) {
                String contextValue = "%s";

                if (subContext.getType().equals(String.class))
                    contextValue = "\"" + contextValue + "\"";

                contextValue = String.format(contextValue, subContext.getValue());
                methodsBuilder.append(String.format("\t\t\t%s %s = %s;\n", subContext.getType().getSimpleName(), subContext.getName(), contextValue.toString()));
            }

            methodsBuilder.append(String.format("\t\t\t%s\n", asyncApiContext.getCode()));

            //async api query-params

            for(Param queryParam : asyncApi.getResultValue().getQueryParams())
                methodsBuilder.append(String.format("\t\t\tString %s = \"%s\";\n", queryParam.getName(), queryParam.getValue()));

            if(needsBody(asyncApi)) {
                methodsBuilder.append(String.format("\t\t\tString requestBody = \"%s\";\n", asyncApi.getResultValue().getBody()));
            }

            //TODO:
            // async: add headers

            methodsBuilder.append(String.format("\t\t\tString requestPath=\"%s\";\n", asyncApi.getPath()));
            //methodsBuilder.append(String.format("\t\t")); set headers!

            methodsBuilder.append(String.format("\t\t%s", asyncApi.getScript().getCode()));

            //substitution
            if(needsBody(asyncApi)) {
                methodsBuilder.append(String.format("\t\trequestBody = context.substitude(requestBody);\n"));
            }


            methodsBuilder.append(String.format("\tDisposable clientRequest = this.asyncWebClient_%s.%s()\n", asyncApi.getName(),
                                                asyncApi.getRequestMethod().toString().toLowerCase()));

            //async request path builder: path and query params
            StringBuilder pathParamsAppendBuilder = new StringBuilder();

            Pattern pathPattern = Pattern.compile("\\{\\w+\\}");
            Matcher matcher = pathPattern.matcher(asyncApi.getPath());

            while(matcher.find()) {
                String substituteParam = matcher.group().substring(1, matcher.group().length() - 1);
                pathParamsAppendBuilder.append(", " + substituteParam);
            }

            methodsBuilder.append(String.format("\t\t\t\t\t\t\t.uri(requestPath%s)\n", pathParamsAppendBuilder));

            //headers
            //TODO:
            // add custom header or header substitution
            for(Map.Entry<String, String> header : api.getAsyncApi().getResultValue().getHeaders().entrySet())
                methodsBuilder.append(String.format("\t\t\t\t\t\t\t.header(\"%s\", \"%s\")\n", header.getKey(), header.getValue()));

            if(needsBody(asyncApi)) {
                methodsBuilder.append(String.format("\t\t\t\t\t\t\t.bodyValue(requestBody)\n"));
            }

            methodsBuilder.append(String.format("\t\t\t\t\t\t\t.exchange()\n"));
            methodsBuilder.append(String.format("\t\t\t\t\t\t\t.doOnSuccess(asyncResponse -> {\n"));

            appendAsyncOnSuccessStatusCheck(methodsBuilder, asyncApi.getAccepting());

            methodsBuilder.append(String.format("\t\t\t\t\t\t\t})\n"));

            methodsBuilder.append(String.format("\t\t\t\t\t\t\t.subscribe();\n\n"));
            methodsBuilder.append(String.format("\t\t\tcontext.release();\n"));

            methodsBuilder.append(String.format("\t\t});\n"));
        }
    }

    private void asyncWebClientContext(Api api, StringBuilder contextBuilder) {
        final AsyncApi asyncApi = api.getAsyncApi();

        if (asyncApi != null) {
            contextBuilder.append(String.format("\tprivate final WebClient asyncWebClient_%s;\n", asyncApi.getName()));
        }
    }

    private boolean needsBody(AsyncApi api) {
        switch (api.getRequestMethod()) {
            case PUT:
            case PATCH:
            case POST:
                return true;
            default:
                return false;
        }
    }

    private void appendAsyncOnSuccessStatusCheck(StringBuilder methodsBuilder, HttpStatus[] onStatuses) {
        if (onStatuses.length > 0) {
            methodsBuilder.append(String.format("\t\t\t\t\t\t\t\tswitch(asyncResponse.statusCode()) {\n"));

            for(HttpStatus onStatus : onStatuses) {
                methodsBuilder.append(String.format("\t\t\t\t\t\t\t\t\tcase %s:\n", onStatus.name()));
            }

            methodsBuilder.append(String.format("\t\t\t\t\t\t\t\t\t\tbreak;\n"));
            methodsBuilder.append(String.format("\t\t\t\t\t\t\t\t\tdefault:\n"));

            // TODO:
            //  add error handlers hear

            methodsBuilder.append(String.format("\t\t\t\t\t\t\t\t\t\tbreak;\n"));
            methodsBuilder.append(String.format("\t\t\t\t\t\t\t\t}\n"));
        }
    }
}