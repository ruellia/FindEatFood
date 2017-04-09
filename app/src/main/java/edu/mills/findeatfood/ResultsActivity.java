package edu.mills.findeatfood;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class ResultsActivity extends ListActivity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] values = {"Recipe 1", "Recipe Two", "Recipe Three"};

        setListAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, values));

        setContentView(R.layout.results);
    }
}