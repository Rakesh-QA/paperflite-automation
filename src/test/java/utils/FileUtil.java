package utils;

import java.io.File;

public class FileUtil {

    public static String getFilePath(String relativePath) {

        // Get project root
        String projectPath = System.getProperty("user.dir");

        // Build full path
        String fullPath = projectPath + "/src/test/resources/" + relativePath;

        // Convert to OS path
        fullPath = fullPath.replace("/", "\\");


        return fullPath;
    }
}