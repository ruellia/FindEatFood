package edu.mills.findeatfood;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import edu.mills.findeatfood.models.Recipe;

/**
 * Provides details about the recipe by giving its name, prep time, ingredients, etc.
 * A checkbox is included that allows for the user to add and remove items from the database.
 * This checkbox serves the purpose of allowing for the user to 'favorite' recipes.
 */

public class RecipeDetailFragment extends Fragment {

    private ProgressDialog progress;
    private SQLiteDatabase db;
    private Recipe recipe;
    private Boolean favoritesCBVal = false;
    private CheckBox favoritesCB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle argumentData = getArguments();
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        new GetRecipeTask().execute(argumentData.getString(MainActivity.RECIPE_ID));
        favoritesCB = (CheckBox) view.findViewById(R.id.favorite_checkbox);
        // Inserts or deletes recipe from database
        favoritesCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favoritesCB.isChecked()) {
                    new InsertRecipeTask().execute();
                    favoritesCBVal = true;
                } else {
                    new DeleteRecipeTask().execute();
                    favoritesCBVal = false;
                }
            }
        });
        return view;
    }

    private class RecipeWrapper {
        public Recipe recipe;
        public Boolean isRecipeInDB;
    }

    private class GetRecipeTask extends AsyncTask<String, Void, RecipeWrapper> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(getActivity());
            progress.setMessage(getString(R.string.version));
            progress.setTitle(getString(R.string.loading));
            progress.setIndeterminate(false);
            progress.setCancelable(true);
            progress.show();
        }

        @Override
        protected RecipeWrapper doInBackground(String... params) {
            db = new SQLiteRecipeOpenHelper(getActivity()).getWritableDatabase();
            recipe = HttpRequestUtilities.getRecipe(params[0]);
            RecipeWrapper recipeWrapper = new RecipeWrapper();
            recipeWrapper.recipe = recipe;
            recipeWrapper.isRecipeInDB = RecipeDatabaseUtilities.searchForRecipe(db, recipe.id);
            return recipeWrapper;
        }

        @Override
        protected void onPostExecute(final RecipeWrapper recipeWrapper) {
            progress.dismiss();
            TextView name = (TextView) getActivity().findViewById(R.id.recipe_name);
            name.setText(recipeWrapper.recipe.name);
            TextView prepTime = (TextView) getActivity().findViewById(R.id.recipe_prep_time);
            prepTime.setText(getString(R.string.cookTime) + recipeWrapper.recipe.totalTime);
            TextView ingredients = (TextView) getActivity().findViewById(R.id.recipe_ingredients);
            String joined = TextUtils.join("\n", recipeWrapper.recipe.ingredientLines);
            ingredients.setText(joined);
            TextView rating = (TextView) getActivity().findViewById(R.id.rating);
            rating.setText(getString(R.string.rating) + recipeWrapper.recipe.rating);

            ImageView recipeIV = (ImageView) getActivity().findViewById(R.id.recipeIV);
            Glide.with(getActivity()).load(recipeWrapper.recipe.getImageURL())
                    .centerCrop()
                    .into(recipeIV);

            Button directionsButton = (Button) getActivity().findViewById(R.id.recipe_instructions);
            directionsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(recipeWrapper.recipe.getSourceURL()));
                    startActivity(browserIntent);
                }
            });
            if (recipeWrapper.isRecipeInDB) {
                favoritesCBVal = true;
                favoritesCB.setChecked(favoritesCBVal);
            }
        }
    }

    private class InsertRecipeTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            RecipeDatabaseUtilities.insertRecipe(db, recipe.name, recipe.id);
            return null;
        }
    }

    private class DeleteRecipeTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            RecipeDatabaseUtilities.deleteRecipe(db, recipe.id);
            return null;
        }
    }
}