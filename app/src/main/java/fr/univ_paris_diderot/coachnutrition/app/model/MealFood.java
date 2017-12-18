package fr.univ_paris_diderot.coachnutrition.app.model;

public class MealFood {
    private long id;
    private int calorie;
    private String name;

    public MealFood() {
        this.calorie = calorie;
        this.name = name;
    }

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
