package com.amolotkoff.builder.strategy.util;

import java.util.Iterator;
import java.util.List;

import com.amolotkoff.builder.strategy.IBuilderStrategy;
import lombok.*;

public class Formatter {
    public static class FormatterBuilder {
        private final StringBuilder builder;

        public FormatterBuilder() {
            builder = new StringBuilder();
        }

        public FormatterBuilder format(String source, String... params) {
            builder.append(String.format(source, params));

            return this;
        }

        public FormatterBuilder format(IBuilderStrategy source) {
            builder.append(source.toCode());

            return this;
        }

        public FormatterBuilder format(String source, int tabs, String... params) {
            return format(String.format(source, params), tabs);
        }

        public FormatterBuilder format(String source, int tabs) {
            if (source.isEmpty())
                return this;

            String[] lines = source.split("\n");

            for(String line : lines) {
                for(int i = 0; i < tabs; i++)
                    builder.append('\t');

                builder.append(line);
                builder.append('\n');
            }

            return this;
        }

        public FormatterBuilder format(IBuilderStrategy source, int tabs) {
            return format(source.toCode(), tabs);
        }

        public <T extends IBuilderStrategy> FormatterBuilder format(Iterable<T> sources, int tabs, String delimiter) {
            int i = 0;
            for(T source : sources) {
                if (i > 0)
                    builder.append(delimiter);

                format(source.toCode(), tabs);

                i++;
            }

            return this;
        }

        public FormatterBuilder line() {
            builder.append('\n');

            return this;
        }

        @Override
        public String toString() {
            return builder.toString();
        }
    }

    public static FormatterBuilder builder() {
        return new FormatterBuilder();
    }
}