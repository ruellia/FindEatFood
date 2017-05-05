package edu.mills.findeatfood;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Displays an editText and button. Clicking the next button will parse the string from the
 * editText into a string array, add the ingredients to the recipe API call, and launch
 * {@link DietaryFragment}.
 */
public class IngredientsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        return view;
    }
}

