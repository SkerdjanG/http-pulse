package com.skerdy.httpulse.utils;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ResourcesUtils {

    private ResourcesUtils() {}

    public static String getTextFromResources(String path) {
        try {
            StringWriter writer = new StringWriter();
            InputStream requestsStream = new ClassPathResource(path).getInputStream();
            IOUtils.copy(requestsStream, writer, Charset.defaultCharset());
            String rawText = writer.toString();
            requestsStream.close();
            writer.close();
            return rawText;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getPulseRequestFilesInActiveDirectory(String activeDirectory) {
        var activeDirectoryFile = new File(activeDirectory);
        File[] files = activeDirectoryFile.listFiles();
        var result = new ArrayList<String>();
        if (files == null || files.length == 0) {
            return new ArrayList<>();
        }
        for (File file : files) {
            if (FilenameUtils.getExtension(file.getName()).equals("pulse")) {
                result.add(file.getName());
            }
        }
        return result;
    }

    public static String getRawTextFromPulseFile(String pulseFile) {
        try {
            var file = new File(pulseFile);
            StringWriter writer = new StringWriter();
            InputStream requestsStream = new FileInputStream(file);
            IOUtils.copy(requestsStream, writer, Charset.defaultCharset());
            String rawText = writer.toString();
            requestsStream.close();
            writer.close();
            return rawText;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
