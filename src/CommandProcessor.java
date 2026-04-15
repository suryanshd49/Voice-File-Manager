import javax.swing.JTextArea;

public class CommandProcessor {

    public static void process(String command, JTextArea outputArea) {

        if (command == null || command.isEmpty()) {
            outputArea.append("No command detected\n");
            return;
        }

        command = command.toLowerCase().trim();

        // Normalize voice mistakes
        command = command.replace("dot", ".");
        command = command.replace("text", "txt");
        command = command.replace("them", "demo"); // common error fix
        command = command.replace("lead", "delete");
        command = command.replace("least", "list");

        String result = "";

        if (command.contains("create")) {
            String name = extractFileName(command);
            result = FileManager.createFile(name);
        }

        else if (command.contains("delete")) {
            String name = extractFileName(command);
            result = FileManager.deleteFile(name);
        }

        else if (command.contains("list")) {
            result = FileManager.listFiles();
        }

        else {
            result = "Command not recognized.";
        }

        outputArea.append("🎤 You said: " + command + "\n");
        outputArea.append(result + "\n");
    }

    private static String extractFileName(String command) {

        String[] words = command.split(" ");

        for (String word : words) {
            if (word.contains(".")) {
                return word;
            }
        }

        // Take last meaningful word
        String name = words[words.length - 1];

        if (name.equals("file") || name.equals("create") || name.equals("delete")) {
            return "default.txt";
        }

        // Always add .txt
        return name + ".txt";
    }
}