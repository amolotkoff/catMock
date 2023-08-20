package com.amolotkoff.mocker.file;

import com.amolotkoff.Application;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.boot.system.ApplicationHome;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileUtil {
    public static final File HOME_DIRECTORY = new ApplicationHome(Application.class).getDir();

    private static final Logger logger = LogManager.getRootLogger();

    public static String Load(Path path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path.toString()));
            StringBuilder sb = new StringBuilder();

            try {
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
            }
            finally {
                br.close();
            }

            return sb.toString();
        }
        catch (FileNotFoundException e) {
            logger.error(String.format("file at path:%s not found!", path));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }

        return "";
    }

    public static String GetBaseFileName(String filePath, boolean noSpaces) {
        int extIndex = filePath.indexOf('.');
        int nameIndex = filePath.lastIndexOf('\\');

        String result = filePath.substring(nameIndex + 1, extIndex);

        if (noSpaces)
            result = result.replaceAll("\\s+", "");

        return result;
    }

    public static String FormatFile(String file, String extension) {
        file = file.trim();

        switch (extension) {
            case ".html":
                return StringEscapeUtils.escapeHtml4(file);
            case ".xml":
                return StringEscapeUtils.escapeXml11(file);
            case ".json":
                return StringEscapeUtils.escapeJson(file);
            default:
                return StringEscapeUtils.escapeJava(file);
        }
    }

    public static Stream<Path> parseDirectory(Path dir, String...ext) {
        try {
            return Files.find(dir, Integer.MAX_VALUE,
                    (path, attr) -> {

                        for (String ex : ext)
                            if (path.toString().endsWith(ex))
                                return true;

                        return false;
                    });
        }
        catch (Exception e) { }

        return Stream.of();
    }
}