package fr.univ_paris_diderot.coachnutrition.app.model;

import java.util.ArrayList;
import java.util.List;

public class Meal {
    private long id;
    private String name;
    private List<MealFood> foodList;
    private Statistic statistic;

    public Meal(long id, String name, List<MealFood> foodList, Statistic statistic) {
        this.id = id;
        this.name = name;
        this.foodList = foodList;
        this.statistic = statistic;
    }

    public Meal() {
        foodList = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MealFood> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<MealFood> foodList) {
        this.foodList = foodList;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }
}

