package hl.univ_paris_diderot.coachnutrition.app.modele;

import android.content.ContentValues;

import hl.univ_paris_diderot.coachnutrition.app.database.Contract;

public class Food extends Modele {
    private String name;
    private int gramme;
    private Statistic statistic;

    public Food(String name, int calorie, int lipid, int glucide, int protein) {
        this.name = name;
        statistic = new Statistic(calorie, lipid, glucide, protein);
    }

    public Food(String name, int calorie) {
        this.name = name;
        statistic = new Statistic(calorie);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }

    public int getGramme() {
        return gramme;
    }

    public void setGramme(int gramme) {
        this.gramme = gramme;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.Food._ID, id);
        contentValues.put(Contract.Food.COLUMN_NAME_GRAMME, gramme);
        contentValues.put(Contract.Food.COLUMN_NAME_NAME, name);
        return contentValues;
    }
}
