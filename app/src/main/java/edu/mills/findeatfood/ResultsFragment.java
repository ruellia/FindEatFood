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

/**
 * Shows list of recipe results returned from the API
 */
public class ResultsFragment extends ListFragment {

    private ResultsListListener listener;
    private List<Matches> recipesList = new ArrayList<Matches>();
    private List<String> ingredients = new ArrayList<String>();
    private List<String> dietRestrictions = new ArrayList<String>();
    private List<String> allergyRestrictions = new ArrayList<String>();
    private ProgressDialog progress;
    private int startPosition;

    /**
     * Constructor for results fragment. This constructor uses
     * setRetainInstance(true) to save the list of recipes upon device rotation.
     */
    public ResultsFragment(){
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle recipeBundle = getArguments();
        dietRestrictions = recipeBundle.getStringArrayList(MainActivity.DIET_RESTRICTIONS);
        allergyRestrictions = recipeBundle.getStringArrayList(MainActivity.ALLERGY_RESTRICTIONS);
        ingredients = Arrays.asList(recipeBundle.getStringArray(MainActivity.INGREDIENTS));
        new SearchRecipesTask().execute();
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
                new SearchRecipesTask().execute();;
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

    private class SearchRecipesTask extends AsyncTask<Void, Void, SearchRecipes> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(getActivity());
            progress.setMessage(getString(R.string.getting_recipes));
            progress.setTitle(R.string.loading);
            progress.setIndeterminate(false);
            progress.setCancelable(true);
            progress.show();
        }

        @Override
        protected SearchRecipes doInBackground(Void... params) {
            SearchRecipes recipes = HttpRequestUtilities.searchRecipes(ingredients, dietRestrictions,
                    allergyRestrictions, startPosition);
            for (Matches match : recipes.matches) {
                recipesList.add(match);
            }
            return recipes;
        }

        @Override
        protected void onPostExecute(final SearchRecipes params) {
            List<String> names = new ArrayList<String>();
            for (Matches m : recipesList) {
                names.add(m.recipeName);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity(), R.layout.row, android.R.id.text1,
                    names);
            setListAdapter(adapter);
            progress.dismiss();
        }
    }
}