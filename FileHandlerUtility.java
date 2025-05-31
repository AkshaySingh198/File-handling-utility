import java.io.*;
import java.util.Scanner;

public class FileHandlerUtility {

    private static final String FILE_PATH = "sample.txt";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            displayMenu();
            choice = Integer.parseInt(scanner.nextLine());

            try {
                switch (choice) {
                    case 1 -> writeToFileFromUser();
                    case 2 -> readFromFile();
                    case 3 -> modifyFileFromUser();
                    case 4 -> System.out.println("Exiting program. Goodbye!");
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (IOException e) {
                System.err.println("An error occurred: " + e.getMessage());
            }

            System.out.println();
        } while (choice != 4);
    }

    /**
     * Displays the menu options.
     */
    public static void displayMenu() {
        System.out.println("===== File Handler Utility =====");
        System.out.println("1. Write to File");
        System.out.println("2. Read from File");
        System.out.println("3. Modify File Content");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    /**
     * Writes user input to the file.
     */
    public static void writeToFileFromUser() throws IOException {
        System.out.println("Enter the content to write to the file (type 'END' on a new line to finish):");
        StringBuilder content = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).equalsIgnoreCase("END")) {
            content.append(line).append(System.lineSeparator());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(content.toString());
            System.out.println("File written successfully.");
        }
    }

    /**
     * Reads and displays the file content.
     */
    public static void readFromFile() throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("File not found. Please write content first.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            System.out.println("---- File Content ----");
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("----------------------");
        }
    }

    /**
     * Gets user input for text replacement and performs the modification.
     */
    public static void modifyFileFromUser() throws IOException {
        File inputFile = new File(FILE_PATH);
        if (!inputFile.exists()) {
            System.out.println("File not found. Please write content first.");
            return;
        }

        System.out.print("Enter word to replace: ");
        String target = scanner.nextLine();
        System.out.print("Enter replacement word: ");
        String replacement = scanner.nextLine();

        File tempFile = new File("temp.txt");

        try (
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String modifiedLine = currentLine.replaceAll(target, replacement);
                writer.write(modifiedLine + System.lineSeparator());
            }
        }

        if (inputFile.delete() && tempFile.renameTo(inputFile)) {
            System.out.println("File modified successfully.");
        } else {
            System.err.println("Failed to modify the file.");
        }
    }
}

