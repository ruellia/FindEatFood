package edu.mills.findeatfood;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends Activity
        implements FindDealsFragment.StoreListListener, ResultsFragment.ResultsListListener {

    private ShareActionProvider shareActionProvider;
    private String[] titles;
    private ListView drawerList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private int currentPosition = 0;

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //call when item in the drawer is clicked
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
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1, titles));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position");
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
            Fragment fragment = fragMan.findFragmentByTag("visible_fragment");
            if (fragment instanceof HomeFragment) {
                currentPosition = 0;
            }
            if (fragment instanceof IngredientsFragment) {
                currentPosition = 1;
            }
            if (fragment instanceof FindDealsFragment) {
                currentPosition = 2;
            }
            if (fragment instanceof FavoritesFragment) {
                currentPosition = 3;
            }
            setActionBarTitle(currentPosition);
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

        //Called when a drawer has settled in a completely closed state
        @Override
        public void onDrawerClosed(View view) {
            super.onDrawerClosed(view);
            invalidateOptionsMenu();
        }

        //Called when a drawer has settled in a completely open state.
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
                fragment = new FindDealsFragment();
                break;
            case 3:
                fragment = new FavoritesFragment();
                break;
            default:
                fragment = new HomeFragment();
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "visible_fragment");
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        setActionBarTitle(position);
        drawerLayout.closeDrawer(drawerList);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the drawer is open, hide action items related to the content view
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_share).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
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
        outState.putInt("position", currentPosition);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        setIntent("This is example text");
        return super.onCreateOptionsMenu(menu);
    }

    private void setIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_create_order:
                //Code to run when the Create Order item is clicked
                //Intent intent = new Intent(this, OrderActivity.class);
                //startActivity(intent);
                return true;
            case R.id.action_settings:
                //Code to run when the settings item is clicked
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void onResultsClicked(View v) {
        ResultsFragment resultsFrag = new ResultsFragment();
        doFragTransaction(resultsFrag);
    }

    public void onEditClicked(View v) {
        IngredientsFragment ingredientsFrag = new IngredientsFragment();
        doFragTransaction(ingredientsFrag);
    }

    public void onDietaryClicked(View v) {
        Log.d("MainActivity", "onDietaryClicked");

        EditText addIngredientET = (EditText) findViewById(R.id.addIngredientET);
        if (addIngredientET.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), R.string.error_ingredient, Toast.LENGTH_SHORT).show();
        } else {
            Bundle toPass = new Bundle();
            String[] ingredients = parseIngredientInput(addIngredientET.getText().toString());
            toPass.putStringArray("ingredients", ingredients);
            DietaryFragment dietaryFrag = new DietaryFragment();
            dietaryFrag.setArguments(toPass);
            doFragTransaction(dietaryFrag);
        }
    }

    private String[] parseIngredientInput(String ingredients) {
        return ingredients.split("\\s*,\\s*");
    }

    @Override
    public void onStoreClicked(long id) {
        FindDealsByStoreFragment storeDealsFrag = new FindDealsByStoreFragment();
        doFragTransaction(storeDealsFrag);
    }

    @Override
    public void onRecipeClicked(String recipeId) {
        Bundle toPass = new Bundle();
        toPass.putString("recipeId", recipeId);
        RecipeDetailFragment detailsFrag = new RecipeDetailFragment();
        detailsFrag.setArguments(toPass);
        doFragTransaction(detailsFrag);
    }

    // helper function
    private void doFragTransaction(Fragment fragment) {
        FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
        fragTransaction.replace(R.id.content_frame, fragment);
        fragTransaction.addToBackStack(null);
        fragTransaction.commit();
    }
}