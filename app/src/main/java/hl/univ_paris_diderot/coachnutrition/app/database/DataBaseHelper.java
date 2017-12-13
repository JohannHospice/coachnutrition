package hl.univ_paris_diderot.coachnutrition.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CoachNutrition.db";

    private static DataBaseHelper ourInstance;

    public static DataBaseHelper getInstance(Context context) {
        if (ourInstance == null)
            ourInstance = new DataBaseHelper(context);
        return ourInstance;
    }

    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBase.Objective.SQL_CREATE_ENTRIES);
        db.execSQL(DataBase.Statistic.SQL_CREATE_ENTRIES);
        db.execSQL(DataBase.Food.SQL_CREATE_ENTRIES);
        db.execSQL(DataBase.Meal.SQL_CREATE_ENTRIES);
        db.execSQL(DataBase.Day.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL(DataBase.Objective.SQL_DELETE_ENTRIES);
            db.execSQL(DataBase.Statistic.SQL_DELETE_ENTRIES);
            db.execSQL(DataBase.Food.SQL_DELETE_ENTRIES);
            db.execSQL(DataBase.Meal.SQL_DELETE_ENTRIES);
            db.execSQL(DataBase.Day.SQL_DELETE_ENTRIES);
            onCreate(db);
        }
    }
}
