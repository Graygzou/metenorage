package Engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author : Matthieu Le Boucher
 */
public class Utils {
    public static String readTextResource(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get("./resources/" + filename)));
    }
}
