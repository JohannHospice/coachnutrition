package hl.univ_paris_diderot.coachnutrition.app.nutrition;

public class Objective {
    private int calorieMax;
    private int calorieMin;

    public Objective(int calorieMax, int calorieMin) {
        this.calorieMax = calorieMax;
        this.calorieMin = calorieMin;
    }

    public int getCalorieMax() {
        return calorieMax;
    }

    public void setCalorieMax(int calorieMax) {
        this.calorieMax = calorieMax;
    }

    public int getCalorieMin() {
        return calorieMin;
    }

    public void setCalorieMin(int calorieMin) {
        this.calorieMin = calorieMin;
    }
}
