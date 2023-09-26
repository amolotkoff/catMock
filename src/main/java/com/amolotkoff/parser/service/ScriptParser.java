package com.amolotkoff.parser.service;

import com.amolotkoff.parser.model.ScriptModel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class ScriptParser {
    private final Object map;

    public ScriptModel Parse() throws Exception {
        log.info("\tparse script...");

        Object scriptMap = Util.<String>get(map, "script");

        if (scriptMap == null)
            return new ScriptModel("","","","","");

        String imports = Util.get(scriptMap, "import", false, "");
        String action = Util.get(scriptMap, "action", false, "");
        String onStart = Util.get(scriptMap, "on-start", false, "");
        String preAction = Util.get(scriptMap, "pre-action", false, "");
        String postAction = Util.get(scriptMap, "post-action", false, "");

        return new ScriptModel(imports, action, onStart, preAction, postAction);
    }
}