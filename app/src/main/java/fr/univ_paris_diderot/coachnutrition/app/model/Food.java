package fr.univ_paris_diderot.coachnutrition.app.model;

/**
 * Created by djihe on 18/12/2017.
 */

public class Food {
    private long id;
    private String name;
    private int gramme;
    private Statistic statistic;

    public Food(long id, String name, int gramme, Statistic statistic) {
        this.id = id;
        this.name = name;
        this.gramme = gramme;
        this.statistic = statistic;
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

    public int getGramme() {
        return gramme;
    }

    public void setGramme(int gramme) {
        this.gramme = gramme;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }
}
