package edu.mills.findeatfood;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays dietary restrictions in checkboxes. Checking a checkbox
 * will add the dietary restriction to the recipe API call. Clicking the
 * find recipe button will send the recipe API call and launch
 * {@link ResultsFragment}
 */
public class DietaryFragment extends Fragment {

    private List<Integer> dietOptionsIds = new ArrayList<Integer>();
    private List<Integer> allergyOptionsIds = new ArrayList<Integer>();
    private OnDietOptionsSelectedListener dietOptionsSelectedListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dietary, container, false);
        String[] dietOptions =
                getResources().getStringArray(R.array.dietOptionsName);
        String[] allergyOptions =
                getResources().getStringArray(R.array.allergyOptionsName);
        addCheckBoxes(dietOptions, view, dietOptionsIds);
        addCheckBoxes(allergyOptions, view, allergyOptionsIds);

        //calls interface method for passing data to MainActivity
        dietOptionsSelectedListener.onDietOptionsSelected(dietOptionsIds, allergyOptionsIds);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //makes sure class is implementing interface
        try {
            dietOptionsSelectedListener = (OnDietOptionsSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + getString(R.string.error));
        }
    }

    private void addCheckBoxes(String[] options,
                               View view,
                               List<Integer> optionsIds) {
        LinearLayout dietaryOptionsLayout =
                (LinearLayout) view.findViewById(R.id.dietaryOptionsLayout);

        for (int i = 0; i < options.length; i++) {
            CheckBox checkBox = new CheckBox(this.getActivity());
            checkBox.setText(options[i]);
            checkBox.setTextColor(getActivity().getResources().getColor(R.color.white));
            int checkBoxId = view.generateViewId();
            optionsIds.add(checkBoxId);
            checkBox.setId(checkBoxId);
            dietaryOptionsLayout.addView(checkBox);
        }
    }

    /**
     * Used for transferring ArrayList of ids for
     * dietary and allergy options into
     * {@code MainActivity}.
     */
    public interface OnDietOptionsSelectedListener {
        /**
         * Passes ArrayLists of dietary and allergy options ids
         * into MainActivity.
         *
         * @param dietOptionsIds    ArrayList of ids for dietary options.
         * @param allergyOptionsIds ArrayList of ids for allergy options.
         */
        void onDietOptionsSelected(List<Integer> dietOptionsIds, List<Integer> allergyOptionsIds);
    }
}
