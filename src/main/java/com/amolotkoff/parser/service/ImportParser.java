package com.amolotkoff.parser.service;

import com.amolotkoff.parser.model.Import;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ImportParser {
    private final Logger logger;
    private final Object map;

    public ImportParser(Object map) {
        this.logger = LogManager.getRootLogger();
        this.map = map;
    }

    public Import Parse() throws Exception {
        logger.info("\tparse imports...");

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