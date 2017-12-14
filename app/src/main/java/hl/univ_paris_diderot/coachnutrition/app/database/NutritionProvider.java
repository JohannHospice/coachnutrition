package hl.univ_paris_diderot.coachnutrition.app.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class NutritionProvider extends ContentProvider {
    private DataBaseHelper helper;

    public static final String AUTHORITY = "fr.univ-paris-diderot.coachnutrition";
    private static final int CODE_OBJECTIVE = 1;
    private static final int CODE_STATISTIC = 2;
    private static final int CODE_FOOD = 3;
    private static final int CODE_MEAL = 4;
    private static final int CODE_DAY = 5;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "objective", CODE_OBJECTIVE);
        uriMatcher.addURI(AUTHORITY, "statistic", CODE_STATISTIC);
        uriMatcher.addURI(AUTHORITY, "food", CODE_FOOD);
        uriMatcher.addURI(AUTHORITY, "meal", CODE_MEAL);
        uriMatcher.addURI(AUTHORITY, "day", CODE_DAY);
    }

    @Override
    public boolean onCreate() {
        helper = DataBaseHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + switchCode(uriMatcher.match(uri));
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrdered) {
        return helper.getReadableDatabase().query(
                DataBase.Statistic.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrdered);
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        String path = switchCode(uriMatcher.match(uri));

        long id = helper.getWritableDatabase().insertOrThrow(path, null, contentValues);
        Uri.Builder builder = (new Uri.Builder())
                .authority(AUTHORITY)
                .appendPath(path);
        return ContentUris.appendId(builder, id).build();
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String whereClause, @Nullable String[] whereArgs) {
        String path = switchCode(uriMatcher.match(uri));

        return helper.getWritableDatabase().delete(path, whereClause, whereArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String whereClause, @Nullable String[] whereArgs) {
        String path = switchCode(uriMatcher.match(uri));

        return helper.getWritableDatabase().update(path, contentValues, whereClause, whereArgs);
    }

    private static String switchCode(int code) {
        switch (code) {
            case CODE_STATISTIC:
                return DataBase.Statistic.TABLE_NAME;
            case CODE_OBJECTIVE:
                return DataBase.Objective.TABLE_NAME;
            case CODE_FOOD:
                return DataBase.Food.TABLE_NAME;
            case CODE_MEAL:
                return DataBase.Meal.TABLE_NAME;
            case CODE_DAY:
                return DataBase.Day.TABLE_NAME;
            default:
                Log.d("Uri provider =", String.valueOf(code));
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }

/*
    public void close() {
        helper.close();
    }

    public long insert(Statistic statistic) {
        ContentValues values = new ContentValues();
        values.put(DataBase.Statistic.COLUMN_NAME_CALORIE, statistic.getCalorie());
        values.put(DataBase.Statistic.COLUMN_NAME_GLUCIDE, statistic.getGlucide());
        values.put(DataBase.Statistic.COLUMN_NAME_LIPIDE, statistic.getLipid());
        values.put(DataBase.Statistic.COLUMN_NAME_PROTEIN, statistic.getProtein());
        return helper.getWritableDatabase().insert(DataBase.Statistic.TABLE_NAME, null, values);
    }

    public int update(int id, Statistic statistic) {
        ContentValues values = new ContentValues();
        values.put(DataBase.Statistic.COLUMN_NAME_CALORIE, statistic.getCalorie());
        values.put(DataBase.Statistic.COLUMN_NAME_GLUCIDE, statistic.getGlucide());
        values.put(DataBase.Statistic.COLUMN_NAME_LIPIDE, statistic.getLipid());
        values.put(DataBase.Statistic.COLUMN_NAME_PROTEIN, statistic.getProtein());
        return helper.getWritableDatabase().update(DataBase.Statistic.TABLE_NAME, values, DataBase.Statistic._ID + " = " + id, null);
    }

    public long delete(long id) {
        return helper.getWritableDatabase().delete(DataBase.Statistic.TABLE_NAME, DataBase.Statistic._ID + " = " + id, null);
    }

    public long delete(Statistic statistic) {
        return delete(statistic.getId());
    }

    public Cursor read(String[] projection, String selection, String[] selectionArgs) {
        return helper.getReadableDatabase().query(
                DataBase.Statistic.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }

    public Statistic get(long id) {
        Cursor cursor = read(
                ALL_COLUMNS,
                DataBase.Statistic._ID + " = ?",
                new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        Statistic statistic = convert(cursor);
        cursor.close();
        return statistic;
    }

    public List<Statistic> get(String selection, String[] selectionArgs) {
        Cursor cursor = read(
                ALL_COLUMNS,
                selection,
                selectionArgs);
        cursor.moveToFirst();

        List<Statistic> statistics = new ArrayList<>();
        while (cursor.moveToNext())
            statistics.add(convert(cursor));
        cursor.close();
        return statistics;
    }

    private Statistic convert(Cursor cursor) {
        Statistic statistic = new Statistic();
        statistic.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DataBase.Statistic._ID)));
        statistic.setCalorie(cursor.getInt(cursor.getColumnIndexOrThrow(DataBase.Statistic.COLUMN_NAME_CALORIE)));
        statistic.setGlucide(cursor.getInt(cursor.getColumnIndexOrThrow(DataBase.Statistic.COLUMN_NAME_GLUCIDE)));
        statistic.setLipid(cursor.getInt(cursor.getColumnIndexOrThrow(DataBase.Statistic.COLUMN_NAME_LIPIDE)));
        statistic.setProtein(cursor.getInt(cursor.getColumnIndexOrThrow(DataBase.Statistic.COLUMN_NAME_PROTEIN)));
        return statistic;
    }
    */
}
