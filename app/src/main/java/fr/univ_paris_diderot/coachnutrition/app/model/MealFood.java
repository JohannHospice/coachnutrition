package fr.univ_paris_diderot.coachnutrition.app.model;

public class MealFood {
    private long id;
    private float calorie;
    private String name;

    public MealFood(long id, float calorie, String name) {
        this.id =id;
        this.calorie = calorie;
        this.name = name;
    }
    public MealFood() {
    }

    public float getCalorie() {
        return calorie;
    }

    public void setCalorie(float calorie) {
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
