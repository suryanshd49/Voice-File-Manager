import java.io.File;
import java.io.IOException;

public class FileManager {

    public static String createFile(String name) {
        try {
            File file = new File(name);
            if (file.createNewFile()) {
                return "File created: " + name;
            } else {
                return "File already exists.";
            }
        } catch (IOException e) {
            return "Error creating file.";
        }
    }

    public static String deleteFile(String name) {
        File file = new File(name);
        if (file.delete()) {
            return "File deleted: " + name;
        } else {
            return "File not found.";
        }
    }

    public static String listFiles() {
        File folder = new File(".");
        File[] files = folder.listFiles();

        StringBuilder result = new StringBuilder("Files:\n");

        for (File f : files) {
            result.append(f.getName()).append("\n");
        }

        return result.toString();
    }
}