package main.core;

import main.contracts.SubstringFinder;
import main.utils.Logger;

public class BruteForceFinder implements SubstringFinder {
    private final char[] original;
    private final Logger logger = new Logger();

    public BruteForceFinder(String original) {
        this.original = original.toCharArray();
    }

    public SearchResult find(String substring) {
        SearchResult result = new SearchResult();

        char[] text = substring.toCharArray();

        for (int i = 0; i < this.original.length - text.length + 1; i++) {
            boolean isMatching = true;
            this.logger.log(String.valueOf(this.original));
            StringBuilder line = new StringBuilder(" ".repeat(i));

            for (int j = 0; j < text.length && isMatching; j++) {
                line.append(text[j]);
                result.comparisons++;
                if (original[i + j] != text[j]) {
                    isMatching = false;
                }
            }

            this.logger.log(line.toString());

            if (isMatching) {
                result.indexesFound.add(i);
            }
        }

        return result;
    }
}
