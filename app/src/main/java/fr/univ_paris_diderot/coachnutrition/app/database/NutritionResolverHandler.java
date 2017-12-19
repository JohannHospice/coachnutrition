package fr.univ_paris_diderot.coachnutrition.app.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import fr.univ_paris_diderot.coachnutrition.app.model.Meal;

public class NutritionResolverHandler {
    private ContentResolver resolver;

    public NutritionResolverHandler(Context context) {
        resolver = context.getContentResolver();
    }

    private static Uri buildUri(String table) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(NutritionProvider.AUTHORITY).appendPath(table);
        return builder.build();
    }

    public Cursor query(String table, long id, String[] projection) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(NutritionProvider.AUTHORITY).appendPath(table);
        Uri uri = ContentUris.appendId(builder, id).build();
        return resolver.query(uri, projection, Contract.Statistic._ID + " = ?", new String[]{String.valueOf(id)}, null);
    }

    public Cursor query(String table, String[] projection, String selection, String[] selectionArgs) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(NutritionProvider.AUTHORITY).appendPath(table);
        return resolver.query(builder.build(), projection, selection, selectionArgs, null);
    }

    public Uri insert(String table, ContentValues contentValues) {
        return resolver.insert(buildUri(table), contentValues);
    }

    public int update(String table, ContentValues contentValues, String where, String[] selectionArgs) {
        return resolver.update(buildUri(table), contentValues, where, selectionArgs);
    }

    public int updateObjective(long id, int minCalorie, int maxCalorie) {
        return update(Contract.Objective.TABLE_NAME, createObjectiveValues(minCalorie, maxCalorie), Contract.Objective._ID + " = ?", new String[]{String.valueOf(id)});
    }

    public int delete(String table, String where, String[] selectionArgs) {
        return resolver.delete(buildUri(table), where, selectionArgs);
    }

    public void deleteFoodMeal(long id) {
        Cursor cursorFoodMeal = getFoodMeal(id, null);
        if (cursorFoodMeal != null && cursorFoodMeal.moveToFirst()) {
            long idFood = cursorFoodMeal.getLong(cursorFoodMeal.getColumnIndex(Contract.FoodMeal.COLUMN_NAME_FOOD_ID));
            long idMeal = cursorFoodMeal.getLong(cursorFoodMeal.getColumnIndex(Contract.FoodMeal.COLUMN_NAME_MEAL_ID));
            float gramme = cursorFoodMeal.getFloat(cursorFoodMeal.getColumnIndex(Contract.FoodMeal.COLUMN_NAME_GRAMME));
            cursorFoodMeal.close();

            Cursor cursorFood = getFood(idFood, new String[]{Contract.Food.COLUMN_NAME_STATISTIC_ID});
            if (cursorFood != null && cursorFood.moveToFirst()) {
                long idFoodStat = cursorFood.getLong(cursorFood.getColumnIndex(Contract.Food.COLUMN_NAME_STATISTIC_ID));
                cursorFood.close();

                Cursor cursorFoodStat = getStatistic(idFoodStat, null);
                if (cursorFoodStat != null && cursorFoodStat.moveToFirst()) {
                    float calorie = cursorFoodStat.getFloat(cursorFoodStat.getColumnIndex(Contract.Statistic.COLUMN_NAME_CALORIE)) * gramme;
                    float protein = cursorFoodStat.getFloat(cursorFoodStat.getColumnIndex(Contract.Statistic.COLUMN_NAME_PROTEIN)) * gramme;
                    float glucide = cursorFoodStat.getFloat(cursorFoodStat.getColumnIndex(Contract.Statistic.COLUMN_NAME_GLUCIDE)) * gramme;
                    float lipide = cursorFoodStat.getFloat(cursorFoodStat.getColumnIndex(Contract.Statistic.COLUMN_NAME_LIPIDE)) * gramme;
                    cursorFoodStat.close();

                    //update mealstat
                    Cursor cursorMeal = getMeal(idMeal, new String[]{Contract.Meal.COLUMN_NAME_DAY_ID, Contract.Meal.COLUMN_NAME_STATISTIC_ID});
                    if (cursorMeal != null && cursorMeal.moveToFirst()) {
                        long mealStatId = cursorMeal.getLong(cursorMeal.getColumnIndex(Contract.Meal.COLUMN_NAME_STATISTIC_ID));
                        long dayId = cursorMeal.getLong(cursorMeal.getColumnIndex(Contract.Meal.COLUMN_NAME_DAY_ID));
                        cursorMeal.close();

                        Cursor cursorMealStat = getStatistic(mealStatId, null);
                        if (cursorMealStat != null && cursorMealStat.moveToFirst()) {
                            ContentValues values = new ContentValues();
                            values.put(Contract.Statistic.COLUMN_NAME_CALORIE, cursorMealStat.getFloat(cursorMealStat.getColumnIndex(Contract.Statistic.COLUMN_NAME_CALORIE)) - calorie);
                            values.put(Contract.Statistic.COLUMN_NAME_PROTEIN, cursorMealStat.getFloat(cursorMealStat.getColumnIndex(Contract.Statistic.COLUMN_NAME_PROTEIN)) - protein);
                            values.put(Contract.Statistic.COLUMN_NAME_GLUCIDE, cursorMealStat.getFloat(cursorMealStat.getColumnIndex(Contract.Statistic.COLUMN_NAME_GLUCIDE)) - glucide);
                            values.put(Contract.Statistic.COLUMN_NAME_LIPIDE, cursorMealStat.getFloat(cursorMealStat.getColumnIndex(Contract.Statistic.COLUMN_NAME_LIPIDE)) - lipide);
                            update(Contract.Statistic.TABLE_NAME, values, Contract.Statistic._ID + " = ?",
                                    new String[]{String.valueOf(mealStatId)});
                            cursorMealStat.close();
                        }
                        Cursor cursorDay = getDay(dayId, new String[]{Contract.Day.COLUMN_NAME_STATISTIC_ID});
                        if (cursorDay != null && cursorDay.moveToFirst()) {
                            long dayStatId = cursorDay.getLong(cursorDay.getColumnIndex(Contract.Day.COLUMN_NAME_STATISTIC_ID));
                            cursorDay.close();

                            Cursor cursorDayStat = getStatistic(dayStatId, null);
                            if (cursorDayStat != null && cursorDayStat.moveToFirst()) {
                                ContentValues values = new ContentValues();
                                values.put(Contract.Statistic.COLUMN_NAME_CALORIE, cursorDayStat.getFloat(cursorDayStat.getColumnIndex(Contract.Statistic.COLUMN_NAME_CALORIE)) - calorie);
                                values.put(Contract.Statistic.COLUMN_NAME_PROTEIN, cursorDayStat.getFloat(cursorDayStat.getColumnIndex(Contract.Statistic.COLUMN_NAME_PROTEIN)) - protein);
                                values.put(Contract.Statistic.COLUMN_NAME_GLUCIDE, cursorDayStat.getFloat(cursorDayStat.getColumnIndex(Contract.Statistic.COLUMN_NAME_GLUCIDE)) - glucide);
                                values.put(Contract.Statistic.COLUMN_NAME_LIPIDE, cursorDayStat.getFloat(cursorDayStat.getColumnIndex(Contract.Statistic.COLUMN_NAME_LIPIDE)) - lipide);
                                update(Contract.Statistic.TABLE_NAME, values, Contract.Statistic._ID + " = ?",
                                        new String[]{String.valueOf(dayStatId)});
                                cursorDayStat.close();
                            }
                        }
                    }
                }
            }
        }
        delete(Contract.FoodMeal.TABLE_NAME, Contract.FoodMeal._ID + " = ?", new String[]{String.valueOf(id)});
    }

    public Cursor getDay(String today, String[] projection) {
        return query(Contract.Day.TABLE_NAME, projection, Contract.Day.COLUMN_NAME_DATE + " = ?", new String[]{today});
    }

    public Cursor getDay(long id, String[] projection) {
        return query(Contract.Day.TABLE_NAME, projection, Contract.Day._ID + " = ?", new String[]{String.valueOf(id)});
    }

    public Cursor getObjective(long id, String[] projection) {
        return query(Contract.Objective.TABLE_NAME, projection, Contract.Objective._ID + " = ?", new String[]{String.valueOf(id)});
    }

    public Cursor getFood(long id, String[] projection) {
        return query(Contract.Food.TABLE_NAME, projection, Contract.Food._ID + " = ?", new String[]{String.valueOf(id)});
    }

    public Cursor getFoodMeal(long id, String[] projection) {
        return query(Contract.FoodMeal.TABLE_NAME, projection, Contract.FoodMeal._ID + " = ?", new String[]{String.valueOf(id)});
    }

    public Cursor getMeal(long id, String[] projection) {
        return query(Contract.Meal.TABLE_NAME, projection, Contract.Meal._ID + " = ?", new String[]{String.valueOf(id)});
    }

    public Cursor getFoods(String[] projection) {
        return query(Contract.Food.TABLE_NAME, projection, null, null);
    }

    public Cursor getMeals(String[] projection) {
        return query(Contract.Meal.TABLE_NAME, projection, null, null);
    }

    public Cursor getStatistic(long id, String[] projection) {
        return query(Contract.Statistic.TABLE_NAME, projection, Contract.Statistic._ID + " = ?", new String[]{String.valueOf(id)});
    }

    public long insertObjective(int minCalorie, int maxCalorie) {
        Uri uri = insert(Contract.Objective.TABLE_NAME, createObjectiveValues(minCalorie, maxCalorie));
        return Long.parseLong(uri.getPathSegments().get(1));
    }

    public long insertStatistic(float calorie, float protein, float glucide, float lipide) {
        Uri uri = insert(Contract.Statistic.TABLE_NAME, createStatisticValues(calorie, protein, glucide, lipide));
        return Long.parseLong(uri.getPathSegments().get(1));
    }

    public long insertFood(String name, long idStatistic) {
        Uri uri = insert(Contract.Food.TABLE_NAME, createFoodValues(name, idStatistic));
        return Long.parseLong(uri.getPathSegments().get(1));
    }

    public long insertFoodMeal(float gramme, long idFood, long idMeal) {
        Uri uri = insert(Contract.FoodMeal.TABLE_NAME, createFoodMealValues(gramme, idFood, idMeal));
        return Long.parseLong(uri.getPathSegments().get(1));
    }

    public long insertMeal(String name, long idDay, long idStatistic) {
        Uri uri = insert(Contract.Meal.TABLE_NAME, createMealValues(name, idDay, idStatistic));
        return Long.parseLong(uri.getPathSegments().get(1));
    }

    public long insertDay(String date, long idObjective, long idStatistic) {
        Uri uri = insert(Contract.Day.TABLE_NAME, createDayValues(date, idObjective, idStatistic));
        return Long.parseLong(uri.getPathSegments().get(1));
    }

    public static ContentValues createStatisticValues(float calorie, float protein, float glucide, float lipide) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.Statistic.COLUMN_NAME_CALORIE, calorie);
        contentValues.put(Contract.Statistic.COLUMN_NAME_GLUCIDE, glucide);
        contentValues.put(Contract.Statistic.COLUMN_NAME_LIPIDE, lipide);
        contentValues.put(Contract.Statistic.COLUMN_NAME_PROTEIN, protein);
        return contentValues;
    }

    public static ContentValues createFoodValues(String name, long idStatistic) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.Food.COLUMN_NAME_NAME, name);
        contentValues.put(Contract.Food.COLUMN_NAME_STATISTIC_ID, idStatistic);
        return contentValues;
    }

    public static ContentValues createFoodMealValues(float gramme, long idFood, long idMeal) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.FoodMeal.COLUMN_NAME_GRAMME, gramme);
        contentValues.put(Contract.FoodMeal.COLUMN_NAME_FOOD_ID, idFood);
        contentValues.put(Contract.FoodMeal.COLUMN_NAME_MEAL_ID, idMeal);
        return contentValues;
    }

    public static ContentValues createMealValues(String name, long idDay, long idStatistic) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.Meal.COLUMN_NAME_NAME, name);
        contentValues.put(Contract.Meal.COLUMN_NAME_DAY_ID, idDay);
        contentValues.put(Contract.Meal.COLUMN_NAME_STATISTIC_ID, idStatistic);
        return contentValues;
    }

    public static ContentValues createDayValues(String date, long idObjective, long idStatistic) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.Day.COLUMN_NAME_DATE, date);
        contentValues.put(Contract.Day.COLUMN_NAME_OBJECTIVE_ID, idObjective);
        contentValues.put(Contract.Day.COLUMN_NAME_STATISTIC_ID, idStatistic);
        return contentValues;
    }

    public static ContentValues createObjectiveValues(int minCalorie, int maxCalorie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.Objective.COLUMN_NAME_MAX_CALORIE, maxCalorie);
        contentValues.put(Contract.Objective.COLUMN_NAME_MIN_CALORIE, minCalorie);
        return contentValues;
    }
}
