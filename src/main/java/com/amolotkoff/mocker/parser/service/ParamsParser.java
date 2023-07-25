package com.amolotkoff.mocker.parser.service;

import com.amolotkoff.mocker.file.FileUtil;
import com.amolotkoff.mocker.parser.model.params.Param;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParamsParser {
    private final Logger logger;
    private final Object map;
    private final String path;

    public ParamsParser(Object map, String path) {
        this.logger = LogManager.getRootLogger();
        this.map = map;
        this.path = path;
    }

    public Param[] Parse() throws Exception {
        if (!Util.has(map, "params"))
            return new Param[0];

        HashMap<String, Object> params = Util.get(map, "params");
        List<Param> result = new ArrayList<>();

        for(Map.Entry<String, Object> param : params.entrySet()) {
            if (param.getKey().equals("script")) {
                continue;
            }

            if (param.getValue() instanceof String) {
                result.add(new Param(param.getKey(), param.getValue().toString()));
            }
            else if (Util.isMap(param.getValue())) {
                if (Util.has(param.getValue(), "script")) {
                    String script = Util.get(param.getValue(), "script");
                    result.add(new Param(param.getKey(), script));
                }
                else if (Util.has(param.getValue(), "file")) {
                    String filePath = Util.get(param.getValue(), "file");
                    String file = FileUtil.Load(Paths.get(path, filePath));
                    file = FileUtil.FormatFile(file, FileUtil.GetBaseFileName(filePath, false));
                    result.add(new Param(param.getKey(), file));
                }
            }
        }

        return result.toArray(new Param[result.size()]);
    }
}