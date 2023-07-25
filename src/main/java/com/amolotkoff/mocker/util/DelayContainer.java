package com.amolotkoff.mocker.util;

import java.util.HashMap;

public class DelayContainer {
    public IDelayFactory factory;
    public HashMap<String, DelayKey> keys;

    public DelayContainer(IDelayFactory factory, HashMap<String, DelayKey> keys) {
        this.factory = factory;
        this.keys = keys;
    }
}