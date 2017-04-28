package edu.mills.findeatfood;

import android.app.Activity;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.mills.findeatfood.models.Matches;
import edu.mills.findeatfood.models.SearchRecipes;

public class ResultsFragment extends ListFragment {

    private ResultsListListener listener;
    private List<Matches> recipesList = new ArrayList<Matches>();
    private ProgressDialog progress;
    private int startPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // mock data
        List<String> ingredients = Arrays.asList(new String[]{"garlic"});
        List<String> diets = Arrays.asList(new String[]{"390^Pescetarian", "388^Lacto vegetarian"});
        List<String> allergies = Arrays.asList(new String[]{"393^Gluten-Free"});
        // api call
        new GetRecipeTask().execute(new AsyncParams(ingredients, diets, allergies, startPosition));
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.results, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Button seeMore = (Button) getActivity().findViewById(R.id.seeMoreButton);
        seeMore.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startPosition += 10;
                // mock data
                List<String> ingredients = Arrays.asList(new String[]{"garlic"});
                List<String> diets = Arrays.asList(new String[]{"390^Pescetarian", "388^Lacto vegetarian"});
                List<String> allergies = Arrays.asList(new String[]{"393^Gluten-Free"});
                new GetRecipeTask().execute(new AsyncParams(ingredients, diets, allergies, startPosition));
            }
        });
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (ResultsListListener) activity;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (listener != null) {
            listener.onRecipeClicked(recipesList.get(position).id);
        }
    }

    static interface ResultsListListener {
        void onRecipeClicked(String recipeId);
    }

    private class GetRecipeTask extends AsyncTask<AsyncParams, Void, SearchRecipes> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(getActivity());
            progress.setMessage("Getting recipes.");
            progress.setTitle("Loading...");
            progress.setIndeterminate(false);
            progress.setCancelable(true);
            progress.show();
        }

        @Override
        protected SearchRecipes doInBackground(AsyncParams... params) {
            SearchRecipes recipes = HttpRequestUtilities.searchRecipes(params[0].ingredients, params[0].diets, params[0].allergies, params[0].startPosition);
            for (Matches match : recipes.matches) {
                recipesList.add(match);
            }
            return HttpRequestUtilities.searchRecipes(params[0].ingredients, params[0].diets, params[0].allergies, params[0].startPosition);
        }

        @Override
        protected void onPostExecute(final SearchRecipes params) {
            List<String> names = new ArrayList<String>();
            for (Matches m : recipesList) {
                names.add(m.recipeName);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_list_item_1,
                    names);
            setListAdapter(adapter);
            progress.dismiss();
        }
    }

    private class AsyncParams {
        public List<String> ingredients;
        public List<String> diets;
        public List<String> allergies;
        public int startPosition;

        public AsyncParams(List<String> ingredients, List<String> diets, List<String> allergies, int startPosition) {
            this.ingredients = ingredients;
            this.diets = diets;
            this.allergies = allergies;
            this.startPosition = startPosition;
        }
    }
}