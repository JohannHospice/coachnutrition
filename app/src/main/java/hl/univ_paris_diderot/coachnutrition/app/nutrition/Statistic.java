package hl.univ_paris_diderot.coachnutrition.app.nutrition;

public abstract class Statistic {
    protected int calorie;
    protected int lipid;
    protected int glucide;
    protected int protein;

    public Statistic() {

    }

    public Statistic(int calorie) {
        this.calorie = calorie;
    }

    public Statistic(int calorie, int lipid, int glucide, int protein) {
        this(calorie);
        this.lipid = lipid;
        this.glucide = glucide;
        this.protein = protein;
    }

    public int getCalorie() {
        return calorie;
    }

    public int getLipid() {
        return lipid;
    }

    public int getGlucide() {
        return glucide;
    }

    public int getProtein() {
        return protein;
    }
}
