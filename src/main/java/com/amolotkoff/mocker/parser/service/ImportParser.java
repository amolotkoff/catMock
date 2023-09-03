package com.amolotkoff.mocker.parser.service;

import com.amolotkoff.mocker.parser.model.Import;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImportParser {
    private final Object map;

    public ImportParser(Object map) {
        this.map = map;
    }

    public Import Parse() throws Exception {
        log.info("\tparse imports...");

        if (!Util.has(map, "import"))
            return new Import();

        String imports = Util.get(map, "import").toString().replace(",", ";");
        StringBuilder builder = new StringBuilder();

        for(String _import : imports.split(";")) {
            _import = _import.trim();
            if (!_import.isEmpty())
                builder.append(String.format("import %s;", _import));
        }

        return new Import(builder.toString());
    }
}