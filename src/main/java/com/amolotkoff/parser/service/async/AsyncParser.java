package com.amolotkoff.parser.service.async;

import com.amolotkoff.mocker.file.Util;
import com.amolotkoff.parser.model.ScriptModel;
import com.amolotkoff.parser.model.api.AsyncApi;
import com.amolotkoff.parser.model.context.MainContext;
import com.amolotkoff.parser.model.params.Param;
import com.amolotkoff.parser.model.result.AsyncResultValue;
import com.amolotkoff.parser.service.*;
import com.amolotkoff.parser.service.delay.DelayParser;
import com.amolotkoff.mocker.util.DelayContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
public class AsyncParser {
    private final Object map;
    private final String path;

    public AsyncParser(Object map, String path) {
        this.map = map;
        this.path = path;
    }

    public AsyncApi Parse() throws Exception {
        log.info("\tparsing async...");

        if (!com.amolotkoff.parser.service.Util.has(map, "async"))
            return null;

        String parentPath = Paths.get(path).getParent().toString();
        HashMap<String, Object> asyncMap = com.amolotkoff.parser.service.Util.get(map, "async");

        DelayContainer delayContainer = new DelayParser(asyncMap).Parse();
        RequestMethod requestMethod = new RequestMethodParser(asyncMap).Parse();
        String requestPath = new PathParser(asyncMap).Parse();
        ScriptModel script = new ScriptParser(asyncMap).Parse();
        Import imports = new ImportParser(asyncMap).Parse();
        Collection<String> produces = new ProducerConsumerParser(asyncMap, ProducerConsumerParser.Type.PRODUCES).Parse();
        Param[] params = new QueryParamsParser(asyncMap, parentPath).Parse();
        String responseBody = new BodyValueParser(asyncMap, parentPath).Parse();
        HttpStatus[] accepting = new AcceptStatusParser(asyncMap, parentPath).Parse();
        HashMap<String, String> resultHeaders = new HeadersParser(asyncMap, parentPath).Parse();
        MainContext mainContext = new ContextParser(asyncMap, parentPath).Parse();
        AsyncResultValue resultValue = new AsyncResultParser(asyncMap, parentPath).Parse();

        return new AsyncApi(Util.GetBaseFileName(path, true),
                            requestPath,
                            requestMethod,
                            produces,
                            delayContainer,
                            script,
                            mainContext,
                            resultValue,
                            imports,
                            accepting);
    }
}