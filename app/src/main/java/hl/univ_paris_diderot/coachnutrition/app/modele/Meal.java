package hl.univ_paris_diderot.coachnutrition.app.modele;

import android.content.ContentValues;

import java.util.List;

import hl.univ_paris_diderot.coachnutrition.app.database.DataBase;

public class Meal extends Modele {
    private String name;
    private Statistic statistic;
    private List<Food> foods;

    public Meal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBase.Meal._ID, id);
        contentValues.put(DataBase.Meal.COLUMN_NAME_NAME, name);
        return contentValues;
    }
}
