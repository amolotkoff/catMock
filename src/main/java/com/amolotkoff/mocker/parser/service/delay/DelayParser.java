package com.amolotkoff.mocker.parser.service.delay;

import com.amolotkoff.mocker.parser.service.Util;
import com.amolotkoff.mocker.util.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.HashMap;

public class DelayParser {
    private final Logger logger;
    private Object map;

    public DelayParser(Object map) {
        this.logger = LogManager.getRootLogger();
        this.map = map;
    }

    public DelayContainer Parse() throws Exception {
        Object delay = Util.get(map, "delay");

        if (delay == null) {
            return null; // then nothing to delay
        }

        String delayType = Util.<String>get(delay, "type").toLowerCase();

        switch (delayType) {
            case "percentile": {
                int percentileMin = Util.get(delay, "min");
                int percentileMax = Util.get(delay, "max");
                int percentile = Util.get(delay, "percentile");
                int value = Util.get(delay, "value");

                DelayKey percentileMinKey = new DelayKey(percentileMin);
                DelayKey percentileMaxKey = new DelayKey(percentileMax);
                DelayKey percentileKey = new DelayKey(percentile);
                DelayKey valueKey = new DelayKey(value);

                HashMap<String, DelayKey> keys = new HashMap<>();

                keys.put("percentile", percentileKey);
                keys.put("min", percentileMinKey);
                keys.put("max", percentileMaxKey);
                keys.put("value", valueKey);

                IDelayFactory factory = new PercentileDelayFactory(percentileKey,
                        percentileMinKey,
                        percentileMaxKey,
                        valueKey);

                return new DelayContainer(factory, keys);

            }
            case "base": {
                int value = Util.get(delay, "value");
                DelayKey valueKey = new DelayKey(value);

                HashMap<String, DelayKey> keys = new HashMap<>();
                keys.put("value", valueKey);

                IDelayFactory factory = new StaticDelayFactory(valueKey);

                return new DelayContainer(factory, keys);

            }
            default:
                throw new Exception("unknown type of delay");
        }
    }
}