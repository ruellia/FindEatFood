package edu.mills.findeatfood;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The top-level activity for the app FindEatFood. This activity is
 * associated with the navigation drawer and its fragments, from which the user
 * can search for recipes from an ingredient list. The app returns a list of
 * recipes from an API if matches are found. The recipes can be added
 * to favorites and stored in a database.
 */

public class MainActivity extends Activity
        implements ResultsFragment.ResultsListListener, DietaryFragment.OnDietOptionsSelectedListener {

    private String[] titles;
    private ListView drawerList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private int currentPosition = 0;
    private String[] ingredients;
    /**
     * Key associated with the String array of ingredients being put in the Bundle for the recipe API call.
     */
    static final String INGREDIENTS = "ingredients";
    /**
     * Key associated with the String ArrayList of diet restrictions being put in the Bundle for the recipe API call.
     */
    static final String DIET_RESTRICTIONS = "dietRestrictions";
    /**
     * Key associated with the String ArrayList of allergy restrictions being put in the Bundle for the recipe API call.
     */
    static final String ALLERGY_RESTRICTIONS = "allergyRestrictions";
    /**
     * Key associated with the recipeId String being put in the Bundle for the recipe API call.
     */
    static final String RECIPE_ID = "recipeId";
    /**
     * Tag associated with visible fragments.
     */
    static final String VISIBLE_FRAGMENT = "visible_fragment";
    /**
     * Key associated with int value being put in Bundle for preserving drawer state upon device rotation.
     */
    static final String POSITION = "position";
    private List<Integer> dietOptionsIds = new ArrayList<Integer>();
    private List<Integer> allergyOptionsIds = new ArrayList<Integer>();

    @Override
    public void onDietOptionsSelected(List<Integer> dietOptionsIds, List<Integer> allergyOptionsIds) {
        this.dietOptionsIds = dietOptionsIds;
        this.allergyOptionsIds = allergyOptionsIds;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titles = getResources().getStringArray(R.array.titles);
        drawerList = (ListView) findViewById(R.id.drawer);
        // reference to Drawer Layout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // populate the ListView
        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, titles));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(POSITION);
            setActionBarTitle(currentPosition);
        } else {
            selectItem(0);
        }
        drawerToggle = new DrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(drawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getFragmentManager().addOnBackStackChangedListener(new FragManager());
    }

    private class FragManager implements FragmentManager.OnBackStackChangedListener {

        public void onBackStackChanged() {
            FragmentManager fragMan = getFragmentManager();
            Fragment fragment = fragMan.findFragmentByTag(VISIBLE_FRAGMENT);

            if (fragment instanceof HomeFragment) {
                currentPosition = 0;
            }
            if (fragment instanceof IngredientsFragment) {
                currentPosition = 1;
            }
            if (fragment instanceof DietaryFragment) {
                currentPosition = 1;
            }
            if (fragment instanceof ResultsFragment) {
                currentPosition = 1;
            }
            if (fragment instanceof FavoritesFragment) {
                currentPosition = 2;
            }
            setActionBarTitle(currentPosition);
            if (fragment instanceof RecipeDetailFragment) {
                currentPosition = 1;
                getActionBar().setTitle("   " + getString(R.string.recipe_details));
            }
            drawerList.setItemChecked(currentPosition, true);
        }
    }

    private class DrawerToggle extends ActionBarDrawerToggle {

        DrawerToggle(Activity activity,
                     DrawerLayout drawerLayout,
                     int openDrawerContentDescRes,
                     int closeDrawerContentDescRes) {
            super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);

        }

        @Override
        public void onDrawerClosed(View view) {
            super.onDrawerClosed(view);
            invalidateOptionsMenu();
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            invalidateOptionsMenu();
        }
    }

    private void selectItem(int position) {
        Fragment fragment;
        currentPosition = position;
        switch (position) {
            case 1:
                fragment = new IngredientsFragment();
                break;
            case 2:
                fragment = new FavoritesFragment();
                break;
            default:
                fragment = new HomeFragment();
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, VISIBLE_FRAGMENT);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        setActionBarTitle(position);
        drawerLayout.closeDrawer(drawerList);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, currentPosition);
    }

    private void setActionBarTitle(int position) {
        String title;
        if (position == 0) {
            title = getResources().getString(R.string.app_name);
        } else {
            title = titles[position];
        }
        String paddedTitle = "   " + title;
        getActionBar().setTitle(paddedTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item);
    }

    public void onResultsClicked(View v) {
        String[] dietaryOptionsName = getResources().getStringArray(R.array.dietOptionsName);
        String[] dietaryOptionsCode = getResources().getStringArray(R.array.dietOptionsCode);
        String[] allergyOptionsName = getResources().getStringArray(R.array.allergyOptionsName);
        String[] allergyOptionsCode = getResources().getStringArray(R.array.allergyOptionsCode);

        HashMap<String, String> restrictionsHashMap = new HashMap<String, String>();
        for (int i = 0; i < dietaryOptionsCode.length; i++) {
            restrictionsHashMap.put(dietaryOptionsName[i], dietaryOptionsCode[i]);
        }

        for (int i = 0; i < allergyOptionsCode.length; i++) {
            restrictionsHashMap.put(allergyOptionsName[i], allergyOptionsCode[i]);
        }

        int dietCheckBoxes =
                getResources().getStringArray(R.array.dietOptionsName).length;
        int allergyCheckBoxes =
                getResources().getStringArray(R.array.allergyOptionsName).length;
        LinearLayout dietaryOptions = (LinearLayout) findViewById(R.id.dietaryOptionsLayout);
        ArrayList<String> dietRestrictions = new ArrayList<String>();
        ArrayList<String> allergyRestrictions = new ArrayList<String>();

        for (int i = 0; i < dietCheckBoxes; i++) {
            CheckBox cb =
                    (CheckBox) dietaryOptions.findViewById(dietOptionsIds.get(i));
            if (cb != null && cb.isChecked()) {
                String cbName = cb.getText().toString();
                String cbCode = restrictionsHashMap.get(cbName);
                dietRestrictions.add(cbCode);
            }
        }

        for (int j = 0; j < allergyCheckBoxes; j++) {
            CheckBox cb =
                    (CheckBox) dietaryOptions.findViewById(allergyOptionsIds.get(j));
            if (cb != null && cb.isChecked()) {
                String cbName = cb.getText().toString();
                String cbCode = restrictionsHashMap.get(cbName);
                allergyRestrictions.add(cbCode);
            }
        }

        Bundle recipeBundle = new Bundle();
        recipeBundle.putStringArray(INGREDIENTS, ingredients);
        recipeBundle.putStringArrayList(DIET_RESTRICTIONS, dietRestrictions);
        recipeBundle.putStringArrayList(ALLERGY_RESTRICTIONS, allergyRestrictions);
        ResultsFragment resultsFrag = new ResultsFragment();
        resultsFrag.setArguments(recipeBundle);
        doFragTransaction(resultsFrag);
    }

    public void onEditClicked(View v) {
        IngredientsFragment ingredientsFrag = new IngredientsFragment();
        doFragTransaction(ingredientsFrag);
    }

    public void onDietaryClicked(View v) {
        EditText addIngredientET = (EditText) findViewById(R.id.addIngredientET);
        if (addIngredientET.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), R.string.error_ingredient, Toast.LENGTH_SHORT).show();
        } else {
            DietaryFragment dietaryFrag = new DietaryFragment();
            doFragTransaction(dietaryFrag);
            setIngredients(parseIngredientInput(addIngredientET.getText().toString()));
        }
    }

    private void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    private String[] parseIngredientInput(String ingredients) {
        return ingredients.split("\\s*,\\s*");
    }

    @Override
    public void onRecipeClicked(String recipeId) {
        Bundle toPass = new Bundle();
        toPass.putString(RECIPE_ID, recipeId);
        RecipeDetailFragment detailsFrag = new RecipeDetailFragment();
        detailsFrag.setArguments(toPass);
        doFragTransaction(detailsFrag);
    }

    private void doFragTransaction(Fragment fragment) {
        FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
        fragTransaction.replace(R.id.content_frame, fragment, VISIBLE_FRAGMENT);
        fragTransaction.addToBackStack(null);
        fragTransaction.commit();
    }
}