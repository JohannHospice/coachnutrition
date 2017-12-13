package hl.univ_paris_diderot.coachnutrition.app.nutrition;

public class Statistic {
    private long id;
    private int calorie = 0;
    private int lipid = 0;
    private int glucide = 0;
    private int protein = 0;

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

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public void setLipid(int lipid) {
        this.lipid = lipid;
    }

    public void setGlucide(int glucide) {
        this.glucide = glucide;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
