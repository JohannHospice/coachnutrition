package hl.univ_paris_diderot.coachnutrition.app.modele;

import android.content.ContentValues;

/**
 * Created by djihe on 14/12/2017.
 */

abstract class Modele {
    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public abstract ContentValues toContentValues();

}
