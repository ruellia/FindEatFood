package edu.mills.findeatfood;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

public class RecipeDetailFragment extends Fragment {
    private long recipeDetailId;
    private String recipeDetailString = "recipeDetailId";
    private boolean pressedTwiceToUnfavorite = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            recipeDetailId = savedInstanceState.getLong(recipeDetailString);
        }
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        configureImageButton(view);
        return view;
    }

    private void configureImageButton(View view){
        CheckBox favoriteChecked = (CheckBox) view.findViewById(R.id.favorite_checkbox);

        favoriteChecked.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                if(!pressedTwiceToUnfavorite) {
                    pressedTwiceToUnfavorite = true;
                    Toast.makeText(getActivity(), "You favorited the recipe", Toast.LENGTH_SHORT).show();
                    SQLiteDatabase db = new SQLiteRecipeOpenHelper(getActivity()).getWritableDatabase();
                    RecipeDatabaseUtilities.insertRecipe(db, recipeDetailString, " " + recipeDetailId);
                    return;
                }else{
                    pressedTwiceToUnfavorite = false;
                    Toast.makeText(getActivity(), "You unfavorited the recipe", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong(recipeDetailString, recipeDetailId);
    }

    public void setRecipeId(long id) {
        this.recipeDetailId = id;
    }

}