package com.amolotkoff.util;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PathBuilder {

    private File path;

    public PathBuilder() {
        path = null;
    }

    public PathBuilder(String append) {
        this.path = new File(append);
    }

    @Override
    public String toString() {
        return path.getPath();
    }

    public PathBuilder append(String append) {
        this.path = path == null ? new File(append)
                                 : new File(path, append);
        return this;
    }

    public PathBuilder parent() {
        this.path = path.getParentFile();
        return this;
    }
}