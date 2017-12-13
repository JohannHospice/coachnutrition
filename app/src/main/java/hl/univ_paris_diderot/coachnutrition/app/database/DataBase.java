package hl.univ_paris_diderot.coachnutrition.app.database;

import android.provider.BaseColumns;

public final class DataBase {
    private DataBase() {
    }

    public static class Statistic implements BaseColumns {
        public static final String TABLE_NAME = "statistic";
        public static final String COLUMN_NAME_PROTEIN = "protein";
        public static final String COLUMN_NAME_CALORIE = "calorie";
        public static final String COLUMN_NAME_GLUCIDE = "glucide";
        public static final String COLUMN_NAME_LIPIDE = "lipide";

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

    public static class Food implements BaseColumns {
        public static final String TABLE_NAME = "food";
        public static final String COLUMN_NAME_STATISTIC_ID = "_statistic_id";
        public static final String COLUMN_NAME_MEAL_ID = "_meal_id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_GRAMME = "gramme";

        public static final String SQL_CREATE_ENTRIES =
                "create table " + TABLE_NAME + "( " +
                        _ID + " integer primary key autoincrement," +
                        COLUMN_NAME_STATISTIC_ID + " int not null, " +
                        COLUMN_NAME_MEAL_ID + " int, " +
                        COLUMN_NAME_NAME + " varchar(255) not null, " +
                        COLUMN_NAME_GRAMME + " int not null);";

        public static final String SQL_DELETE_ENTRIES =
                "drop table if exists " + TABLE_NAME;
    }

    public static class Meal implements BaseColumns {
        public static final String TABLE_NAME = "meal";
        public static final String COLUMN_NAME_STATISTIC_ID = "_statistic_id";
        public static final String COLUMN_NAME_DAY_ID = "_day_id";
        public static final String COLUMN_NAME_NAME = "name";

        public static final String SQL_CREATE_ENTRIES =
                "create table " + TABLE_NAME + "( " +
                        _ID + " integer primary key autoincrement," +
                        COLUMN_NAME_STATISTIC_ID + " int not null, " +
                        COLUMN_NAME_DAY_ID + " int not null, " +
                        COLUMN_NAME_NAME + " varchar(255) not null);";

        public static final String SQL_DELETE_ENTRIES =
                "drop table if exists " + TABLE_NAME;
    }

    public static class Day implements BaseColumns {
        public static final String TABLE_NAME = "day";
        public static final String COLUMN_NAME_STATISTIC_ID = "_statistic_id";
        public static final String COLUMN_NAME_OBJECTIVE_ID = "_objective_id";
        public static final String COLUMN_NAME_DATE = "date";

        public static final String SQL_CREATE_ENTRIES =
                "create table " + TABLE_NAME + "( " +
                        _ID + " integer primary key autoincrement," +
                        COLUMN_NAME_STATISTIC_ID + " int not null, " +
                        COLUMN_NAME_OBJECTIVE_ID + " int not null, " +
                        COLUMN_NAME_DATE + " date not null);";

        public static final String SQL_DELETE_ENTRIES =
                "drop table if exists " + TABLE_NAME;
    }

    public static class Objective implements BaseColumns {
        public static final String TABLE_NAME = "objective";
        public static final String COLUMN_NAME_MAX_CALORIE = "max_calorie";
        public static final String COLUMN_NAME_MIN_CALORIE = "min_calorie";

        public static final String SQL_CREATE_ENTRIES =
                "create table " + TABLE_NAME + "( " +
                        _ID + " integer primary key autoincrement," +
                        COLUMN_NAME_MAX_CALORIE + " int not null, " +
                        COLUMN_NAME_MIN_CALORIE + " int not null);";

        public static final String SQL_DELETE_ENTRIES =
                "drop table if exists " + TABLE_NAME;
    }
}
