package com.amolotkoff.mocker.util;

import com.amolotkoff.mocker.serializers.StaticDelayFactorySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = StaticDelayFactorySerializer.class)
public class StaticDelayFactory implements IDelayFactory {
    private final DelayKey value;

    public StaticDelayFactory(DelayKey value) {
        this.value = value;
    }

    @Override
    public long get() {
        return value.Value;
    }

    @Override
    public String toString() {
        return "base";
    }
}