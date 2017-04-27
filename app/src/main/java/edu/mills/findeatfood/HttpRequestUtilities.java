package edu.mills.findeatfood;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;

import edu.mills.findeatfood.models.Recipe;
import edu.mills.findeatfood.models.ValContainer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public final class HttpRequestUtilities {
    private HttpRequestUtilities() {
    }

    // Retrofit interfaces
    private static interface YummlyInterface {
        // Request method and URL specified in the annotation
        // Callback for the parsed response is the last parameter

        @GET("recipe/{recipeId}?_app_id=dac3087f&_app_key=3fe7a0e08b8fc3738d09e764e4f12dcd")
        Call<Recipe> getRecipe(@Path("recipeId") String recipeId);
    }

    // call this method only from an AsyncTask!!!
    public static Recipe getRecipe(String recipeId) {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Recipe> jsonAdapter = moshi.adapter(Recipe.class);
        final String BASE_URL = "http://api.yummly.com/v1/api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create().asLenient())
                .build();
        YummlyInterface service = retrofit.create(YummlyInterface.class);
        Call<Recipe> call = service.getRecipe(recipeId);
        ValContainer<Recipe> recipeContainer = new ValContainer<Recipe>();
        try {
            recipeContainer.setVal(call.execute().body());
            System.out.println(recipeContainer.getVal());
        } catch (Exception e) {
            // handle this error (an error will always occur if you call this method from main thread)
        }
        return recipeContainer.getVal();
    }
}