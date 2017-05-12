package edu.mills.findeatfood.models;

import java.util.List;

public final class SearchRecipes {
    public final List<Matches> matches;

    public SearchRecipes(List<Matches> matches) {
        this.matches = matches;
    }

    public String toString() {
        return matches.get(0).recipeName + matches.get(1).recipeName;
    }
}
