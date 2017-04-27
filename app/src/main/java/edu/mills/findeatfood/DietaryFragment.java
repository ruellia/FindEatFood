package edu.mills.findeatfood;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;

public class DietaryFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            String[] list = args.getStringArray("ingredients");
            Toast.makeText(getActivity().getApplicationContext(), Arrays.toString(list), Toast.LENGTH_SHORT).show();

            for (int i =0; i< list.length; i++ ) {
                Log.d("Test Ingredient Bundle", args.getStringArray("ingredients")[i]);
            }
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dietary, container, false);
        String[] dietaryOptions = getResources().getStringArray(R.array.dietaryOptions);
        LinearLayout dietaryOptionsLayout = (LinearLayout) view.findViewById(R.id.dietaryOptionsLayout);

        for (int i = 0; i < dietaryOptions.length; i++) {
            CheckBox checkBox = new CheckBox(this.getActivity());
            checkBox.setText(dietaryOptions[i]);
            dietaryOptionsLayout.addView(checkBox);
        }
        return view;
    }
}
