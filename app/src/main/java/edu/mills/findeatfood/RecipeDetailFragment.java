package edu.mills.findeatfood;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecipeDetailFragment extends Fragment {
    private long recipeDetailId;
    private String recipeDetailString = "recipeDetailId";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            recipeDetailId = savedInstanceState.getLong(recipeDetailString);
        }
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong(recipeDetailString, recipeDetailId);
    }

    public void setWorkout(long id) {
        this.recipeDetailId = id;
    }


}
