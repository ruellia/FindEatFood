package edu.mills.findeatfood;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.test.RenamingDelegatingContext;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class RecipeDatabaseUtilitiesTest {

    private static final String[] initialRecipeNames = {"Beef Bourguignon", "Pork Chops Au Poivre"};
    private static final String[] initialRecipeIds = {"Beef-bourguignon-333851", "Pork-Chops-Au-Poivre-1568332"};
    private SQLiteDatabase db;

    @Before
    public void setUp() throws Exception {
        //This creates a temporary context so database accesses in the tests are isolated.
        RenamingDelegatingContext context =
                new RenamingDelegatingContext(InstrumentationRegistry.getTargetContext(), "_test");
        db = new SQLiteRecipeOpenHelper(context).getWritableDatabase();
    }

    @Test
    public void getRecipes() throws Exception {
        // gets all the recipes from the database
        Cursor recipeCursor = RecipeDatabaseUtilities.getAllRecipes(db);

        // verifies all recipes were returned and correct
        int j = 0;
        while (recipeCursor.moveToNext()) {
            String recipeName = recipeCursor.getString(0);
            String recipeId = recipeCursor.getString(1);
            assertEquals(initialRecipeNames[j], recipeName);
            assertEquals(initialRecipeIds[j], recipeId);
            j++;
        }
    }

    @Test
    public void insertRecipes() throws Exception {
        // inserts recipe into database
        RecipeDatabaseUtilities.insertRecipe(db, "CHICKEN SOUP", "Chicken-Soup-000000");

        // get cursor to all recipes
        Cursor recipeCursor = RecipeDatabaseUtilities.getAllRecipes(db);

        // move past the default recipes
        recipeCursor.moveToNext();
        recipeCursor.moveToNext();

        // verify that recipe was inserted properly
        recipeCursor.moveToNext();
        String recipeName = recipeCursor.getString(0);
        String recipeId = recipeCursor.getString(1);
        assertEquals("CHICKEN SOUP", recipeName);
        assertEquals("Chicken-Soup-000000", recipeId);
    }
}