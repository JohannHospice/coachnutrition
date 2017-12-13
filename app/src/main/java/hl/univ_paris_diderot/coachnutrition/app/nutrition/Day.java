package hl.univ_paris_diderot.coachnutrition.app.nutrition;

import java.util.Date;
import java.util.List;

import hl.univ_paris_diderot.coachnutrition.app.Objective;

public class Day {
    private long id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
