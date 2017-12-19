package fr.univ_paris_diderot.coachnutrition.app.model;

/**
 * Created by djihe on 18/12/2017.
 */

public class Statistic {
    private long id;

    private float calorie;
    private float glucide;
    private float protein;

    public Statistic(long id, float calorie, float glucide, float protein, float lipide) {
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

    public float getCalorie() {
        return calorie;
    }

    public void setCalorie(float calorie) {
        this.calorie = calorie;
    }

    public float getGlucide() {
        return glucide;
    }

    public void setGlucide(float glucide) {
        this.glucide = glucide;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getLipide() {
        return lipide;
    }

    public void setLipide(float lipide) {
        this.lipide = lipide;
    }

    float lipide;
}
