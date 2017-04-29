package edu.mills.findeatfood;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
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

public class RecipeDetailFragment extends Fragment {

    private ProgressDialog progress;
    private SQLiteDatabase db;
    private Recipe recipe;
    Boolean favoritesCBVal = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle argumentData = getArguments();
        new GetRecipeTask().execute(argumentData.getString("recipeId"));
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false);
    }

    private class RecipeWrapper{
        public Recipe recipe;
        public Cursor recipeCursor;
    }

    private class GetRecipeTask extends AsyncTask<String, Void, RecipeWrapper> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(getActivity());
            progress.setMessage("Getting the latest version of your recipe.");
            progress.setTitle("Loading...");
            progress.setIndeterminate(false);
            progress.setCancelable(true);
            progress.show();
        }

        @Override
        protected RecipeWrapper doInBackground(String... params) {
            db = new SQLiteRecipeOpenHelper(getActivity()).getWritableDatabase();
            Recipe recipe = HttpRequestUtilities.getRecipe(params[0]);
            RecipeWrapper recipeWrapper = new RecipeWrapper();
            recipeWrapper.recipe = recipe;
            recipeWrapper.recipeCursor = RecipeDatabaseUtilities.searchForRecipe(db, recipe.id);
            return recipeWrapper;
        }

        @Override
        //TODO: populate image
        protected void onPostExecute(final RecipeWrapper recipeWrapper) {
            progress.dismiss();
            TextView name = (TextView) getActivity().findViewById(R.id.recipe_name);
            name.setText(recipeWrapper.recipe.name);
            TextView prepTime = (TextView) getActivity().findViewById(R.id.recipe_prep_time);
            prepTime.setText(recipeWrapper.recipe.totalTime);
            TextView ingredients = (TextView) getActivity().findViewById(R.id.recipe_ingredients);
            String joined = TextUtils.join("\n", recipeWrapper.recipe.ingredientLines);
            ingredients.setText(joined);
            TextView rating = (TextView) getActivity().findViewById(R.id.rating);
            rating.setText("Rating: " + recipeWrapper.recipe.rating);

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


            final CheckBox favoritesCB = (CheckBox) getView().findViewById(R.id.favorite_checkbox);
            if (recipeWrapper.recipeCursor.moveToNext()) {
                if ((recipeWrapper.recipe.id).equals(recipeWrapper.recipeCursor.getString(1))) {
                    favoritesCBVal = true;
                    favoritesCB.setChecked(favoritesCBVal);
                }
            }
            favoritesCB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (favoritesCB.isChecked()) {
                        if (favoritesCBVal) {
                            //do nothing because recipe is already in db
                        }
                        new InsertRecipeTask().execute();
                        favoritesCBVal = true;
                    } else {
                        if (favoritesCBVal) {
                            new DeleteRecipeTask().execute();
                            favoritesCBVal = false;
                        }
                    }
                }
            });
        }
    }

    private class InsertRecipeTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            RecipeDatabaseUtilities.insertRecipe(db, recipe.name, recipe.id);
            return null;
        }
    }

    private class DeleteRecipeTask extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Void... voids) {
            RecipeDatabaseUtilities.deleteRecipe(db, recipe.id);
            return null;
        }
    }
}
