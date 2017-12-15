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
    private DatabaseHelper helper;

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
        String table = switchCode(uriMatcher.match(uri));
        if (table == null)
            return null;
        return helper.getReadableDatabase().query(
                table,
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
        String table = switchCode(uriMatcher.match(uri));
        if (table == null)
            return null;
        SQLiteDatabase db = helper.getWritableDatabase();
        long id = db.insertOrThrow(table, null, contentValues);
        Uri.Builder builder = (new Uri.Builder())
                .authority(AUTHORITY)
                .appendPath(table);
        return ContentUris.appendId(builder, id).build();
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String whereClause, @Nullable String[] whereArgs) {
        String table = switchCode(uriMatcher.match(uri));
        if (table == null)
            return 0;
        return helper.getWritableDatabase().update(table, contentValues, whereClause, whereArgs);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String whereClause, @Nullable String[] whereArgs) {
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
            case CODE_DAY:
                return Contract.Day.TABLE_NAME;
            default:
                Log.d("Uri provider =", String.valueOf(code));
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }
}