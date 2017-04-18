package edu.mills.findeatfood;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IngredientsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);

        Button dietaryActivityB = (Button) getView().findViewById(R.id.dietaryActivityB);

        dietaryActivityB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText addIngredientET = (EditText) getView().findViewById(R.id.addIngredientET);

                if (addIngredientET.getText().toString().equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.error_ingredient, Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(getActivity().getApplicationContext(), DietaryFragment.class);
                startActivity(intent);
            }
        });
        return view;
    }
}

