package com.amolotkoff.parser.service.delay;

import com.amolotkoff.parser.service.Util;
import com.amolotkoff.mocker.util.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class DelayParser {

    private Object map;

    public DelayParser(Object map) {
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