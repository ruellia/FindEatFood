package edu.mills.findeatfood;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends ListFragment {

    private ResultsFragment.ResultsListListener listener;
    private List<String> recipes;
    private List<String> recipeIds;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        recipes = new ArrayList<String>();
        recipeIds = new ArrayList<String>();
        new GetAllFavoriteRecipesTask().execute();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        view.setBackgroundColor(getResources().getColor(android.R.color.black));
        ListView listView = getListView();
        listView.setDivider(new ColorDrawable(getResources().getColor(R.color.orange)));
        listView.setDividerHeight(1);
        int padding_in_dp = 15;  // 6 dps
        final float scale = getResources().getDisplayMetrics().density;
        int padding_in_px = (int) (padding_in_dp * scale + 0.5f);
        listView.setPadding(padding_in_px,padding_in_px,padding_in_px,padding_in_px);
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
                    getActivity().getApplicationContext(), R.layout.row, android.R.id.text1,
                    recipes);
            setListAdapter(adapter);
        }
    }
}
