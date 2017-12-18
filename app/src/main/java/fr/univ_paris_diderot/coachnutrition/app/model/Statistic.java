package fr.univ_paris_diderot.coachnutrition.app.model;

/**
 * Created by djihe on 18/12/2017.
 */

public class Statistic {
    private long id;

    private int calorie;
    private int glucide;
    private int protein;

    public Statistic(long id, int calorie, int glucide, int protein, int lipide) {
        this.id = id;
        this.calorie = calorie;
        this.glucide = glucide;
        this.protein = protein;
        this.lipide = lipide;
    }

    public Statistic() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public int getGlucide() {
        return glucide;
    }

    public void setGlucide(int glucide) {
        this.glucide = glucide;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getLipide() {
        return lipide;
    }

    public void setLipide(int lipide) {
        this.lipide = lipide;
    }

    int lipide;
}
