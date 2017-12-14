package hl.univ_paris_diderot.coachnutrition.app.modele;

import android.content.ContentValues;

import hl.univ_paris_diderot.coachnutrition.app.database.DataBase;

public class Objective extends Modele {
    private int calorieMax;
    private int calorieMin;

    public Objective(int calorieMax, int calorieMin) {
        this.calorieMax = calorieMax;
        this.calorieMin = calorieMin;
    }

    public int getCalorieMax() {
        return calorieMax;
    }

    public void setCalorieMax(int calorieMax) {
        this.calorieMax = calorieMax;
    }

    public int getCalorieMin() {
        return calorieMin;
    }

    public void setCalorieMin(int calorieMin) {
        this.calorieMin = calorieMin;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBase.Objective._ID, id);
        contentValues.put(DataBase.Objective.COLUMN_NAME_MAX_CALORIE, calorieMax);
        contentValues.put(DataBase.Objective.COLUMN_NAME_MIN_CALORIE, calorieMin);
        return contentValues;
    }
}
