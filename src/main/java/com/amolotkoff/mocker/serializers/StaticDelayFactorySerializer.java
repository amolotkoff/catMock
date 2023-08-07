package com.amolotkoff.mocker.serializers;

import com.amolotkoff.mocker.util.DelayKey;
import com.amolotkoff.mocker.util.StaticDelayFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.lang.reflect.Field;

public class StaticDelayFactorySerializer extends StdSerializer<StaticDelayFactory> {

    public StaticDelayFactorySerializer() {
        this(null);
    }

    public StaticDelayFactorySerializer(Class<StaticDelayFactory> item) {
        super(item);
    }

    @Override
    public void serialize(StaticDelayFactory item, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        Long value = 0L, startValue = 0L;

        try {
            Field valueField = StaticDelayFactory.class.getField("value");
            valueField.setAccessible(true);
            DelayKey delayKey = (DelayKey)valueField.get(item);
            value = delayKey.Value;
            startValue = delayKey.getStartValue();
        }
        catch (Exception e) { }

        jgen.writeStartObject();
        jgen.writeStringField("type","base");
        jgen.writeEndObject();
    }
}
