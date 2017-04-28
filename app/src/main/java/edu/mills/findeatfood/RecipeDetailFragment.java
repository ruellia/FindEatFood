package edu.mills.findeatfood;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import edu.mills.findeatfood.models.Recipe;

public class RecipeDetailFragment extends Fragment {

    private ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle argumentData = getArguments();
        new GetRecipeTask().execute(argumentData.getString("recipeId"));
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false);
    }

    private class GetRecipeTask extends AsyncTask<String, Void, Recipe> {

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
        protected Recipe doInBackground(String... params) {
            return HttpRequestUtilities.getRecipe(params[0]);
        }

        @Override
        //TODO: populate image
        protected void onPostExecute(final Recipe params) {
            progress.dismiss();
            TextView name = (TextView) getActivity().findViewById(R.id.recipe_name);
            name.setText(params.name);
            TextView prepTime = (TextView) getActivity().findViewById(R.id.recipe_prep_time);
            prepTime.setText(params.totalTime);
            String[] temp = {"A", "b", "c",  "A", "b", "c","A", "b", "c","A", "b", "c","A", "b", "c","A", "b", "c","A", "b", "c","A", "b", "c","A", "b", "c","A", "b", "c","A", "b", "c","A", "b", "c","A", "b", "c","A", "b", "c","A", "b", "c","A", "b", "c","A", "b", "c","A", "b", "c","A", "b", "c","A", "b", "c","A", "b", "c","A", "b", "c","A", "b", "c","A", "b", "c"};
            List<String> test = Arrays.asList(temp);
            String joined = TextUtils.join("\n", temp);
            TextView ingredients = (TextView) getActivity().findViewById(R.id.recipe_ingredients);
            //String joined = TextUtils.join("\n", params.ingredientLines);
            ingredients.setText(joined);
            TextView rating = (TextView) getActivity().findViewById(R.id.rating);
            rating.setText("Rating: " + params.rating);
            Button directionsButton = (Button) getActivity().findViewById(R.id.recipe_instructions);
            directionsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(params.getSourceURL()));
                    startActivity(browserIntent);
                }
            });
        }
    }
}
