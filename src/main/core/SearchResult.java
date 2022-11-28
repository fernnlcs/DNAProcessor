package main.core;

import main.structures.PriorityQueue;
import main.utils.SortMode;

public class SearchResult {
    public int comparisons = 0;
    public PriorityQueue<Integer> indexesFound = new PriorityQueue<>(SortMode.DESCENDING);

    public int[] getIndexesFound() {
        int size = this.indexesFound.size();
        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = this.indexesFound.remove();
        }

        return result;
    }
}
