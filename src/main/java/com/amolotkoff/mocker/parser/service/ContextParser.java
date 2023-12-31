package com.amolotkoff.mocker.parser.service;

import com.amolotkoff.mocker.file.FileUtil;
import com.amolotkoff.mocker.parser.model.context.MainContext;
import com.amolotkoff.mocker.parser.model.context.SubContext;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ContextParser {

    private final Object map;
    private final String path;

    public ContextParser(Object map, String path) {
        this.map = map;
        this.path = path;
    }

    public MainContext Parse() throws Exception {
        log.info("\tparse context...");

        HashMap<String, Object> mainContext = Util.<HashMap<String, Object>>get(map, "context", false, null);

        if (mainContext == null)
            return new MainContext(new SubContext[0], "");

        String code = Util.<String>get(mainContext, "script", false, "");
        List<SubContext> models = new ArrayList<>();

        for(Map.Entry<String, Object> varEntry : mainContext.entrySet()) {
            String contextName = varEntry.getKey();

            if (!contextName.equals("script")) {
                Object contextValue = varEntry.getValue();

                if (Util.isMap(contextValue)) {
                    HashMap<String, Object> contextMap = (HashMap<String, Object>) contextValue;

                    if (contextMap.containsKey("file")) {
                        Path filePath = Paths.get(path, contextMap.get("file").toString());
                        String file = FileUtil.Load(filePath);
                        models.add(new SubContext(contextName, String.class, file));
                    }
                }
                else
                    models.add(new SubContext(contextName, contextValue.getClass(), contextValue));
            }
        }

        SubContext[] asArray = new SubContext[models.size()];
        asArray = models.toArray(asArray);

        log.info("\tsuccess...");

        return new MainContext(asArray, code);
    }
}