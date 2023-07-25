package com.amolotkoff.mocker.util;

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