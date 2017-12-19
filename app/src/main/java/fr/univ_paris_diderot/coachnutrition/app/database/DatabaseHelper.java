package fr.univ_paris_diderot.coachnutrition.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "CoachNutrition.db";

    private static DatabaseHelper ourInstance;

    public static DatabaseHelper getInstance(Context context) {
        if (ourInstance == null)
            ourInstance = new DatabaseHelper(context);
        return ourInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Contract.Objective.SQL_CREATE_ENTRIES);
        db.execSQL(Contract.Statistic.SQL_CREATE_ENTRIES);
        db.execSQL(Contract.Food.SQL_CREATE_ENTRIES);
        db.execSQL(Contract.FoodMeal.SQL_CREATE_ENTRIES);
        db.execSQL(Contract.Meal.SQL_CREATE_ENTRIES);
        db.execSQL(Contract.Day.SQL_CREATE_ENTRIES);
/*
        db.execSQL(Contract.Trigger1.SQL_CREATE_ENTRIES);
        db.execSQL(Contract.Trigger3.SQL_CREATE_ENTRIES);
  */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //if (newVersion > oldVersion) {
        db.execSQL(Contract.Objective.SQL_DELETE_ENTRIES);
        db.execSQL(Contract.Statistic.SQL_DELETE_ENTRIES);
        db.execSQL(Contract.Food.SQL_DELETE_ENTRIES);
        db.execSQL(Contract.FoodMeal.SQL_DELETE_ENTRIES);
        db.execSQL(Contract.Meal.SQL_DELETE_ENTRIES);
        db.execSQL(Contract.Day.SQL_DELETE_ENTRIES);
    /*    db.execSQL(Contract.Trigger1.SQL_DELETE_ENTRIES);
        db.execSQL(Contract.Trigger3.SQL_DELETE_ENTRIES);
      */
        onCreate(db);
        //}
    }
}
