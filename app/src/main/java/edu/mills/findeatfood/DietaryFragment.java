package edu.mills.findeatfood;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class DietaryFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dietary, container, false);
        String[] dietaryOptions =
                getResources().getStringArray(R.array.dietaryOptions);
        LinearLayout dietaryOptionsLayout =
                (LinearLayout) view.findViewById(R.id.dietaryOptionsLayout);
        for (int i = 0; i < dietaryOptions.length; i++) {
            CheckBox checkBox = new CheckBox(this.getActivity());
            checkBox.setText(dietaryOptions[i]);
            dietaryOptionsLayout.addView(checkBox);
        }
        return view;
    }
}
