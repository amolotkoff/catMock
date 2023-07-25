package com.amolotkoff.mocker.parser.service.async;

import com.amolotkoff.mocker.file.FileUtil;
import com.amolotkoff.mocker.parser.model.Import;
import com.amolotkoff.mocker.parser.model.ScriptModel;
import com.amolotkoff.mocker.parser.model.api.AsyncApi;
import com.amolotkoff.mocker.parser.model.params.Param;
import com.amolotkoff.mocker.parser.service.*;
import com.amolotkoff.mocker.parser.service.delay.DelayParser;
import com.amolotkoff.mocker.util.DelayContainer;
import com.amolotkoff.mocker.util.IDelayFactory;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;

public class AsyncParser {
    private final Logger logger;
    private final Object map;
    private final String path;

    public AsyncParser(Object map, String path) {
        this.logger = LogManager.getRootLogger();
        this.map = map;
        this.path = path;
    }

    public AsyncApi Parse() throws Exception {
        logger.info("\tparsing async...");

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
        Param[] params = new ParamsParser(asyncMap, parentPath).Parse();
        String responseBody = new BodyValueParser(asyncMap, parentPath).Parse();
        HttpStatus[] accepting = new AcceptStatusParser(asyncMap, parentPath).Parse();
        HashMap<String, String> resultHeaders = new HeadersParser(asyncMap).Parse();

        return new AsyncApi(FileUtil.GetBaseFileName(path, true),
                            requestPath,
                            requestMethod,
                            produces,
                            resultHeaders,
                            delayContainer,
                            script,
                            params,
                            responseBody,
                            imports,
                            accepting);
    }
}