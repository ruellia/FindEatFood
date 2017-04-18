package edu.mills.findeatfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class IngredientActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ingredients);

        final Button addIngredientB1 = (Button) findViewById(R.id.addIngredientB1);
        final Button addIngredientB2 = (Button) findViewById(R.id.addIngredientB2);
        final Button addIngredientB3 = (Button) findViewById(R.id.addIngredientB3);
        final Button addIngredientB4 = (Button) findViewById(R.id.addIngredientB4);
        final Button dietaryActivityB = (Button) findViewById(R.id.dietaryActivityB);

        final EditText addIngredientET1 = (EditText) findViewById(R.id.addIngredientET1);
        final EditText addIngredientET2 = (EditText) findViewById(R.id.addIngredientET2);
        final EditText addIngredientET3 = (EditText) findViewById(R.id.addIngredientET3);
        final EditText addIngredientET4 = (EditText) findViewById(R.id.addIngredientET4);
        final EditText addIngredientET5 = (EditText) findViewById(R.id.addIngredientET5);

        addIngredientB1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (addIngredientET1.equals("")) {
                    addIngredientET1.setError(getText(R.string.error_ingredient));
                } else {
                    LinearLayout row2 = (LinearLayout) findViewById(R.id.row_2);
                    row2.setVisibility(View.VISIBLE);
                    addIngredientB1.setVisibility(View.GONE);
                }
            }
        });

        addIngredientB2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (addIngredientET2.equals("")) {
                    addIngredientET2.setError(getText(R.string.error_ingredient));
                } else {
                    LinearLayout row3 = (LinearLayout) findViewById(R.id.row_3);
                    row3.setVisibility(View.VISIBLE);
                    addIngredientB2.setVisibility(View.GONE);
                }
            }
        });

        addIngredientB3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (addIngredientET3.equals("")) {
                    addIngredientET3.setError(getText(R.string.error_ingredient));
                } else {
                    LinearLayout row4 = (LinearLayout) findViewById(R.id.row_4);
                    row4.setVisibility(View.VISIBLE);
                    addIngredientB3.setVisibility(View.GONE);
                }
            }
        });

        addIngredientB4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (addIngredientET4.equals("")) {
                    addIngredientET4.setError(getText(R.string.error_ingredient));
                } else {
                    LinearLayout row5 = (LinearLayout) findViewById(R.id.row_5);
                    row5.setVisibility(View.VISIBLE);
                    addIngredientB4.setVisibility(View.GONE);
                }
            }
        });

        dietaryActivityB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addIngredientET1.equals("")) {
                    addIngredientET1.setError(getText(R.string.error_ingredient));
                } else {
                    Intent intent = new Intent(IngredientActivity.this, DietaryFragment.class);
                    startActivity(intent);
                }
            }
        });
    }
}
