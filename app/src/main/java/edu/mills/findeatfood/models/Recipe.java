package edu.mills.findeatfood.models;

import java.util.List;

public final class Recipe {
    public final String totalTime;
    public final String name;
    public final String id;
    public final List<String> ingredientLines;
    public final int rating;
    public final List<Images> images;

    public Recipe(String totalTime, String name, String id, List<String> ingredientLines, int rating, List<Images> images) {
        this.totalTime = totalTime;
        this.name = name;
        this.id = id;
        this.ingredientLines = ingredientLines;
        this.rating = rating;
        this.images = images;
    }

    public String toString() { // for debugging purposes
        return totalTime + name + id + rating + images.get(0).hostedLargeUrl + ingredientLines.get(0);
    }
}

class Images {
    public final String hostedLargeUrl;

    public Images(String hostedLargeUrl) {
        this.hostedLargeUrl = hostedLargeUrl;
    }
}