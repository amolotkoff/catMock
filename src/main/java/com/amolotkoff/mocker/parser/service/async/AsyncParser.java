package com.amolotkoff.mocker.parser.service.async;

import com.amolotkoff.mocker.file.FileUtil;
import com.amolotkoff.mocker.parser.model.Import;
import com.amolotkoff.mocker.parser.model.ScriptModel;
import com.amolotkoff.mocker.parser.model.api.AsyncApi;
import com.amolotkoff.mocker.parser.model.context.MainContext;
import com.amolotkoff.mocker.parser.model.params.Param;
import com.amolotkoff.mocker.parser.model.result.AsyncResultValue;
import com.amolotkoff.mocker.parser.service.*;
import com.amolotkoff.mocker.parser.service.delay.DelayParser;
import com.amolotkoff.mocker.util.DelayContainer;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import lombok.*;

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

        if (!Util.has(map, "async"))
            return null;

        String parentPath = Paths.get(path).getParent().toString();
        HashMap<String, Object> asyncMap = Util.get(map, "async");

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

        return new AsyncApi(FileUtil.GetBaseFileName(path, true),
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