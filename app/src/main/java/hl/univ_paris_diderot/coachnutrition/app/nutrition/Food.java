package hl.univ_paris_diderot.coachnutrition.app.nutrition;

public class Food {
    private long id;
    private String name;
    private Statistic statistic;

    public Food(String name, int calorie, int lipid, int glucide, int protein) {
        this.name = name;
        statistic = new Statistic(calorie, lipid, glucide, protein);
    }

    public Food(String name, int calorie) {
        this.name = name;
        statistic = new Statistic(calorie);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }
}
