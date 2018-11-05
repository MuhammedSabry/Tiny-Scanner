package sabry.muhammed;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        printInitialMessage();
        Scanner scanner = new Scanner(System.in);
        String inputMethod = scanner.nextLine();
        int choice = -1;
        choice = getChoice(scanner, inputMethod, choice);
        StringBuilder codeBuilder = new StringBuilder();
        //In case it's a file
        if (choice == 1)
            getCodeFromFile(scanner, codeBuilder);
            //In case he is going to write
        else
            getCodeFromCommandLine(scanner, codeBuilder);

        List<Token> tokens = Tokenizer.getExpressionTokenizer().tokenize(codeBuilder.toString());
        writeCompiledCode(tokens);
        System.out.println("For the output file, it's in the same folder as the program\nnamed scannedCode.txt");
        System.out.println("Enter any key to exit");
        scanner.nextLine();
    }

    private static void writeCompiledCode(List<Token> tokens) {
        System.out.println("The code has been read successfully");
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(new File("scannedCode.txt")), "utf-8"))) {
            tokens.forEach(token -> {
                try {
                    writer.write(token.toString() + " -> " + token.getTokenType()+"\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            // Report
        }
    }

    private static void getCodeFromFile(Scanner scanner, StringBuilder codeBuilder) throws InterruptedException {
        System.out.println("Please enter the full path of your txt file containing the code");
        Thread.sleep(1000);
        System.out.print("File path:");
        try {
            Scanner fileScanner = new Scanner(new FileInputStream(new File(scanner.nextLine())));
            while (fileScanner.hasNextLine())
                codeBuilder.append(fileScanner.nextLine()).append("\n");
        } catch (FileNotFoundException e) {
            System.out.println("Wrong file path");
            Thread.sleep(1000);
            System.out.println("Please enter correct path:");
            Thread.sleep(1000);
            getCodeFromFile(scanner, codeBuilder);
        }
    }

    private static void getCodeFromCommandLine(Scanner scanner, StringBuilder codeBuilder) throws InterruptedException {
        System.out.println("The program will keep accepting code lines from you till the word \"end\"");
        Thread.sleep(1000);
        System.out.println("Your code:");
        String line = scanner.nextLine();
        while (!line.trim().toLowerCase().equals("end")) {
            codeBuilder.append(line).append("\n");
            line = scanner.nextLine();
        }
        codeBuilder.append(line);
    }

    private static int getChoice(Scanner scanner, String inputMethod, int choice) {
        try {
            choice = Integer.valueOf(inputMethod);
        } catch (Exception ignored) {
        }
        while (choice != 1 && choice != 2) {
            System.out.println("Wrong choice.\nPlease pick either of the methods by entering 1 or 2");
            inputMethod = scanner.nextLine();
            try {
                choice = Integer.valueOf(inputMethod);
            } catch (Exception ignored) {
            }
        }
        return choice;
    }

    private static void printInitialMessage() throws InterruptedException {
        System.out.println("Welcome to TINY Language scanner...");
        Thread.sleep(1000);
        System.out.println("Please choose your method of input by inserting the method's number");
        Thread.sleep(1000);
        System.out.println("1:File\n2:Write the code");
        Thread.sleep(1000);
        System.out.print("Method number= ");
    }
}
