package edu.mills.findeatfood;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


/**
 * Provides an interface that connects to the database. Activities and fragments should
 * make calls to here instead of trying to connect directly to the database.
 */
public final class RecipeDatabaseUtilities {

    private static final String RECIPE_TABLE = "FAVORITE_RECIPES";

    private RecipeDatabaseUtilities() {
    }

    /**
     * Inserts the given recipe into the database by using the SQLiteRecipeOpenHelper.
     * @param db Our SQLiteDatabase, where we will be inserting the recipe into.
     * @param recipeName A String which is the name of the recipe.
     * @param recipeId A String which is the id of the recipe.
     * @return void It inserts the RECIPE_TABLE into the database with the values of the
     * name of the recipe and its id.
     */
    public static void insertRecipe(SQLiteDatabase db,
                                    String recipeName,
                                    String recipeId) {
        ContentValues values = new ContentValues();
        values.put(SQLiteRecipeOpenHelper.RECIPE_NAME_COL, recipeName);
        values.put(SQLiteRecipeOpenHelper.RECIPE_ID_COL, recipeId);
        db.insert(RECIPE_TABLE, null, values);
    }

    /**
     * Gets all of the recipes in the database by using SQLiteRecipeOpenHelper.
     * @param db Our SQLiteDatabase, where we will be getting all of the recipes from.
     * @return Cursor Queries through our database to get the table and all of the names
     * and ids of the recipes.
     */
    public static Cursor getAllRecipes(SQLiteDatabase db) {
        return db.query(SQLiteRecipeOpenHelper.RECIPE_TABLE,
                new String[]{SQLiteRecipeOpenHelper.RECIPE_NAME_COL
                        , SQLiteRecipeOpenHelper.RECIPE_ID_COL},
                null,
                null,
                null,
                null,
                null);
    }

    /**
     * Searches through the database for the given recipe in order to not insert duplicates.
     * Does this by querying through the database.
     * @param db Our SQLiteDatabase.
     * @param recipeId A String, which is the id of the given recipe.
     * @return Boolean Returns true if the recipe is found in the database. Returns false if the recipe is not found.
     */
    public static Boolean searchForRecipe(SQLiteDatabase db,
                                          String recipeId) {
        Cursor cursor = db.query(SQLiteRecipeOpenHelper.RECIPE_TABLE,
                new String[]{SQLiteRecipeOpenHelper.RECIPE_NAME_COL,
                        SQLiteRecipeOpenHelper.RECIPE_ID_COL},
                SQLiteRecipeOpenHelper.RECIPE_ID_COL + " = ?",
                new String[]{recipeId},
                null,
                null,
                null);

        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Deletes the given recipe from the database.
     * @param db Our SQLiteDatabase.
     * @param recipeId A String, which is the id of the given recipe.
     * @return void Returns void.
     */
    public static void deleteRecipe(SQLiteDatabase db,
                                    String recipeId) {
        db.delete(SQLiteRecipeOpenHelper.RECIPE_TABLE,
                SQLiteRecipeOpenHelper.RECIPE_ID_COL + " = ?",
                new String[]{recipeId});
    }


}
