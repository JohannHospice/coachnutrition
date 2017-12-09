package hl.univ_paris_diderot.coachnutrition.app.nutrition.collection;

import java.util.ArrayList;

import hl.univ_paris_diderot.coachnutrition.app.nutrition.Statistic;

public abstract class Collection<N extends Statistic> extends Statistic {
    private ArrayList<N> elements = new ArrayList<>();

    public void add(N n) {
        elements.add(n);
        plus(n);
    }

    public void remove(int i) {
        less(elements.remove(i));
    }

    public void remove(N n) {
        elements.remove(n);
        less(n);
    }

    public void less(N n) {
        calorie -= n.getCalorie();
        lipid -= n.getLipid();
        protein -= n.getProtein();
        glucide -= n.getGlucide();
    }

    public void plus(N n) {
        calorie += n.getCalorie();
        lipid += n.getLipid();
        protein += n.getProtein();
        glucide += n.getGlucide();
    }
}
