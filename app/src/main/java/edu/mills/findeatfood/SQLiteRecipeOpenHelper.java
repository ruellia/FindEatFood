package edu.mills.findeatfood;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteRecipeOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "favorite_recipes";
    private static final int DB_VERSION = 2;

    //package-private table and column names
    static final String RECIPE_TABLE = "FAVORITE_RECIPES";
    static final String RECIPE_NAME_COL = "RECIPE_NAME";
    static final String RECIPE_ID_COL = "RECIPE_ID";

    SQLiteRecipeOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE " + RECIPE_TABLE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    RECIPE_NAME_COL + " STRING," +
                    RECIPE_ID_COL + " STRING);");
            RecipeDatabaseUtilities.insertRecipe(db, "Beef Bourguignon", "Beef-bourguignon-333851");
            RecipeDatabaseUtilities.insertRecipe(db, "Pork Chops Au Poivre", "Pork-Chops-Au-Poivre-1568332");
        }
    }
}
