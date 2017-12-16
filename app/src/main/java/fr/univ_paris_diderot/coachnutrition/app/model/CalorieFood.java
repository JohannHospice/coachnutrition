package fr.univ_paris_diderot.coachnutrition.app.model;

public class CalorieFood {
    private int calorie;
    private String name;

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CalorieFood(int calorie, String name) {

        this.calorie = calorie;
        this.name = name;
    }
}
