package com.amolotkoff.mocker;

import com.amolotkoff.mocker.util.DelayKey;
import com.amolotkoff.mocker.util.StaticDelayFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

public class JsonSerializes {

    @Test
    public void SerializeStaticFactory() {
        ObjectMapper mapper = new ObjectMapper();

        if (!mapper.canSerialize(StaticDelayFactory.class))
            Assert.fail();
    }
}
