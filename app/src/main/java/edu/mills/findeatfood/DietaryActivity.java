package edu.mills.findeatfood;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class DietaryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietary);

        EditText dietTypeET = (EditText) findViewById(R.id.dietTypeET);
        EditText allergiesET = (EditText) findViewById(R.id.allergiesET);
        EditText dislikedIngET = (EditText) findViewById(R.id.dislikedIngET);

    }
}
