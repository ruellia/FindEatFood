package edu.mills.findeatfood;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class DietaryFragment extends Fragment {

    public static List<Integer> checkBoxIds = new ArrayList<Integer>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dietary, container, false);

        String[] dietaryOptions =
                getResources().getStringArray(R.array.dietaryOptionsName);
        LinearLayout dietaryOptionsLayout =
                (LinearLayout) view.findViewById(R.id.dietaryOptionsLayout);


        for (int i = 0; i < dietaryOptions.length; i++) {
            CheckBox checkBox = new CheckBox(this.getActivity());
            checkBox.setText(dietaryOptions[i]);
            int checkBoxId = view.generateViewId();
            checkBoxIds.add(checkBoxId);
            checkBox.setId(checkBoxId);
            dietaryOptionsLayout.addView(checkBox);
        }

        return view;
    }

}
