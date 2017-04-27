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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.mills.findeatfood.models.Recipe;

public class FavoritesFragment extends ListFragment {

    private ResultsFragment.ResultsListListener listener;
    private List<String> recipes;
    private List<String> recipeIds;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recipes = new ArrayList<String>();
        recipeIds = new ArrayList<String>();
        SQLiteDatabase db = new SQLiteRecipeOpenHelper(getActivity()).getReadableDatabase();
        Cursor getAllRecipesCursor = RecipeDatabaseUtilities.getAllRecipes(db);
        while (getAllRecipesCursor.moveToNext()) {
            recipes.add(getAllRecipesCursor.getString(0));
            recipeIds.add(getAllRecipesCursor.getString(1));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(), android.R.layout.simple_list_item_1,
                recipes);
        setListAdapter(adapter);
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
}
