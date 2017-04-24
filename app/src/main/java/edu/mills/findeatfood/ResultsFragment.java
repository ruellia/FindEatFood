package edu.mills.findeatfood;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ResultsFragment extends ListFragment {

    static interface ResultsListListener {
        void onRecipeClicked(long id);
    };
    private ResultsListListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String[] tempValues = {"Recipe One", "Recipe Two", "Recipe Three"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(), android.R.layout.simple_list_item_1,
                tempValues);
        setListAdapter(adapter);
        return inflater.inflate(R.layout.results, container, false);
        // keeping this here for future reference as I'm not 100% on using bundle
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (ResultsListListener) activity;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (listener != null) {
            listener.onRecipeClicked(id);
        }
    }
}
