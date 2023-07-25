package com.amolotkoff.mocker.util;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentLinkedDeque;

@Service
public class ContextService {
    private final ConcurrentLinkedDeque<Context> contexts = new ConcurrentLinkedDeque<>();

    public Context getContext() {
        if (contexts.size() > 0)
            return contexts.getLast();

        return new Context(this);
    }

    public void ReturnToStack(Context context) {
        contexts.push(context);
    }
}