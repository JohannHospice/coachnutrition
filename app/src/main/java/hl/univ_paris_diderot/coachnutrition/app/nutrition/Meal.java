package hl.univ_paris_diderot.coachnutrition.app.nutrition;

import java.util.List;

import hl.univ_paris_diderot.coachnutrition.app.nutrition.Food;
import hl.univ_paris_diderot.coachnutrition.app.nutrition.Statistic;

public class Meal {
    private long id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
