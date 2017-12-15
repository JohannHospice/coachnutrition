package fr.univ_paris_diderot.coachnutrition.app.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.Date;

public class NutritionResolverHandler {
    private ContentResolver resolver;

    public NutritionResolverHandler(Context context) {
        resolver = context.getContentResolver();
    }

    public void init() {

        Uri uri = insert(Contract.Statistic.TABLE_NAME, createStatisticValues(200, 40, 30, 21));
        System.out.println(uri.getPathSegments().get(1));
    }

    public Cursor getId(String table, long id) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(NutritionProvider.AUTHORITY).appendPath(table);
        Uri uri = ContentUris.appendId(builder, id).build();
        return resolver.query(uri, null, Contract.Statistic._ID + " = ?", new String[]{String.valueOf(id)}, null);
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

    public int delete(String where, String[] selectionArgs) {
        return resolver.delete(buildUri(Contract.Food.TABLE_NAME), where, selectionArgs);
    }

    private static Uri buildUri(String table) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(NutritionProvider.AUTHORITY).appendPath(table);
        return builder.build();
    }

    public Cursor getDay(Date today) {
        return query(Contract.Day.TABLE_NAME, null, "date = ?", new String[]{String.valueOf(today.getTime())});
    }

    public Cursor getFood(long id) {
        return query(Contract.Food.TABLE_NAME, null, "id = ?", new String[]{String.valueOf(id)});
    }

    public long insertStatistic(int calorie, int protein, int glucide, int lipide) {
        Uri uri = insert(Contract.Statistic.TABLE_NAME, createStatisticValues(calorie, protein, glucide, lipide));
        return Long.parseLong(uri.getPathSegments().get(1));
    }

    public long insertFood(String name, int gramme, long idStatistic) {
        Uri uri = insert(Contract.Food.TABLE_NAME, createFoodValues(name, gramme, idStatistic));
        return Long.parseLong(uri.getPathSegments().get(1));
    }

    private ContentValues createStatisticValues(int calorie, int protein, int glucide, int lipide) {
        ContentValues statisticValues = new ContentValues();
        statisticValues.put(Contract.Statistic.COLUMN_NAME_CALORIE, calorie);
        statisticValues.put(Contract.Statistic.COLUMN_NAME_GLUCIDE, glucide);
        statisticValues.put(Contract.Statistic.COLUMN_NAME_LIPIDE, lipide);
        statisticValues.put(Contract.Statistic.COLUMN_NAME_PROTEIN, protein);
        return statisticValues;
    }

    private ContentValues createFoodValues(String name, int gramme, long idStatistic) {
        ContentValues foodValues = new ContentValues();
        foodValues.put(Contract.Food.COLUMN_NAME_NAME, name);
        foodValues.put(Contract.Food.COLUMN_NAME_GRAMME, gramme);
        foodValues.put(Contract.Food.COLUMN_NAME_STATISTIC_ID, idStatistic);
        return foodValues;
    }

    private ContentValues createFoodValues(String name, int gramme, long idStatistic, long idMeal) {
        ContentValues foodValues = createFoodValues(name, gramme, idStatistic);
        foodValues.put(Contract.Food.COLUMN_NAME_MEAL_ID, idMeal);
        return foodValues;
    }
}
