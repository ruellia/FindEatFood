package edu.mills.findeatfood;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public final class RecipeDatabaseUtilities {

    private static final String RECIPE_TABLE = "FAVORITE_RECIPES";

    private RecipeDatabaseUtilities() {
    }

    public static void insertRecipe(SQLiteDatabase db,
                                    String recipeName,
                                    String recipeId){
        ContentValues values = new ContentValues();
        values.put(SQLiteRecipeOpenHelper.RECIPE_NAME_COL, recipeName);
        values.put(SQLiteRecipeOpenHelper.RECIPE_ID_COL, recipeId);
        db.insert(RECIPE_TABLE, null, values);
    }

    public static Cursor getAllRecipes(SQLiteDatabase db){
        return db.query(SQLiteRecipeOpenHelper.RECIPE_TABLE,
                new String[]{SQLiteRecipeOpenHelper.RECIPE_NAME_COL
                        , SQLiteRecipeOpenHelper.RECIPE_ID_COL},
                null,
                null,
                null,
                null,
                null);
    }

}
