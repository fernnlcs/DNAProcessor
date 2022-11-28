package main;

import main.core.BruteForceFinder;
import main.core.KMPFinder;
import main.core.SearchResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        File file = new File("src/assets/dna.txt");
        StringBuilder original = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                original.append(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            original.append("");
        }

        Scanner input = new Scanner(System.in);
        String pattern = choosePattern(input);

        while (pattern != null) {
            bruteForce(original.toString(), pattern);
            System.out.println();
            kmp(original.toString(), pattern);
            pattern = choosePattern(input);
        }

    }

    public static void bruteForce(String original, String pattern) {
        BruteForceFinder finder = new BruteForceFinder(original);
        SearchResult result = finder.find(pattern);
        int indexesFound = result.indexesFound.size();

        System.out.println("\t\tFORÇA BRUTA:");
        System.out.print("Texto encontrado nas posições: [");
        while (result.indexesFound.size() > 0) {
            System.out.print(result.indexesFound.remove());
            if (result.indexesFound.size() != 0) {
                System.out.print(", ");
            }
        }
        System.out.println("].");
        System.out.println(indexesFound + " ocorrências.");
        System.out.println(result.comparisons + " comparações realizadas.");
    }

    public static void kmp(String original, String pattern) {
        KMPFinder finder = new KMPFinder(original);
        SearchResult result = finder.find(pattern);
        int indexesFound = result.indexesFound.size();

        System.out.println("\t\tKMP:");
        System.out.print("Texto encontrado nas posições: [");
        while (result.indexesFound.size() > 0) {
            System.out.print(result.indexesFound.remove());
            if (result.indexesFound.size() != 0) {
                System.out.print(", ");
            }
        }
        System.out.println("].");
        System.out.println(indexesFound + " ocorrências.");
        System.out.println(result.comparisons + " comparações realizadas.");
    }

    private static String choosePattern(Scanner scanner) {
        System.out.println("Escolha o padrão que você deseja buscar: ");
        System.out.println("0 | [Sair]");
        System.out.println("1 | TATATAAGAAAAAAG");
        System.out.println("2 | AGACTCTG");
        System.out.println("3 | GAGAGCGG");
        System.out.println("4 | TCCTCCCAC");
        System.out.println("Se deseja buscar outro padrão, pode digitá-lo");

        String choice = scanner.nextLine();

        return switch (choice) {
            case "0" -> null;
            case "1" -> "TATATAAGAAAAAAG";
            case "2" -> "AGACTCTG";
            case "3" -> "GAGAGCGG";
            case "4" -> "TCCTCCCAC";
            default -> choice;
        };
    }
}
