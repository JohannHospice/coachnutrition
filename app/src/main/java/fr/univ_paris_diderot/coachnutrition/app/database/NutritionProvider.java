package fr.univ_paris_diderot.coachnutrition.app.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class NutritionProvider extends ContentProvider {
    public static final String AUTHORITY = "fr.univ-paris-diderot.coachnutrition";
    private static final UriMatcher uriMatcher;
    private static final int CODE_OBJECTIVE = 1;
    private static final int CODE_STATISTIC = 2;
    private static final int CODE_FOOD = 3;
    private static final int CODE_MEAL = 4;
    private static final int CODE_DAY = 5;
    private static final int CODE_FOOD_MEAL = 6;
    private static final int CODE_STATISTIC_ID = 7;
    private static final int CODE_FOOD_STATISTIC = 8;

    private DatabaseHelper helper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, Contract.Objective.TABLE_NAME, CODE_OBJECTIVE);
        uriMatcher.addURI(AUTHORITY, Contract.Statistic.TABLE_NAME, CODE_STATISTIC);
        uriMatcher.addURI(AUTHORITY, Contract.Statistic.TABLE_NAME + "/#", CODE_STATISTIC_ID);
        uriMatcher.addURI(AUTHORITY, Contract.FoodMeal.TABLE_NAME, CODE_FOOD_MEAL);
        uriMatcher.addURI(AUTHORITY, Contract.Food.TABLE_NAME, CODE_FOOD);
        uriMatcher.addURI(AUTHORITY, Contract.Meal.TABLE_NAME, CODE_MEAL);
        uriMatcher.addURI(AUTHORITY, Contract.Day.TABLE_NAME, CODE_DAY);
        uriMatcher.addURI(AUTHORITY, Contract.Food.TABLE_NAME + Contract.Statistic.TABLE_NAME, CODE_FOOD_STATISTIC);
    }

    @Override
    public boolean onCreate() {
        helper = DatabaseHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;// "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + switchCode(uriMatcher.match(uri));
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrdered) {
        Log.d("provider query", uri.toString());
        int code = uriMatcher.match(uri);
        try {
            String table = switchCode(code);
            return helper.getReadableDatabase().query(table, projection, selection, selectionArgs, null, null, sortOrdered);

        } catch (Exception e) {
            if (code == CODE_FOOD_STATISTIC) {
                String sql = "select * from " + Contract.Food.TABLE_NAME + ", " + Contract.Statistic.TABLE_NAME;
                if (selectionArgs != null && selectionArgs[0] != null) {
                    sql += " where " + Contract.Food.COLUMN_NAME_NAME + " like ?";
                    return helper.getReadableDatabase().rawQuery(sql, selectionArgs);
                }
                return helper.getReadableDatabase().rawQuery(sql, null);
            }
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Log.d("provider insert", uri.toString());
        String table = switchCode(uriMatcher.match(uri));
        SQLiteDatabase db = helper.getWritableDatabase();
        long id = db.insertOrThrow(table, null, contentValues);
        Uri.Builder builder = (new Uri.Builder())
                .authority(AUTHORITY)
                .appendPath(table);
        return ContentUris.appendId(builder, id).build();
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String whereClause, @Nullable String[] whereArgs) {
        Log.d("provider update", uri.toString());
        String table = switchCode(uriMatcher.match(uri));
        return helper.getWritableDatabase().update(table, contentValues, whereClause, whereArgs);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String whereClause, @Nullable String[] whereArgs) {
        Log.d("provider delete", uri.toString());
        String table = switchCode(uriMatcher.match(uri));
        if (table == null)
            return 0;
        return helper.getWritableDatabase().delete(table, whereClause, whereArgs);
    }

    private static String switchCode(int code) {
        switch (code) {
            case CODE_STATISTIC:
                return Contract.Statistic.TABLE_NAME;
            case CODE_OBJECTIVE:
                return Contract.Objective.TABLE_NAME;
            case CODE_FOOD:
                return Contract.Food.TABLE_NAME;
            case CODE_MEAL:
                return Contract.Meal.TABLE_NAME;
            case CODE_FOOD_MEAL:
                return Contract.FoodMeal.TABLE_NAME;
            case CODE_DAY:
                return Contract.Day.TABLE_NAME;
            default:
                Log.d("Uri provider =", String.valueOf(code));
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }
}
