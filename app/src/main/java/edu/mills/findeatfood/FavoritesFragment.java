package edu.mills.findeatfood;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class FavoritesFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String[] tempValues = {"Favorite One", "Favorite Two", "Favorite Three"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(), android.R.layout.simple_list_item_1,
                tempValues);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
