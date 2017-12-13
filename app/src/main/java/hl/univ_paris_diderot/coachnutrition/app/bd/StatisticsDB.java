package hl.univ_paris_diderot.coachnutrition.app.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import hl.univ_paris_diderot.coachnutrition.app.nutrition.Statistic;

public class StatisticsDB {
    private final static String[] ALL_COLUMNS = {
            Contract.Statistic._ID,
            Contract.Statistic.COLUMN_NAME_CALORIE,
            Contract.Statistic.COLUMN_NAME_PROTEIN,
            Contract.Statistic.COLUMN_NAME_GLUCIDE,
            Contract.Statistic.COLUMN_NAME_LIPIDE};

    private DbHelper helper;

    public StatisticsDB(Context context) {
        helper = new DbHelper(context);
    }

    public void close() {
        helper.close();
    }

    public long insert(Statistic statistic) {
        ContentValues values = new ContentValues();
        values.put(Contract.Statistic.COLUMN_NAME_CALORIE, statistic.getCalorie());
        values.put(Contract.Statistic.COLUMN_NAME_GLUCIDE, statistic.getGlucide());
        values.put(Contract.Statistic.COLUMN_NAME_LIPIDE, statistic.getLipid());
        values.put(Contract.Statistic.COLUMN_NAME_PROTEIN, statistic.getProtein());
        return helper.getWritableDatabase().insert(Contract.Statistic.TABLE_NAME, null, values);
    }

    public int update(int id, Statistic statistic) {
        ContentValues values = new ContentValues();
        values.put(Contract.Statistic.COLUMN_NAME_CALORIE, statistic.getCalorie());
        values.put(Contract.Statistic.COLUMN_NAME_GLUCIDE, statistic.getGlucide());
        values.put(Contract.Statistic.COLUMN_NAME_LIPIDE, statistic.getLipid());
        values.put(Contract.Statistic.COLUMN_NAME_PROTEIN, statistic.getProtein());
        return helper.getWritableDatabase().update(Contract.Statistic.TABLE_NAME, values, Contract.Statistic._ID + " = " + id, null);
    }

    public long delete(long id) {
        return helper.getWritableDatabase().delete(Contract.Statistic.TABLE_NAME, Contract.Statistic._ID + " = " + id, null);
    }

    public long delete(Statistic statistic) {
        return delete(statistic.getId());
    }

    public Cursor read(String[] projection, String selection, String[] selectionArgs) {
        return helper.getReadableDatabase().query(
                Contract.Statistic.TABLE_NAME,
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
                Contract.Statistic._ID + " = ?",
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
        statistic.setId(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.Statistic._ID)));
        statistic.setCalorie(cursor.getInt(cursor.getColumnIndexOrThrow(Contract.Statistic.COLUMN_NAME_CALORIE)));
        statistic.setGlucide(cursor.getInt(cursor.getColumnIndexOrThrow(Contract.Statistic.COLUMN_NAME_GLUCIDE)));
        statistic.setLipid(cursor.getInt(cursor.getColumnIndexOrThrow(Contract.Statistic.COLUMN_NAME_LIPIDE)));
        statistic.setProtein(cursor.getInt(cursor.getColumnIndexOrThrow(Contract.Statistic.COLUMN_NAME_PROTEIN)));
        return statistic;
    }
}
