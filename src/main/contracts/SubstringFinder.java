package main.contracts;

import main.core.SearchResult;

public interface SubstringFinder {
    SearchResult find(String substring);
}
