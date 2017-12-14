package hl.univ_paris_diderot.coachnutrition.app.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import hl.univ_paris_diderot.coachnutrition.app.modele.Day;
import hl.univ_paris_diderot.coachnutrition.app.modele.Food;
import hl.univ_paris_diderot.coachnutrition.app.modele.Meal;
import hl.univ_paris_diderot.coachnutrition.app.modele.Objective;
import hl.univ_paris_diderot.coachnutrition.app.modele.Statistic;

public class NutritionResolverHandler {
    private ContentResolver resolver;

    NutritionResolverHandler(Context context) {
        resolver = context.getContentResolver();
    }

    public Uri insert(Statistic statistic) {
        return resolver.insert(buildUri(DataBase.Statistic.TABLE_NAME), statistic.toContentValues());
    }

    public Uri insert(Objective objective) {
        return resolver.insert(buildUri(DataBase.Objective.TABLE_NAME), objective.toContentValues());
    }

    public Uri insert(Food food) {
        insert(food.getStatistic());
        return resolver.insert(buildUri(DataBase.Food.TABLE_NAME), food.toContentValues());
    }

    public Uri insert(Meal meal) {
        for (Food food : meal.getFoods()) {
            ContentValues foodValues = food.toContentValues();
            foodValues.put(DataBase.Food.COLUMN_NAME_MEAL_ID, meal.getId());
            resolver.insert(buildUri(DataBase.Food.TABLE_NAME), foodValues);
        }
        insert(meal.getStatistic());
        return resolver.insert(buildUri(DataBase.Food.TABLE_NAME), meal.toContentValues());
    }

    public Uri insert(Day day) {
        for (Meal meal : day.getMeals()) {
            ContentValues mealValues = meal.toContentValues();
            mealValues.put(DataBase.Meal.COLUMN_NAME_DAY_ID, day.getId());
            resolver.insert(buildUri(DataBase.Meal.TABLE_NAME), mealValues);
        }
        insert(day.getStatistic());
        insert(day.getObjective());
        return resolver.insert(buildUri(DataBase.Food.TABLE_NAME), day.toContentValues());
    }

    private static Uri buildUri(String table) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(NutritionProvider.AUTHORITY).appendPath(table);
        return builder.build();
    }
}
