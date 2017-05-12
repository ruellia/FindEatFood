package edu.mills.findeatfood;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

import edu.mills.findeatfood.models.Recipe;
import edu.mills.findeatfood.models.SearchRecipes;
import edu.mills.findeatfood.ValContainer;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Utility class for making HTTP requests to Yummly.
 * Activities and fragments should only call these methods from an AsyncTask.
 */
public final class HttpRequestUtilities {

    private static final String YUMMLY_BASE_URL = "http://api.yummly.com/v1/api/";

    private HttpRequestUtilities() {
    }

    // Retrofit interfaces to interact with Yummly.
    private static interface YummlyInterface {
        //TODO: read keys from private file

        // Request method and URL specified in the annotation.
        // Callback for the parsed response is the last parameter.
        @GET("recipe/{recipeId}?_app_id=dac3087f&_app_key=3fe7a0e08b8fc3738d09e764e4f12dcd")
        Call<Recipe> getRecipe(@Path("recipeId") String recipeId);

        @GET("recipes?_app_id=dac3087f&_app_key=3fe7a0e08b8fc3738d09e764e4f12dcd&maxResult=10")
        Call<SearchRecipes> searchRecipes(@Query("allowedIngredient") List<String> ingredients,
                                          @Query("allowedDiet") List<String> diets,
                                          @Query("allowedAllergy") List<String> allergies,
                                          @Query("start") int startPosition);
    }

    /**
     * Makes a GET request to Yummly to get recipe information.
     * @param   recipeId the recipe ID to pass to Yummly
     * @return  the Recipe associated with the passed in recipeId
     */
    public static Recipe getRecipe(String recipeId) {
        YummlyInterface service = createYummlyService(Recipe.class);
        Call<Recipe> call = service.getRecipe(recipeId);
        ValContainer<Recipe> recipeContainer = new ValContainer<Recipe>();
        try {
            recipeContainer.setVal(call.execute().body());
        } catch (Exception e) {
            // An exception will always be thrown if this method is called from the UI thread.
        }
        return recipeContainer.getVal();
    }

    /**
     * Makes a GET request to Yummly to search for recipes that fulfill passed in criteria.
     * Details on ten recipes will be sent back from Yummly.
     * @param   ingredients the ingredients that must be included in the recipes
     * @param   diets the diets that the recipes must adhere to
     * @param   allergies the allergies that the recipes must adhere to
     * @param   startPosition the position from which Yummly will return the next ten recipes
     * @return  the SearchRecipes model that includes the recipes sent back by Yummly
     */
    public static SearchRecipes searchRecipes(List<String> ingredients, List<String> diets,
                                              List<String> allergies, int startPosition) {
        YummlyInterface service = createYummlyService(SearchRecipes.class);
        Call<SearchRecipes> call = service.searchRecipes(ingredients, diets, allergies, startPosition);
        ValContainer<SearchRecipes> recipesContainer = new ValContainer<SearchRecipes>();
        try {
            recipesContainer.setVal(call.execute().body());
        } catch (Exception e) {
            // An exception will always be thrown if this method is called from the UI thread.
        }
        return recipesContainer.getVal();
    }

    private static YummlyInterface createYummlyService(Class jsonClass) {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter jsonAdapter = moshi.adapter(jsonClass);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(YUMMLY_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create().asLenient())
                .build();
        return retrofit.create(YummlyInterface.class);
    }
}