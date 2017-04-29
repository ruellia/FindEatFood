package edu.mills.findeatfood;

import android.app.Activity;
import android.app.ListFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends ListFragment {

    private ResultsFragment.ResultsListListener listener;
    private List<String> recipes;
    private List<String> recipeIds;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recipes = new ArrayList<String>();
        recipeIds = new ArrayList<String>();
        new GetAllFavoriteRecipesTask().execute();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (ResultsFragment.ResultsListListener) activity;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (listener != null) {
            listener.onRecipeClicked(recipeIds.get(position));
        }
    }

    private class GetAllFavoriteRecipesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... Void) {
            SQLiteDatabase db = new SQLiteRecipeOpenHelper(getActivity()).getReadableDatabase();
            Cursor getAllRecipesCursor = RecipeDatabaseUtilities.getAllRecipes(db);
            while (getAllRecipesCursor.moveToNext()) {
                recipes.add(getAllRecipesCursor.getString(0));
                recipeIds.add(getAllRecipesCursor.getString(1));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void params) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,
                    recipes);
            setListAdapter(adapter);
        }
    }
}
