package fr.univ_paris_diderot.coachnutrition.app.database;

import android.provider.BaseColumns;

public final class Contract {
    private Contract() {
    }

    public static class Trigger1 {
        public static final String TRIGGER_NAME = "t1";
        public static final String SQL_CREATE_ENTRIES =
                "create trigger " + TRIGGER_NAME + " after insert on " + FoodMeal.TABLE_NAME + " for each row " +
                        "update " + Statistic.TABLE_NAME + " " +
                        "set " + Statistic.COLUMN_NAME_CALORIE + " = " + Statistic.COLUMN_NAME_CALORIE + " + NEW." + FoodMeal.COLUMN_NAME_GRAMME + " * " +
                        "(select sf." + Statistic.COLUMN_NAME_CALORIE + " " +
                        "from " + Meal.TABLE_NAME + " m, " + Food.TABLE_NAME + " f, (select * from " + Statistic.TABLE_NAME + ") sf " +
                        "where m." + Meal._ID + " = NEW." + FoodMeal.COLUMN_NAME_MEAL_ID + " and f." + Food._ID + " = NEW." + FoodMeal.COLUMN_NAME_FOOD_ID + " and sf." + Statistic._ID + " = f." + Food.COLUMN_NAME_STATISTIC_ID + " ) " +
                        "where " + Statistic._ID + " = (select m." + Meal.COLUMN_NAME_STATISTIC_ID + " from " + Meal.TABLE_NAME + " m where m." + Meal._ID + " = NEW." + FoodMeal.COLUMN_NAME_MEAL_ID + ");";
        public static final String SQL_DELETE_ENTRIES =
                "drop trigger if exists " + TRIGGER_NAME;
    }

    public static class Trigger3 {
        public static final String TRIGGER_NAME = "t3";
        public static final String SQL_CREATE_ENTRIES =
                "create trigger " + TRIGGER_NAME + " before delete on " + FoodMeal.TABLE_NAME + " for each row " +
                        "update " + Statistic.TABLE_NAME + " " +
                        "set " + Statistic.COLUMN_NAME_CALORIE + " = " + Statistic.COLUMN_NAME_CALORIE + " - OLD." + FoodMeal.COLUMN_NAME_GRAMME + " * " +
                        "(select sf." + Statistic.COLUMN_NAME_CALORIE + " " +
                        "from " + Meal.TABLE_NAME + " m, " + Food.TABLE_NAME + " f, (select * from " + Statistic.TABLE_NAME + ") sf " +
                        "where m." + Meal._ID + " = OLD." + FoodMeal.COLUMN_NAME_MEAL_ID + " and f." + Food._ID + " = OLD." + FoodMeal.COLUMN_NAME_FOOD_ID + " and sf." + Statistic._ID + " = f." + Food.COLUMN_NAME_STATISTIC_ID + ") " +
                        "where " + Statistic._ID + " = (select m." + Meal.COLUMN_NAME_STATISTIC_ID + " from " + Meal.TABLE_NAME + " m where m." + Meal._ID + " = OLD." + FoodMeal.COLUMN_NAME_MEAL_ID + ");";
        public static final String SQL_DELETE_ENTRIES =
                "drop trigger if exists " + TRIGGER_NAME;
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
                        COLUMN_NAME_CALORIE + " float not null, " +
                        COLUMN_NAME_PROTEIN + " float, " +
                        COLUMN_NAME_GLUCIDE + " float, " +
                        COLUMN_NAME_LIPIDE + " float);";

        public static final String SQL_DELETE_ENTRIES =
                "drop table if exists " + TABLE_NAME;
    }

    public static class Food implements BaseColumns {
        public static final String TABLE_NAME = "food";
        public static final String COLUMN_NAME_STATISTIC_ID = "_statistic_id";
        public static final String COLUMN_NAME_NAME = "name";

        public static final String SQL_CREATE_ENTRIES =
                "create table " + TABLE_NAME + "( " +
                        _ID + " integer primary key autoincrement," +
                        COLUMN_NAME_STATISTIC_ID + " int not null, " +
                        COLUMN_NAME_NAME + " varchar(255) not null);";

        public static final String SQL_DELETE_ENTRIES =
                "drop table if exists " + TABLE_NAME;
    }

    public static class FoodMeal implements BaseColumns {
        public static final String TABLE_NAME = "food_meal";
        public static final String COLUMN_NAME_FOOD_ID = "_food_id";
        public static final String COLUMN_NAME_MEAL_ID = "_meal_id";
        public static final String COLUMN_NAME_GRAMME = "gramme";

        public static final String SQL_CREATE_ENTRIES =
                "create table " + TABLE_NAME + "( " +
                        _ID + " integer primary key autoincrement," +
                        COLUMN_NAME_MEAL_ID + " int not null, " +
                        COLUMN_NAME_FOOD_ID + " int not null, " +
                        COLUMN_NAME_GRAMME + " float not null);";

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
                        COLUMN_NAME_DATE + " varchar(8) not null);";

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
