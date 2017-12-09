package hl.univ_paris_diderot.coachnutrition.app.nutrition;

public class Food extends Statistic {
    private String name;

    public Food(String name, int calorie, int lipid, int glucide, int protein) {
        super(calorie, lipid, glucide, protein);
        this.name = name;
    }

    public Food(String name, int calorie) {
        super(calorie);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
