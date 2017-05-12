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
        // This creates a temporary context so database accesses in the tests are isolated.
        RenamingDelegatingContext context =
                new RenamingDelegatingContext(InstrumentationRegistry.getTargetContext(), "_test");
        db = new SQLiteRecipeOpenHelper(context).getWritableDatabase();

        // Entering mock data to test RecipeDatabaseUtilities methods.
        RecipeDatabaseUtilities.insertRecipe(db, "Beef Bourguignon", "Beef-bourguignon-333851");
        RecipeDatabaseUtilities.insertRecipe(db, "Pork Chops Au Poivre", "Pork-Chops-Au-Poivre-1568332");
    }

    @Test
    public void getRecipes() throws Exception {
        // Gets all the recipes from the database.
        Cursor recipeCursor = RecipeDatabaseUtilities.getAllRecipes(db);

        // Verifies all recipes were returned and correct.
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
        // Inserts recipe into database.
        RecipeDatabaseUtilities.insertRecipe(db, "CHICKEN SOUP", "Chicken-Soup-000000");

        // Get cursor to all recipes.
        Cursor recipeCursor = RecipeDatabaseUtilities.getAllRecipes(db);

        // Move past the default recipes.
        recipeCursor.moveToNext();
        recipeCursor.moveToNext();

        // Verify that recipe was inserted properly.
        recipeCursor.moveToNext();
        String recipeName = recipeCursor.getString(0);
        String recipeId = recipeCursor.getString(1);
        assertEquals("CHICKEN SOUP", recipeName);
        assertEquals("Chicken-Soup-000000", recipeId);
    }

    @Test
    public void searchForRecipe() throws Exception {
        // Checks if recipe is in db.
        Boolean isRecipe = RecipeDatabaseUtilities.searchForRecipe(db, "Beef-bourguignon-333851");

        // Verifies recipe is in db.
        assertEquals(true, isRecipe);

        // Checks if recipe is in db.
        isRecipe = RecipeDatabaseUtilities.searchForRecipe(db, "Carrot-soup-827394");

        // Verifies recipe is not in db.
        assertEquals(false, isRecipe);
    }

    @Test
    public void deleteRecipe() throws Exception {
        // Deletes recipe from database.
        RecipeDatabaseUtilities.deleteRecipe(db, "Beef-bourguignon-333851");

        // Checks if recipe was deleted.SS
        Boolean isRecipe =
                RecipeDatabaseUtilities.searchForRecipe(db, "Beef-bourguignon-333851");
        assertEquals(false, isRecipe);
    }
}