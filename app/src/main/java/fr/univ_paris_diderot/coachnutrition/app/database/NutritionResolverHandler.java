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
}
