package hl.univ_paris_diderot.coachnutrition.app.nutrition.collection;

import java.util.Date;

import hl.univ_paris_diderot.coachnutrition.app.nutrition.Objective;

public class Day extends Collection<Meal> {
    private Date date;

    private Objective objective;

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

}
