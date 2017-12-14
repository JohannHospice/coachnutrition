package hl.univ_paris_diderot.coachnutrition.app.modele;

import android.content.ContentValues;

import java.util.Date;
import java.util.List;

import hl.univ_paris_diderot.coachnutrition.app.database.DataBase;

public class Day extends Modele {
    private Date date;
    private Statistic statistic;
    private Objective objective;
    private List<Meal> meals;

    public Day(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Objective getObjective() {
        return objective;
    }

    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
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
        contentValues.put(DataBase.Day._ID, id);
        contentValues.put(DataBase.Day.COLUMN_NAME_DATE, date.getTime());
        contentValues.put(DataBase.Day.COLUMN_NAME_OBJECTIVE_ID, objective.getId());
        contentValues.put(DataBase.Day.COLUMN_NAME_STATISTIC_ID, statistic.getId());
        return contentValues;
    }
}