package com.amolotkoff.parser.service;

import com.amolotkoff.parser.exceptions.KeyNotFoundException;

import java.util.HashMap;

public class Util {
    public static boolean isMap(Object map) {
        return map instanceof HashMap;
    }

    public static <T> T get(Object map, String key, boolean required, T defaultValue) throws KeyNotFoundException, ClassCastException  {
        if (!(map instanceof HashMap))
            throw new ClassCastException("Object is not an instance of HashMap");

        boolean containsKey = ((HashMap<String, Object>) map).containsKey(key);

        if (!containsKey && required)
            throw new KeyNotFoundException(String.format("Key (%s) not found in map:%s", key, map));

        if (containsKey)
            return (T)((HashMap<String, Object>) map).get(key);
        else
            return defaultValue;
    }

    public static <T> T get(Object map, String key) throws Exception {
        return get(map, key, false, null);
    }

    public static <T> boolean has(Object map, String key) {
        if (!isMap(map))
            return false;

        try {
            get(map, key, true, null);
            return true;
        }
        catch (KeyNotFoundException e) {
            return false;
        }
        catch (ClassCastException e) {
            return false;
        }
    }
}