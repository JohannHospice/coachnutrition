package hl.univ_paris_diderot.coachnutrition.app.bd;

import android.provider.BaseColumns;

public final class Contract {
    private Contract() {}

    public static class Statistic implements BaseColumns {
        public static final String TABLE_NAME = "statistic";
        public static final String COLUMN_NAME_PROTEIN = "protein";
        public static final String COLUMN_NAME_CALORIE = "calorie";
        public static final String COLUMN_NAME_GLUCIDE = "glucide";
        public static final String COLUMN_NAME_LIPIDE = "lipide";
        public static final int NUM_COL_ID = 0;
        public static final int NUM_COL_CALORIE = 1;
        public static final int NUM_COL_PROTEIN = 2;
        public static final int NUM_COL_GLUCIDE = 3;
        public static final int NUM_COL_LIPIDE = 4;

        public static final String SQL_CREATE_ENTRIES =
                "create table " + TABLE_NAME + "( " +
                        _ID + " integer primary key autoincrement," +
                        COLUMN_NAME_CALORIE + " int not null, " +
                        COLUMN_NAME_PROTEIN + " int, " +
                        COLUMN_NAME_GLUCIDE + " int, " +
                        COLUMN_NAME_LIPIDE + " int);";

        public static final String SQL_DELETE_ENTRIES =
                "drop table if exists " + TABLE_NAME;
    }
}
