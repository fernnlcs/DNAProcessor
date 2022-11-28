package main.core;

import main.contracts.SubstringFinder;
import main.utils.Logger;

public class KMPFinder implements SubstringFinder {
    private final char[] original;
    public int[] prefixTable;
    private final Logger logger = new Logger();

    public KMPFinder(String original) {
        this.original = original.toCharArray();
        this.buildPrefixTable();
    }

    public char[] getOriginal() {
        return this.original.clone();
    }

    private void buildPrefixTable() {
        char[] original = this.getOriginal();
        this.prefixTable = new int[original.length];
        this.prefixTable[0] = 0;

        int comparingWith = 0;
        for (int i = 1; i < original.length; i++) {
           if (original[i] == original[comparingWith]) {
               comparingWith++;
           } else {
               comparingWith = 0;
           }
            prefixTable[i] = comparingWith;
        }
    }

    @Override
    public SearchResult find(String substring) {
        SearchResult result = new SearchResult();
        char[] original = this.getOriginal();
        char[] pattern = substring.toCharArray();
        int origIndex = 0; // i
        int patIndex = 0; // j
        boolean isRunning = true;

        while (isRunning) {
            if (check(original[origIndex], pattern[patIndex], result)) {
                // Deu match
                origIndex++;
                patIndex++;
            } else if (patIndex > 0) {
                // Não deu match, mas tá em vantagem
                patIndex = this.prefixTable[patIndex - 1];
            } else {
                // Não deu mesmo
                origIndex++;
            }

            // Padrão encontrado
            if (patIndex == pattern.length) {
                result.indexesFound.add(origIndex - pattern.length);
                patIndex = this.prefixTable[patIndex - 1];
            }
            isRunning = origIndex < (original.length - pattern.length);
        }

        return result;
    }

    private boolean check(char originalChar, char searchChar, SearchResult result) {
        result.comparisons++;
        return originalChar == searchChar;
    }
}
