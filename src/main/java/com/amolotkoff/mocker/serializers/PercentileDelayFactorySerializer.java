package com.amolotkoff.mocker.serializers;

import com.amolotkoff.mocker.util.DelayKey;
import com.amolotkoff.mocker.util.PercentileDelayFactory;
import com.amolotkoff.mocker.util.StaticDelayFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.lang.reflect.Field;

public class PercentileDelayFactorySerializer extends StdSerializer<PercentileDelayFactory> {

    public PercentileDelayFactorySerializer() {
        this(null);
    }

    public PercentileDelayFactorySerializer(Class<PercentileDelayFactory> item) {
        super(item);
    }

    @Override
    public void serialize(PercentileDelayFactory item, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("type","percentile");
        jgen.writeEndObject();
    }
}