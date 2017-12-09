package hl.univ_paris_diderot.coachnutrition.app.nutrition.collection;

import hl.univ_paris_diderot.coachnutrition.app.nutrition.Food;

public class Meal extends Collection<Food> {
    private String name;

    public Meal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
