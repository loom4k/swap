package me.loom4k.engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {
    private Utils() {
        // Utility class
    }

    public static String readFile(String filepath) {
        String string;
        try {
            string = new String(Files.readAllBytes(Paths.get(filepath)));
        } catch(IOException exception) {
            throw new RuntimeException("Error reading file [" + filepath + "]", exception);
        }

        return string;
    }

}
