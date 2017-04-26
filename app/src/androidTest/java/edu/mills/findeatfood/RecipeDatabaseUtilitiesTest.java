package edu.mills.findeatfood;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.test.RenamingDelegatingContext;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class RecipeDatabaseUtilitiesTest {

    private static final String[] recipeNamesArray = {"CHICKEN SOUP", "ICE CREAM", "BURRITO"};
    private static final String[] recipeIdsArray = {"CHICKEN-SOUP-214216",
            "ICE-CREAM-915810", "BURRITO-166461"};
    private String recipeName;
    private String recipeId;
    private SQLiteDatabase db;
    private Cursor insertRecipeCursor;
    private Cursor getAllRecipeCursor;

    @Before
    public void setUp() throws Exception {

        //This creates a temporary context so database accesses in the tests are isolated.
        RenamingDelegatingContext context =
                new RenamingDelegatingContext(InstrumentationRegistry.getTargetContext(), "_test");
        db = new SQLiteRecipeOpenHelper(context).getWritableDatabase();

    }

    @Test
    public void insertAndGetRecipes() throws Exception {
        //inserts recipes into database
        RecipeDatabaseUtilities.insertRecipe(db, recipeNamesArray[0], recipeIdsArray[0]);
        RecipeDatabaseUtilities.insertRecipe(db, recipeNamesArray[1], recipeIdsArray[1]);
        RecipeDatabaseUtilities.insertRecipe(db, recipeNamesArray[2], recipeIdsArray[2]);

        //gets all recipes from the database
        insertRecipeCursor = db.query(SQLiteRecipeOpenHelper.RECIPE_TABLE,
                new String[]{SQLiteRecipeOpenHelper.RECIPE_NAME_COL,
                        SQLiteRecipeOpenHelper.RECIPE_ID_COL},
                null,
                null,
                null,
                null,
                null);

        //verifies recipes were inserted properly
        int i = 0;
        while (insertRecipeCursor.moveToNext()) {
            recipeName = insertRecipeCursor.getString(0);
            recipeId = insertRecipeCursor.getString(1);
            assertEquals(recipeNamesArray[i], recipeName);
            assertEquals(recipeIdsArray[i], recipeId);
            i++;
        }

        //gets all the recipes from the database
        getAllRecipeCursor = RecipeDatabaseUtilities.getAllRecipes(db);

        //verifies all recipes were returned and correct
        int j = 0;
        while (getAllRecipeCursor.moveToNext()) {
            recipeName = getAllRecipeCursor.getString(0);
            recipeId = getAllRecipeCursor.getString(1);
            assertEquals(recipeNamesArray[j], recipeName);
            assertEquals(recipeIdsArray[j], recipeId);
            j++;
        }
    }
}